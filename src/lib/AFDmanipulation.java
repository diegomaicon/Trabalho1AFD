package lib;

import modelo.Afd;
import modelo.State;
import modelo.Transition;

import java.util.ArrayList;

/**
 * Created by Diego on 19/04/2017.
 */
public class AFDmanipulation {
    /**
     * Construtor da Classe
     */
    public AFDmanipulation() {
    }

    /**
     *  Adicona um estado na lista de estados do autômato
     * @param m Objeto de AFD
     * @param id Identificador do estado
     * @param ini se for inicial
     * @param fin se for final
     * @return
     */
    public Afd addState(Afd m, int id, boolean ini, boolean fin) {
        ArrayList<State> listState = m.getEstado();
        listState.add(new State(id, 0.0, 0.0, ini, fin));
        m.setEstado(listState);
        return m;
    }

    /**
     *  Adiciona uuma trannsição na lista de transições do autômato
     * @param m  Objeto de AFD
     * @param from Estado origem
     * @param to   Estado destino
     * @param consume Letra do alfabeto
     * @return
     */
    public Afd addTransition(Afd m, int from, int to, Character consume) {
        ArrayList<Transition> listTransition = m.getFuncTransicao();
        ArrayList<State> listState = m.getEstado();
        Transition t = new Transition();
        for (State e : listState) {
            if (e.getId() == from) {
                t.setFrom(e);
            }

            if (e.getId() == to) {
                t.setTo(e);
            }
        }

        t.setRead(consume);
        listTransition.add(t);
        m.setFuncTransicao(listTransition);
        return m;
    }

    /**
     * Deleta estado, se exixtir na lista de estado.
     *
     * @param m Objeto do AFD
     * @param id Id do estado, que será deletado
     * @return m Objeto do AFD
     */
    public Afd deleteState(Afd m, int id) {
        ArrayList<State> listState = m.getEstado();
        State aux = new State();
        for (State e : listState) {
            if (e.getId() == id) {
                aux = e;
            }
        }
        listState.remove(aux);
        m = deleteTransition(m,aux.getId());

        m.setEstado(listState);
        return m;
    }

    /**
     * Deleta somete uma transição, se ela existir, será conferido com os parâmetros fornecidos
     *
     * @param m Objeto de AFD
     * @param from Estado origem
     * @param to   Estado destino
     * @param consume letra do alfabeto
     * @return m
     */
    public Afd deleteTransition(Afd m,int from, int to,Character consume){
        ArrayList<Transition> listTransition = m.getFuncTransicao();
        Transition aux = new Transition();
        for (Transition t:listTransition) {
            if ((t.getFrom().getId() == from) && (t.getTo().getId() == to) && t.getRead().equals(consume)){
                aux = t;
            }
        }
        listTransition.remove(aux);
        m.setFuncTransicao(listTransition);
        return m;
    }

    /**
     *  Deleta transições, que chega ao estado eccluído.
     *  Recebe como parâmetro um objeto de AFD, e o ID do estado excluído
     *
     * @param m
     * @param to
     * @return m
     */
    private Afd deleteTransition(Afd m,int to){
        ArrayList<Transition> listTransition = m.getFuncTransicao();
        ArrayList<Transition> listTransitionExclui = new ArrayList<Transition>();
        for (Transition t:listTransition) {
            if (t.getTo().getId() == to){
                listTransitionExclui.add(t);
            }
        }
        for (Transition t: listTransitionExclui) {
            listTransition.remove(t);
        }
        m.setFuncTransicao(listTransition);
        return m;
    }


    /**
     *
     *  Determina se dois estados são equivalentes ou não
     *
     *  @param state1 Estado a ser comparado
     *  @param state2 Outro estado a ser comparado
     *  @return Se state 1 é equivalente a state2
     *
     */
    public boolean equivalents(Afd m,State state1, State state2){

        boolean achou = false;

        if(state1.iseFinal() && !state2.iseFinal()){
            return achou;
        } else if (state1.iseInicial() && !state2.iseInicial()){
            return achou;
        }
        Transition aux1 = new Transition();
        Transition aux2 = new Transition();

        for(char letra:m.getAlfabeto()) {

            for (Transition transFrom: m.getFuncTransicao()) {
                if (transFrom.getFrom().getId() == state1.getId() && transFrom.getRead().equals(letra)){
                    aux1 = transFrom;
                }
                if (transFrom.getFrom().getId() == state2.getId() && transFrom.getRead().equals(letra)){
                    aux2 = transFrom;
                }

            }

            if (aux1.getTo() != null && aux2.getTo() != null) {
                if (aux1.getTo().getId() == aux2.getTo().getId()) {
                    achou = true;
                }
            } else achou = false;
        }

        return achou;
    }


}
