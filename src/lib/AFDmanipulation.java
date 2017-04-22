package lib;

import modelo.Afd;
import modelo.Equivalente;
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
    public Afd deleteState(Afd m, int id, int idnovo) {
        ArrayList<State> listState = m.getEstado();
        State aux = new State();
        for (State e : listState) {
            if (e.getId() == id) {
                aux = e;
            }
        }
        listState.remove(aux);
        m = alteraTransicaoTo(m,id,idnovo);
        m.setEstado(listState);
        return m;
    }

    /**
     * Atualiza transações From, para novo ID de estado criado
     * @param m
     * @param id
     * @param idnovo
     * @return
     */
    private Afd alteraTransicaoFrom(Afd m,int id,int idnovo){
        ArrayList<Transition> listTransition = m.getFuncTransicao();
        for (Transition t:listTransition) {
            if(t.getFrom().getId() == id) {
                t.getFrom().setId(idnovo);
            }
        }

        m.setFuncTransicao(listTransition);
        return m;
    }

    /**
     *   Atualiza transações TO, para novo ID de estado criado
     * @param m
     * @param id
     * @param idnovo
     * @return
     */
    private Afd alteraTransicaoTo(Afd m,int id,int idnovo){
        ArrayList<Transition> listTransition = m.getFuncTransicao();
        for (Transition t:listTransition) {
            if(t.getTo().getId() == id) {
                t.getTo().setId(idnovo);
            }
        }

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
     *  Deleta transições, que chega ao estado excluído.
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
     *  Determina se dois estados são equivalentes ou não e retorna os dois se foram equivalentes.
     *
     *  @param state1 Estado a ser comparado
     *  @param state2 Outro estado a ser comparado
     *  @return Se state 1 é equivalente a state2
     *
     */
    private Equivalente states2Equi(Afd m,State state1, State state2){
        Equivalente equi = new Equivalente();

        if(state1.iseFinal() && !state2.iseFinal()){
            return equi;
        } else if (state1.iseInicial() && !state2.iseInicial()){
            return equi;
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

                    equi.setState1(state1);
                    equi.setState2(state2);
                    return equi;
                }
            }
        }

        return equi;
    }

    /**
     *  Função te retorna uma liata com dois estados equivalentes entre si,
     *  cada componente da lista tem dois estados, estado1 e estado2, ambos equivalentes.
     *
     * @param m
     * @return
     */
    public ArrayList<Equivalente> equivalents(Afd m){
        ArrayList<State> listaState = m.getEstado();
        ArrayList<Equivalente> eEquiv = new ArrayList<Equivalente>();
        Equivalente aux;

        for (int i = 0; i < listaState.size()-1 ; i++) {
            for (int j = i+1; j <listaState.size() ; j++) {
              aux = states2Equi(m,listaState.get(i),listaState.get(j));
              if (aux.getState1() != null) {
                  eEquiv.add(aux);
              }
            }
        }

        return eEquiv;
    }

    /**
     *  Algoritimo de minimidazação, exclui um dos estados equivalentes e atualiza
     *  transição para o novo ID de estado Criado
     * @param m
     * @param listEquiv
     * @return
     */
    public Afd minimum(Afd m,ArrayList<Equivalente> listEquiv) {
        State novo = new State();
        Transition t;
        ArrayList<Transition> listExcluiTrans;

        for (int i = 0; i < listEquiv.size(); i++) {
            listExcluiTrans =new ArrayList<Transition>();

            for (Transition trans : m.getFuncTransicao()) {

                if (listEquiv.get(i).getState2().getId() == trans.getFrom().getId()) {
                    String id = listEquiv.get(i).getState1().getId() + "" + listEquiv.get(i).getState2().getId();
                    novo.setId(Integer.parseInt(id));
                    novo.seteInicial(listEquiv.get(i).getState1().iseInicial());
                    novo.seteInicial(listEquiv.get(i).getState1().iseFinal());
                    novo.setX(listEquiv.get(i).getState1().getX());
                    novo.setY(listEquiv.get(i).getState1().getY());
                    listExcluiTrans.add(trans);
                }
            }

            for (Transition t1: listExcluiTrans) {
                m = deleteTransition(m, t1.getFrom().getId(), t1.getTo().getId(), t1.getRead());

            }
            m = alteraTransicaoFrom(m,listEquiv.get(i).getState1().getId(),novo.getId());
            m = deleteState(m,listEquiv.get(i).getState2().getId(),novo.getId());
        }
        return m;
    }

    /**
     *  Metodo que realiza complemeto de um autômato.
     *  estados final, viram não finais e não finais viram finais.
     *
     * @param m
     * @return
     */
    public Afd complement(Afd m){
        for (State e :m.getEstado()) {
            if (e.iseFinal()){
                e.seteFinal(false);
            }
            else e.seteFinal(true);
        }
        return m;
    }
}
