package bin;

import lib.AFDequivalente;
import lib.AFDexecute;
import lib.AFDmanipulation;
import lib.XMLmanipulation;
import modelo.Afd;
import modelo.Equivalente;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Diego on 19/04/2017.
 */
public class Index {

    public static void main(String[] args) throws IOException {

       // XMLmanipulation xml = new XMLmanipulation();


        Gui.start();

        //Item
        //Afd m = xml.load("max3.jff");
       //Afd inter1 = xml.load("d2.jff");
       // Afd inter2 = xml.load("d1.jff");


        //Item 2
        // xml.salve(m,"saida.jff");

       // AFDmanipulation afdm = new AFDmanipulation();
       // AFDequivalente afde = new AFDequivalente();

        // //Item 7
        // m = afdm.deleteState(m,1);


        // Item 3a
        // ArrayList<Equivalente> eqv =  afde.equivalents(m);


        //Item 3b
       // m = afdm.minimum(m,eqv);

        //Item 5a
       // m = afdm.complement(m);

        //m = afdm.intersection(inter1,inter2);

        //diferença
       // m = afdm.intersection(inter1,afdm.complement(inter2));

        //m = afdm.union(inter1,inter2);
      //  xml.salve(m,"mim3.jff");
//
        System.out.println("Fim");

    }
}
