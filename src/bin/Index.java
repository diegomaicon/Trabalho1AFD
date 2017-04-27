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
        Afd m1,m2;
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


        switch (Integer.parseInt(op)) {

            case 1:
                do {
                    caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada.");
                    m1 = xml.load(caminhoEntrada1 + ".jff");
                    if (m1.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m1.getEstado() == null);
                eqv = afdm.equivalents(m1);
                for (Equivalente e : eqv) {
                    print += "Estado -> " + e.getState1().getId() + " = " + e.getState2().getId() + "\n";
                }
                JOptionPane.showMessageDialog(null, print);
                break;

            case 2:
                do {
                    caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada.");
                    m1 = xml.load(caminhoEntrada1 + ".jff");
                    if (m1.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m1.getEstado() == null);
                eqv = afdm.equivalents(m1);
                m1 = afdm.minimum(m1, eqv);
                saida = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada");
                xml.salve(m1, saida + ".jff");
                break;
            case 4://Complemeto
                do {
                    caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada.");
                    m1 = xml.load(caminhoEntrada1 + ".jff");
                    if (m1.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m1.getEstado() == null);
                m1 = afdm.complement(m1);
                saida = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada");
                xml.salve(m1, saida + ".jff");
                break;
            case 5: //União
                do {
                    caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD 1 entrada.");
                    m1 = xml.load(caminhoEntrada1 + ".jff");
                    if (m1.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m1.getEstado() == null);
                do {
                    caminhoEntrada2 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD 2 entrada.");
                    m2 = xml.load(caminhoEntrada2 + ".jff");
                    if (m2.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m2.getEstado() == null);
                m1 = afdm.union(m1,m2);
                saida = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada");
                xml.salve(m1, saida + ".jff");
                break;

            case 6: //Interseção
                do {
                    caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD 1 entrada.");
                    m1 = xml.load(caminhoEntrada1 + ".jff");
                    if (m1.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m1.getEstado() == null);
                do {
                    caminhoEntrada2 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD 2 entrada.");
                    m2 = xml.load(caminhoEntrada2 + ".jff");
                    if (m2.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m2.getEstado() == null);
                m1 = afdm.intersection(m1,m2);
                saida = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada");
                xml.salve(m1, saida + ".jff");
                break;
            case 7: //Diferença
                do {
                    caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD 1 entrada.");
                    m1 = xml.load(caminhoEntrada1 + ".jff");
                    if (m1.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m1.getEstado() == null);
                do {
                    caminhoEntrada2 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD 2 entrada.");
                    m2 = xml.load(caminhoEntrada2 + ".jff");
                    if (m2.getEstado() == null) {
                        JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                    }
                } while (m2.getEstado() == null);
                m1 = afdm.intersection(m1,afdm.complement(m2));
                saida = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada");
                xml.salve(m1, saida + ".jff");
                break;
        }

    }

    public static void main(String[] args) throws IOException {

        XMLmanipulation xml = new XMLmanipulation();
        AFDexecute exe = new AFDexecute();

       // Index.start();

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
        // ArrayList<Equivalente> eqv =  afdm.equivalents(m);
         System.out.println("ok");

        //Item 3b
        //m = afdm.minimum(m,eqv);

        //Item 5a
       // m = afdm.complement(m);

        //m = afdm.intersection(inter1,inter2);
        //diferença
       // m = afdm.intersection(inter1,afdm.complement(inter2));

        //m = afdm.union(inter1,inter2);
        xml.salve(m,"difference2.jff");


    }
}
