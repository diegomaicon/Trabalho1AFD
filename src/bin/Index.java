package bin;

import lib.AFDmanipulation;
import lib.XMLmanipulation;
import modelo.Afd;

import java.io.IOException;

/**
 * Created by Diego on 19/04/2017.
 */
public class Index {

    public static void main(String[] args) throws IOException {
        Afd m = new Afd();
        XMLmanipulation xml = new XMLmanipulation();

        m = xml.load("teste.jff");

        xml.salve(m,"saida.jff");


        AFDmanipulation afdm = new AFDmanipulation();

        m = afdm.deleteState(m,1);


        System.out.println("ok");

    }
}
