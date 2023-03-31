package simpledb.buffer;

import simpledb.file.*;
import simpledb.log.LogMgr;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Manages the pinning and unpinning of buffers to blocks.
 * @author Edward Sciore
 *
 */
public class BufferMgr {
   private Buffer[] bufferpool;
   private int numAvailable;
   private static final long MAX_TIME = 10000; // 10 seconds
   //Replacement
   private static ReplacementStartegy REPLACEMENT_STRATEGY = ReplacementStartegy.NAIVE;
   private static long counter = 0;
   private int clockPosition = 0;
   
   /**
    * Creates a buffer manager having the specified number 
    * of buffer slots.
    * This constructor depends on a {@link FileMgr} and
    * {@link simpledb.log.LogMgr LogMgr} object.
    * @param numbuffs the number of buffer slots to allocate
    */
   public BufferMgr(FileMgr fm, LogMgr lm, int numbuffs) {
      bufferpool = new Buffer[numbuffs];
      numAvailable = numbuffs;
      for (int i=0; i<numbuffs; i++)
         bufferpool[i] = new Buffer(fm, lm);
   }
   
   /**
    * Returns the number of available (i.e. unpinned) buffers.
    * @return the number of available buffers
    */
   public synchronized int available() {
      return numAvailable;
   }
   
   /**
    * Flushes the dirty buffers modified by the specified transaction.
    * @param txnum the transaction's id number
    */
   public synchronized void flushAll(int txnum) {
      for (Buffer buff : bufferpool)
         if (buff.modifyingTx() == txnum)
         buff.flush();
   }
   
   
   /**
    * Unpins the specified data buffer. If its pin count
    * goes to zero, then notify any waiting threads.
    * @param buff the buffer to be unpinned
    */
   public synchronized void unpin(Buffer buff) {
      buff.unpin();
      if (!buff.isPinned()) {
         numAvailable++;
         notifyAll();
      }
   }
   
   /**
    * Pins a buffer to the specified block, potentially
    * waiting until a buffer becomes available.
    * If no buffer becomes available within a fixed 
    * time period, then a {@link BufferAbortException} is thrown.
    * @param blk a reference to a disk block
    * @return the buffer pinned to that block
    */
   public synchronized Buffer pin(BlockId blk) {
      try {
         long timestamp = System.currentTimeMillis();
         Buffer buff = tryToPin(blk);
         while (buff == null && !waitingTooLong(timestamp)) {
            wait(MAX_TIME);
            buff = tryToPin(blk);
         }
         if (buff == null)
            throw new BufferAbortException();
         return buff;
      }
      catch(InterruptedException e) {
         throw new BufferAbortException();
      }
   }  
   
   private boolean waitingTooLong(long starttime) {
      return System.currentTimeMillis() - starttime > MAX_TIME;
   }
   
   /**
    * Tries to pin a buffer to the specified block. 
    * If there is already a buffer assigned to that block
    * then that buffer is used;  
    * otherwise, an unpinned buffer from the pool is chosen.
    * Returns a null value if there are no available buffers.
    * @param blk a reference to a disk block
    * @return the pinned buffer
    */
   private Buffer tryToPin(BlockId blk) {
      Buffer buff = findExistingBuffer(blk);
      if (buff == null) {
         buff = chooseUnpinnedBuffer();
         if (buff == null)
            return null;
         buff.assignToBlock(blk);
      }
      if (!buff.isPinned())
         numAvailable--;
      buff.pin();
      return buff;
   }
   
   private Buffer findExistingBuffer(BlockId blk) {
      for (Buffer buff : bufferpool) {
         BlockId b = buff.block();
         if (b != null && b.equals(blk))
            return buff;
      }
      return null;
   }
   
   private Buffer chooseUnpinnedBuffer() {

      //scegli la strategia
      if(BufferMgr.REPLACEMENT_STRATEGY == ReplacementStartegy.LRU)
         return this.chooseUnpinnedBufferLRU();
      else if(BufferMgr.REPLACEMENT_STRATEGY == ReplacementStartegy.CLOCK)
         return this.chooseUnpinnedBufferClock();

      //Strategia NAIVE
      for (Buffer buff : bufferpool)
         if (!buff.isPinned())
         return buff;
      return null;
   }

   private Buffer chooseUnpinnedBufferClock() {
      int k= this.clockPosition;
      int i = 0;
      for(i=0;i<this.bufferpool.length; i++){
         if(!this.bufferpool[k].isPinned()){
            this.clockPosition=(k+1)% bufferpool.length; //la prossima volta parti dal successivo
            return this.bufferpool[k];
         }
         k=(k+1)%bufferpool.length;
      }
      return null;
   }

   private Buffer chooseUnpinnedBufferLRU() {
      //copia del buffer
      Buffer[] copy = this.bufferpool.clone();
      //faccio il sort sul tempo di Unpin
      Arrays.sort(copy, Comparator.comparing(Buffer::getUnpinTime));

      for (Buffer buff : copy)
         if (!buff.isPinned()) return buff;

      return null;
   }

   protected Buffer[] getBufferpool(){
      return this.bufferpool;
   }

   protected static long getNextCounter(){
      return ++BufferMgr.counter;
   }

   public static void setReplacementStrategy(ReplacementStartegy s){
      BufferMgr.REPLACEMENT_STRATEGY = s;
   }
}
