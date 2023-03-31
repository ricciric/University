package simpledb.record;

import simpledb.file.BlockId;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

import java.time.Clock;

public class RecordPageTest {

    public static void main(String[] args) throws Exception{
        //Creo il DB
        SimpleDB db = new SimpleDB("recordpagetest", 2000, 500);
        Transaction tx = db.newTx();

        //creo la relazione
        Schema schema = new Schema();
        schema.addIntField("A");
        schema.addStringField("B", 12);

        //creo il record che conterrà i blocchi e le relazioni
        Layout layout = new Layout(schema);

        BlockId blk = tx.append("testfile");
        tx.pin(blk);
        RecordPage rp = new RecordPage(tx, blk, layout);
        rp.format();

        //Inserisco valori random nella pagina
        System.out.println("Filling the page with random records.");
        int slot = rp.insertAfter(-1);//é per indicare di prendere all'inizio il primo slot
        int n1 = 0;
        while (slot >= 0) {
            int n = (int) Math.round(Math.random() * 50);//creo numero casuale
            rp.setInt(slot, "A", n1);//inserisco nel campo A, deve essere un numero progressivo da 0
            rp.setString(slot, "B", "str"+n);//inserisco nel campo B mettendo str+il numero casuale (n)
            System.out.println("inserting into slot " + slot + ": {" + n1 + ", " + "str"+n + "}");
            slot = rp.insertAfter(slot);//aggiorno lo slot e vado al successivo
            n1++;
        }
        String stat = db.fileMgr().getBlockStats().toString();
        System.out.println(stat);
        //tot.blocchi scritti 83
    }
}
