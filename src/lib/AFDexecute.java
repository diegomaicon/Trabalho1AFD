package lib;

import modelo.Afd;
import modelo.State;
import modelo.Transition;

import java.util.ArrayList;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende@gmail.com
 *
 *      on 19/04/2017.
 */
public class AFDexecute {

    /**
     *  Executa o autômato e percorre as transições para cada caracter da Palavra.
     *  Se o caracter da Palavra não estiver no alfabeto, já retorna False.
     *  Se não há transição com o caracter da Palavra, já retorna False.
     *  Se percorreu toda palavra e estado atual for Final, retorna True.
     *  Se percorreu toda palavra e estado atual NÂO for Final, retorna False.
     *
     * @param m Objeto do AFD
     * @param word Palavra String
     * @return Boolean
     */

    public boolean accept(Afd m,String word) {
        char palavra[] = word.toCharArray();
        ArrayList<Transition> listTrans = m.getFuncTransicao();
        ArrayList<Character> alfabeto = m.getAlfabeto();

        State estadoAtual = m.geteInicial();  //Estabelece oestado inicialpara efetuara primeiraverificação detroca de

        for (char w :palavra) {
            if (alfabeto.contains(w)) {
                for (Transition t : listTrans) {
                    if (estadoAtual.getId() == t.getFrom().getId()) {
                        if (t.getRead().equals(w)) {
                            estadoAtual = t.getTo();
                            break;
                        }
                    }else return false;
                }
            } else return false;
        }

        if (estadoAtual.iseFinal()){
            return true;
        } else return false;

    }


   public State move(Afd m,State estado ,String word){
       char palavra[] = word.toCharArray();
       ArrayList<Transition> listTrans = m.getFuncTransicao();
       ArrayList<Character> alfabeto = m.getAlfabeto();
       State estadoAtual = estado;

       for (char w :palavra) {
           if (alfabeto.contains(w)) {
               for (Transition t : listTrans) {
                   if (estadoAtual.getId() == t.getFrom().getId()) {
                       if (t.getRead().equals(w)) {
                           estadoAtual = t.getTo();
                           break;
                       }
                   }else return estadoAtual;
               }
           } else return estadoAtual;
       }
        return estadoAtual;

   }
}
