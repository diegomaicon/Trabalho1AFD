package bin;

import lib.AFDexecute;
import lib.AFDmanipulation;
import lib.XMLmanipulation;
import modelo.Afd;
import modelo.Equivalente;
import modelo.State;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Diego on 19/04/2017.
 */
public class Index {


    public static void start(){

        AFDmanipulation afdm = new AFDmanipulation();
        XMLmanipulation xml = new XMLmanipulation();
        String print="";
        String caminhoEntrada1 ="";
        String caminhoEntrada2 ="";
        String saida ="";
        Afd m ;
        ArrayList<Equivalente> eqv;

        String op = JOptionPane.showInputDialog(null," Digite a opção:\n [ 1 ]  Imprimir estados equivalentes (Item 3).\n"+
                                                                              " [ 2 ]   Obter versão mínima do AFD (Item 3).\n"+
                                                                              " [ 3 ]   Automatos equivalentes (Item 4).\n"+
                                                                              " [ 4 ]   Complemento do Altômato (Item 5).\n"+
                                                                              " [ 5 ]   União de Autômato (Item 5).\n"+
                                                                              " [ 6 ]   Interseção de Autômato (Item 5).\n"+
                                                                              " [ 7 ]   Diferença de Autômato (Item 5).\n"+
                                                                              " [ 8 ]   Aceita Palavra - Testar a pertença de uma palavra na linguagem (Item 6).\n"+
                                                                              " [ 9 ]   Aceita Palavra - Testar movimentos (Item 6).\n"+
                                                                              " [ 10 ]  Alterar Autômato (Item 7)\n");


        switch (Integer.parseInt(op)){

            case 1:
               caminhoEntrada1 = JOptionPane.showInputDialog(null,"Informe nome arquivo entrada.");
                m = xml.load(caminhoEntrada1+".jff");
                 eqv =  afdm.equivalents(m);
                for (Equivalente e:eqv) {
                     print += "Estado "+e.getState1().getId()+" = "+e.getState2().getId()+"\n";
                }
                JOptionPane.showMessageDialog(null,print);
                break;
            case 2:
                caminhoEntrada1 = JOptionPane.showInputDialog(null,"Informe nome arquivo entrada.");
                m = xml.load(caminhoEntrada1+".jff");
                eqv =  afdm.equivalents(m);
                m = afdm.minimum(m,eqv);
                saida = JOptionPane.showInputDialog(null,"Informe nome arquivo entrada");
                xml.salve(m,saida+".jff");
                break;
        }


    }

    public static void main(String[] args) throws IOException {

        XMLmanipulation xml = new XMLmanipulation();
        AFDexecute exe = new AFDexecute();

        Index.start();


        //Item
        Afd m = xml.load("max.jff");
        Afd uni1 = xml.load("u1.jff");
        Afd uni2 = xml.load("u2.jff");


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
