package bin;

import lib.AFDequivalente;
import lib.AFDexecute;
import lib.AFDmanipulation;
import lib.XMLmanipulation;
import modelo.Afd;
import modelo.Equivalente;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende@gmail.com
 *
 *      on 19/04/2017.
 */

public class Gui {

    public static void start() {
        AFDequivalente afde = new AFDequivalente();
        AFDmanipulation afdm = new AFDmanipulation();
        XMLmanipulation xml = new XMLmanipulation();
        AFDexecute exe = new AFDexecute();
        String word = "";
        String print = "";
        String caminhoEntrada1 = "";
        String caminhoEntrada2 = "";
        String saida = "",op="100",id="";
        Afd m1, m2;
        ArrayList<Equivalente> eqv ;
        do {

            op = JOptionPane.showInputDialog(null, " Digite a opção:\n [ 1 ]  Imprimir estados equivalentes (Item 3).\n" +
                    " [ 2 ]   Obter versão mínima do AFD (Item 3).\n\n" +
                    " [ 3 ]   Automatos equivalentes (Item 4).\n\n" +
                    " [ 4 ]   Complemento do Altômato (Item 5).\n" +
                    " [ 5 ]   União de Autômato (Item 5).\n" +
                    " [ 6 ]   Interseção de Autômato (Item 5).\n" +
                    " [ 7 ]   Diferença de Autômato (Item 5).\n\n" +
                    " [ 8 ]   Aceita Palavra - Testar a pertença de uma palavra na linguagem (Item 6).\n" +
                    " [ 9 ]   Aceita Palavra - Testar movimentos (Item 6).\n\n" +
                    " [ 10 ]  Adicinar Estado (Item 7)\n" +
                    " [ 11 ]  Adicinar Transição (Item 7)\n" +
                    " [ 12 ]  Deletar Estado (Item 7)\n" +
                    " [ 13 ]  Deletar Transição (Item 7)\n\n" +

                    " [ 0 ]   SAIR \n");


            switch (Integer.parseInt(op)) {
                case 1: //Estados Equivalentes
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);
                    eqv = AFDequivalente.equivalents(m1);
                    for (Equivalente e : eqv) {
                        print += "Estado -> " + e.getState1().getId() + " = " + e.getState2().getId() + "\n";
                    }
                    JOptionPane.showMessageDialog(null, print);
                    print="";
                    eqv.clear();
                    break;

                case 2://Minimização de autômato
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);
                    eqv = AFDequivalente.equivalents(m1);
                    m1 = afdm.minimum(m1, eqv);
                    saida = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada");
                    xml.salve(m1, saida + ".jff");
                    eqv.clear();
                    break;
                case 3: //Autômato Equivalente
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
                    if (AFDequivalente.equivalents(m1,m2))
                        JOptionPane.showMessageDialog(null, " SIM ", "Autômatos Equivalentes", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(null, " NÃO ", "Autômatos Equivalentes", JOptionPane.ERROR_MESSAGE);
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
                    m1 = afdm.union(m1, m2);
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
                    m1 = afdm.intersection(m1, m2);
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
                    m1 = afdm.intersection(m1, afdm.complement(m2));
                    saida = JOptionPane.showInputDialog(null, "Informe nome arquivo entrada");
                    xml.salve(m1, saida + ".jff");
                    break;

                case 8:
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD  entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);

                    word = JOptionPane.showInputDialog(null, "Informe a Palavra a ser reconhecida com os caracter " + m1.getAlfabeto().toString());

                    // Item 6a
                    if (exe.accept(m1, word))
                        JOptionPane.showMessageDialog(null, "Aceita", "Aceita", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(null, "Não Aceita", " Não Aceita", JOptionPane.ERROR_MESSAGE);
                    break;

                case 9:
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD  entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);

                    word = JOptionPane.showInputDialog(null, "Informe a Palavra a ser reconhecida com os caracter " + m1.getAlfabeto().toString());

                    // Item 6a
                    if (exe.accept(m1, word))
                        JOptionPane.showMessageDialog(null, "Aceita", "Aceita", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(null, "Não Aceita", " Não Aceita", JOptionPane.ERROR_MESSAGE);
                    break;


                case 10: //Adiciona Estado
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD  entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);

                    int fin = JOptionPane.showConfirmDialog(null,"Estado Final ?","",JOptionPane.YES_NO_OPTION);
                    if (fin == 1)
                        afdm.addState(m1, m1.getEstado().size()+1,false);
                    else
                        afdm.addState(m1, m1.getEstado().size()+1,true);

                    xml.salve(m1, caminhoEntrada1 + ".jff");
                    JOptionPane.showMessageDialog(null,"Estado Adicionado !");
                    break;


                case 11: // Adiciona Transação
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD  entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);

                    m1 = afdm.addTransition(m1,Integer.parseInt(JOptionPane.showInputDialog(null,"Saindo do estado. ")),
                            Integer.parseInt(JOptionPane.showInputDialog(null,"Chegando do estado. ")),
                            JOptionPane.showInputDialog(null,"Gastando, escolha: "+ m1.getAlfabeto().toString()).charAt(0));
                    xml.salve(m1, caminhoEntrada1 + ".jff");
                    JOptionPane.showMessageDialog(null,"Transação Adicionada !");
                    break;
                case 12://Deleta Estado
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD  entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);
                    id = JOptionPane.showInputDialog(null,"Número Autômato");
                    afdm.deleteState(m1,Integer.parseInt(id));
                    xml.salve(m1, caminhoEntrada1 + ".jff");
                    JOptionPane.showMessageDialog(null,"Estado Deletado !");
                    break;
                case 13: // Deleta Transação
                    do {
                        caminhoEntrada1 = JOptionPane.showInputDialog(null, "Informe nome arquivo AFD  entrada.");
                        m1 = xml.load(caminhoEntrada1 + ".jff");
                        if (m1.getEstado() == null) {
                            JOptionPane.showMessageDialog(null, "Arquivo inexistente!");
                        }
                    } while (m1.getEstado() == null);

                    m1 = afdm.deleteTransition(m1,Integer.parseInt(JOptionPane.showInputDialog(null,"Saindo do estado. ")),
                            Integer.parseInt(JOptionPane.showInputDialog(null,"Chegando do estado. ")),
                            JOptionPane.showInputDialog(null,"Gastando, escolha: "+ m1.getAlfabeto().toString()).charAt(0));
                    xml.salve(m1, caminhoEntrada1 + ".jff");
                    JOptionPane.showMessageDialog(null,"Transação Deletado !");
                    break;
            }

        } while (Integer.parseInt(op) != 0) ;

        return;
    }

}
