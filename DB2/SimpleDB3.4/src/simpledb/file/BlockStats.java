package simpledb.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockStats {

    private HashMap<String, Integer> readFile2Blocks = new HashMap<String, Integer>();

    private HashMap<String, Integer> writtenFile2Blocks = new HashMap<String, Integer>();

    public void logReadBlock(BlockId block){
        if(readFile2Blocks.containsKey(block.fileName())){
            int n = readFile2Blocks.get(block.fileName()).intValue() + 1;
            Integer n2 = n;
            readFile2Blocks.put(block.fileName(), n2);
        }
        else{
            Integer i = 1;
            readFile2Blocks.put(block.fileName(), i);
        }
    }

    public void logWrittenBlock(BlockId block){
        if(writtenFile2Blocks.containsKey(block.fileName())){
            int n = writtenFile2Blocks.get(block.fileName()).intValue() + 1;
            Integer n2 = n;
            writtenFile2Blocks.put(block.fileName(), n2);
        }
        else{
            Integer i = 1;
            writtenFile2Blocks.put(block.fileName(), i);
        }
    }

    @Override
    public String toString() {
        String stringa = "\nTotale blocchi letti: " + readFile2Blocks.toString() + "\nTotale blocchi scritti: " + writtenFile2Blocks.toString();
        return stringa;
    }

    public void reset(){
        readFile2Blocks.clear();
        writtenFile2Blocks.clear();
    }

}
