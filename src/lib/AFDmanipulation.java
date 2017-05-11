package lib;

import modelo.*;

import java.util.ArrayList;
/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende94@gmail.com
 *
 *      on 19/04/2017.
 */
public class AFDmanipulation {
    /**
     * Construtor da Classe
     */
    public AFDmanipulation() {
    }

    /**
     *  Adiciona um estado na lista de estados do autômato
     *
     * @param m Objeto de AFD
     * @param id Identificador do estado
     * @param fin se for final
     * @return
     */
    public Afd addState(Afd m, int id, boolean fin) {
        ArrayList<State> listState = m.getEstado();
        listState.add(new State(id,"0.0","0.0",fin, false));
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
     * Deleta estado, se existir na lista de estado.
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
     * Deleta somente uma transição, se ela existir, será verificado com os parâmetros fornecidos
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
     * @param from
     * @return m
     */
    private Afd deleteTransition(Afd m,int id){
        ArrayList<Transition> listTransition = m.getFuncTransicao();
        ArrayList<Transition> listTransitionExclui = new ArrayList<Transition>();

        for (Transition t:listTransition) {
            if (t.getFrom().getId() == id){
                listTransitionExclui.add(t);
            }
        }

        for (Transition t:listTransition) {
            if (t.getTo().getId() == id){
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
     *  Algoritmo de minimidazação, exclui os estados equivalentes e atualiza
     *  transição para o ID do estado Criado
     * @param m
     * @param listEquiv
     * @return
     */
    public Afd minimum(Afd m,ArrayList<Equivalente> listEquiv) {
        State novo = new State();
        Transition t =null;
        ArrayList<Transition> listExcluiTrans = null;


        for (int i = 0; i < listEquiv.size(); i++) {
            listExcluiTrans = new ArrayList<Transition>();
            //Cria novos estados de acordo om a equivalencia
            for (Transition trans : m.getFuncTransicao()) {
                String id ="";
                if (listEquiv.get(i).getState2().getId() == trans.getFrom().getId()) {
                    id = listEquiv.get(i).getState1().getId() + "" + listEquiv.get(i).getState2().getId();
                    novo.setId(Integer.parseInt(id));
                    novo.seteInicial(listEquiv.get(i).getState1().iseInicial());
                    novo.seteFinal(listEquiv.get(i).getState1().iseFinal());
                    novo.setX(listEquiv.get(i).getState1().getX());
                    novo.setY(listEquiv.get(i).getState1().getY());
                    listExcluiTrans.add(trans);
                }
            }
            //deleta transações
            for (Transition t1: listExcluiTrans) {
                m = deleteTransition(m, t1.getFrom().getId(), t1.getTo().getId(), t1.getRead());
            }
            //altera transações para novo estado
            m = alteraTransicaoFrom(m,listEquiv.get(i).getState1().getId(),novo.getId());
            //deleta um dos estados equivalentes.
            m = deleteState(m,listEquiv.get(i).getState2().getId(),novo.getId());

        }
        return m;
    }

    /**
     *  Realiza complemeto de um autômato.
     *  Estados final, viram não finais e não finais viram finais.
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

    /**
     * Intercessão entre dois Autômatos.
     * @param m1
     * @param m2
     * @return
     */
    public Afd intersection(Afd m1,Afd m2) {
        Afd novoAfd = new Afd();
        ArrayList<StateNew> novoStates = new ArrayList<StateNew>();
        ArrayList<TransitionNew> novoTrans = new ArrayList<TransitionNew>();
        //Cria novos estados
        novoStates = criaNovosStatesInter(m1, m2, novoStates);


        ArrayList<Transition> listTransM1 = m1.getFuncTransicao();
        ArrayList<Transition> listTransM2 = m2.getFuncTransicao();

        //VErificando prapara qual estado a nova transição vai
        int vai1=-1,vai2=-1;
        for (Character letra:m1.getAlfabeto()) {
             for (StateNew stateNew : novoStates) {
                 for (Transition trans1 : listTransM1) {
                     if (stateNew.getN1() == trans1.getFrom().getId() && trans1.getRead().equals(letra)){
                         vai1 = trans1.getTo().getId();
                         break;
                     }
                 }

                 for (Transition trans2 : listTransM2) {
                     if (stateNew.getN2() == trans2.getFrom().getId() && trans2.getRead().equals(letra)){
                         vai2 = trans2.getTo().getId();
                         break;
                     }
                 }
                    //adiciona transição do tipo nova
                    novoTrans.add(new TransitionNew(stateNew,new StateNew(vai1,vai2),letra));

             }
        }
        //Cria nova lista de estados.
        ArrayList<State> listState = new ArrayList<State>();
        for (StateNew enew:novoStates) {
            String id = "1"+enew.getN1()+""+enew.getN2();
            listState.add(new State(Integer.parseInt(id),enew.getX(),enew.getY(),enew.iseFinal(),enew.iseInicial()));
        }
        novoAfd.setEstado(listState);
        //Cria Lista de novas transições
        ArrayList<Transition> listTransition = new ArrayList<Transition>();
        for (TransitionNew tnew:novoTrans){
             String idf  = "1"+tnew.getFrom().getN1()+""+tnew.getFrom().getN2();
             String idt  = "1"+tnew.getTo().getN1()+""+tnew.getTo().getN2();
             listTransition.add(new Transition(new State(Integer.parseInt(idf),tnew.getFrom().getX(),tnew.getFrom().getY(),tnew.getFrom().iseFinal(),tnew.getFrom().iseInicial()),
                                               new State(Integer.parseInt(idt),tnew.getFrom().getX(),tnew.getFrom().getY(),tnew.getFrom().iseFinal(),tnew.getFrom().iseInicial()),
                                               tnew.getRead()));
        }
        novoAfd.setFuncTransicao(listTransition);
        //Seta novo estado inicial
        for (State e :listState){
            if(e.iseInicial()){
                novoAfd.seteInicial(e);
                break;
            }
        }
        //Cria Lista de novos estados finais
        ArrayList<State> listStatef = new ArrayList<State>();
        for (State e :listState){
            if(e.iseFinal()){
                listStatef.add(e);
            }
        }
        novoAfd.seteFinal(listStatef);
        novoAfd.setAlfabeto(m1.getAlfabeto());
        novoAfd = eliminaStateNoTo(novoAfd);
        return novoAfd;
    }

    /**
     *  União de doia autômatos
     * @param m1
     * @param m2
     * @return
     */
    public Afd union(Afd m1,Afd m2) {
        Afd novoAfd = new Afd();
        ArrayList<StateNew> novoStates = new ArrayList<StateNew>();
        ArrayList<TransitionNew> novoTrans = new ArrayList<TransitionNew>();

        novoStates = criaNovosStatesUnion(m1, m2, novoStates);

        ArrayList<Transition> listTransM1 = m1.getFuncTransicao();
        ArrayList<Transition> listTransM2 = m2.getFuncTransicao();

        //Verificando para qual estado a nova transição vai
        int vai1=-1,vai2=-1;
        for (Character letra:m1.getAlfabeto()) {
            for (StateNew stateNew : novoStates) {
                for (Transition trans1 : listTransM1) {
                    if (stateNew.getN1() == trans1.getFrom().getId() && trans1.getRead().equals(letra)){
                        vai1 = trans1.getTo().getId();
                        break;
                    }
                }

                for (Transition trans2 : listTransM2) {
                    if (stateNew.getN2() == trans2.getFrom().getId() && trans2.getRead().equals(letra)){
                        vai2 = trans2.getTo().getId();
                        break;
                    }
                }
                //adiciona transição do tipo nova
                novoTrans.add(new TransitionNew(stateNew,new StateNew(vai1,vai2),letra));

            }
        }
        //Cria nova lista de estados.
        ArrayList<State> listState = new ArrayList<State>();
        for (StateNew enew:novoStates) {
            String id = "1"+enew.getN1()+""+enew.getN2();
            listState.add(new State(Integer.parseInt(id),enew.getX(),enew.getY(),enew.iseFinal(),enew.iseInicial()));
        }
        novoAfd.setEstado(listState);
        //Cria Lista de novas transições
        ArrayList<Transition> listTransition = new ArrayList<Transition>();
        for (TransitionNew tnew:novoTrans){
            String idf  = "1"+tnew.getFrom().getN1()+""+tnew.getFrom().getN2();
            String idt  = "1"+tnew.getTo().getN1()+""+tnew.getTo().getN2();
            listTransition.add(new Transition(new State(Integer.parseInt(idf),tnew.getFrom().getX(),tnew.getFrom().getY(),tnew.getFrom().iseFinal(),tnew.getFrom().iseInicial()),
                    new State(Integer.parseInt(idt),tnew.getFrom().getX(),tnew.getFrom().getY(),tnew.getFrom().iseFinal(),tnew.getFrom().iseInicial()),
                    tnew.getRead()));
        }
        novoAfd.setFuncTransicao(listTransition);
        //Seta novo estado inicial
        for (State e :listState){
            if(e.iseInicial()){
                novoAfd.seteInicial(e);
                break;
            }
        }
        //Cria Lista de novos estados finais
        ArrayList<State> listStatef = new ArrayList<State>();
        for (State e :listState){
            if(e.iseFinal()){
                listStatef.add(e);
            }
        }
        novoAfd.seteFinal(listStatef);
        novoAfd.setAlfabeto(m1.getAlfabeto());
        novoAfd = eliminaStateNoTo(novoAfd);
        return novoAfd;
    }

    /**
     *   Cria novos estados para o algotitmo de intercessão.
     * @param m1
     * @param m2
     * @param novoStates
     * @return
     */
    private ArrayList<StateNew> criaNovosStatesInter(Afd m1, Afd m2, ArrayList<StateNew> novoStates) {
        //Cria novos Estados indice (i,j)
        for (State em1 : m1.getEstado()) {
            for (State em2 : m2.getEstado()) {

                if (em1.iseInicial() && em2.iseInicial()){
                    if (em1.iseFinal() && em2.iseFinal())
                         novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), true, em1.iseInicial()));
                    else novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), false, em1.iseInicial()));
                } else {
                    //se os dois estados forem final cria um novo, sendo ele final
                    if (em1.iseFinal() && em2.iseFinal())
                        novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), true, false));
                    else novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), false, false));
                }
            }
        }
        return novoStates;
    }

    /**
     *  Cria novos estados para algoritmo de União
     * @param m1
     * @param m2
     * @param novoStates
     * @return
     */
    private ArrayList<StateNew> criaNovosStatesUnion(Afd m1, Afd m2, ArrayList<StateNew> novoStates) {
        //Cria novos Estados indice (i,j)
        for (State em1 : m1.getEstado()) {
            for (State em2 : m2.getEstado()) {
                if (em1.iseInicial() && em2.iseInicial()){
                    if(em1.iseFinal() || em2.iseFinal())
                         novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), true, em1.iseInicial()));
                    else novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), false, em1.iseInicial()));
                } else {
                    //Se um ou outro é final, cria um novo estado Final
                    if (em1.iseFinal() || em2.iseFinal())
                        novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), true, false));
                    else novoStates.add(new StateNew(em1.getId(),em2.getId(),-1, em1.getX(), em1.getY(), false, false));
                }
            }
        }
        return novoStates;
    }

    /**
     * Elimina Estados que não tem Chegada. Nenhuma transição chega nele, a não ser
     * estado inicial.
     *
     * @param m
     * @return
     */
    private Afd eliminaStateNoTo(Afd m){

        ArrayList<State> listExclui;
        boolean flag = true;
        boolean aux = false;
        while (flag) {
            listExclui = new ArrayList<State>();
            for (State e : m.getEstado()) {
                aux = false;
                for (Transition t : m.getFuncTransicao()) {
                    if (e.getId() == t.getTo().getId()) {
                        aux = true;
                        break;
                    }
                }
                if (!aux && !e.iseInicial()) {
                    listExclui.add(e);
                }
            }
            //Exclui Estado que não tem aresta de chegada e aresta que sai deste
            if(listExclui.size() !=0 ) {
                for (State e : listExclui) {
                    m = deleteState(m, e.getId());
                }
            } else {
                flag = false;
            }
        }
        return m;
    }
}
