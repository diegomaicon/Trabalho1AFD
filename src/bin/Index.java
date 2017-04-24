package bin;

import lib.AFDexecute;
import lib.AFDmanipulation;
import lib.XMLmanipulation;
import modelo.Afd;
import modelo.Equivalente;
import modelo.State;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Diego on 19/04/2017.
 */
public class Index {


    public static void main(String[] args) throws IOException {

        XMLmanipulation xml = new XMLmanipulation();
        AFDexecute exe = new AFDexecute();


        //Item
        Afd m = xml.load("max.jff");
        Afd uni1 = xml.load("union1.jff");
        Afd uni2 = xml.load("union2.jff");


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
         ArrayList<Equivalente> eqv =  afdm.equivalents(m);
         System.out.println("ok");

        //Item 3b
        m = afdm.minimum(m,eqv);

        //Item 5a
       // m = afdm.complement(m);

        m = afdm.union(uni1,uni2);

        xml.salve(m,"unionFinal.jff");


    }
}
