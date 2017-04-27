package bin;

import lib.AFDexecute;
import lib.AFDmanipulation;
import lib.XMLmanipulation;
import modelo.Afd;

import java.io.IOException;

/**
 * Created by Diego on 19/04/2017.
 */
public class Index {

    public static void main(String[] args) throws IOException {

        XMLmanipulation xml = new XMLmanipulation();
        AFDexecute exe = new AFDexecute();

        Gui.start();


        //Item
        Afd m = xml.load("max.jff");
        Afd inter1 = xml.load("d2.jff");
        Afd inter2 = xml.load("d1.jff");


        //Item 2
        // xml.salve(m,"saida.jff");

        AFDmanipulation afdm = new AFDmanipulation();

        // //Item 7
        // m = afdm.deleteState(m,1);
/*
        // Item 6a
        if (exe.accept(m,"bbba"))
            System.out.println("Aceita");
        else System.out.println("Não Aceita");
*/
/*
        //  Item 6b
        State estado = m.geteInicial();
        estado = exe.move(m,estado,"bbb");
        if(estado.iseFinal())
            System.out.println("Aceita");
        else System.out.println("Não Aceita");
*/

        // Item 3a
        // ArrayList<Equivalente> eqv =  afdm.equivalents(m); //Falta fazer


        //Item 3b
        //m = afdm.minimum(m,eqv);

        //Item 5a
       // m = afdm.complement(m);

        //m = afdm.intersection(inter1,inter2);

        //diferença
       // m = afdm.intersection(inter1,afdm.complement(inter2));

        //m = afdm.union(inter1,inter2);
        xml.salve(m,"difference2.jff");

        System.out.println("ok");
    }
}
