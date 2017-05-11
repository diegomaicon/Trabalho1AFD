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
public class AFDequivalente {
    private Coordenadas idStates;
    private boolean equivalente;
    private ArrayList<Coordenadas> marcarCoordenadas = new ArrayList<Coordenadas>();

    public AFDequivalente(Coordenadas id, boolean equivalente, ArrayList<Coordenadas> marcarCoordenadas) {
        this.idStates = id;
        this.equivalente = equivalente;
        this.marcarCoordenadas = marcarCoordenadas;
    }

    public AFDequivalente() {
    }

    public Coordenadas getId() {
        return idStates;
    }

    public void setId(Coordenadas id) {
        this.idStates = id;
    }

    public boolean isEquivalente() {
        return equivalente;
    }

    public void setEquivalente(boolean equivalente) {
        this.equivalente = equivalente;
    }

    public ArrayList<Coordenadas> getMarcarCoordenadas() {
        return marcarCoordenadas;
    }

    public void setMarcarCoordenadas(ArrayList<Coordenadas> marcar) {
        this.marcarCoordenadas = marcar;
    }

    /**
     * Pega as informações necessarias do AFD e cria uma Lista de AFDEquivalente
     * @param listaEstados do AFD
     * @return Lista AFDequivalente
     */
    public static ArrayList<AFDequivalente> CarregaAfdMinimizacao(ArrayList<State> listaEstados) {
        ArrayList<AFDequivalente> tabelaMinimizacao = new ArrayList<>();
        for (State s : listaEstados) {
            for (State s2 : listaEstados) {
                //Verifica se os identificadores não são iguais e se já existe objeto com essas coordenadas
                if ((s.getId() != s2.getId()) && (!verificaExistencia(tabelaMinimizacao, s.getId(), s2.getId()))) {
                    AFDequivalente aux = new AFDequivalente();
                    Coordenadas c = new Coordenadas(s.getId(), s2.getId());
                    aux.setId(c);
                    aux.setEquivalente(true);
                    tabelaMinimizacao.add(aux);
                }
            }
        }
        return tabelaMinimizacao;
    }

    /**
     *  Verifica Estados equivalentes
     * @param afd
     * @return ArrayList<Equivalente> Lista de estados que são equivalentes
     */
    public static ArrayList<Equivalente> equivalents(Afd afd) {
        ArrayList<AFDequivalente> tabelaMinimizacao = CarregaAfdMinimizacao(afd.getEstado());
        for (AFDequivalente t : tabelaMinimizacao) {
            if (finalState(afd, t.getId().getCoordenadaA()) != finalState(afd, t.getId().getCoordenadaB())) {
                //Marca como false os triviais - Se um é final e outro não..
                t.setEquivalente(false);
            }
        }
        ArrayList<Transition> listTransition = afd.getFuncTransicao();
        ArrayList<Character> alfabeto = afd.getAlfabeto();
        for (AFDequivalente t : tabelaMinimizacao) {
            if (t.isEquivalente() == true) {
                for (Character a : alfabeto) {
                    //Partindo de determinado Estado, consumindo A, qual sera o destino
                    int num1 = verificaDestinoState(listTransition, t.getId().getCoordenadaA(), a);
                    int num2 = verificaDestinoState(listTransition, t.getId().getCoordenadaB(), a);
                    //Verifica se o objeto que contem as Coordenadas de destino está marcado
                    if ((verificaEqStates(tabelaMinimizacao, num1, num2) == false) && t.isEquivalente() == true) {
                        t.setEquivalente(false);
                    } else {
                        t.marcarCoordenadas.add(new Coordenadas(num1, num2));
                    }
                }
                ArrayList<Coordenadas> marcar = t.getMarcarCoordenadas();
                for (Coordenadas c : marcar) {
                    if ((verificaEqStates(tabelaMinimizacao, c.getCoordenadaA(), c.getCoordenadaB())) == false) {
                        t.setEquivalente(false);
                    }
                }

            }
        }
        for (AFDequivalente t : tabelaMinimizacao) {
            if (t.isEquivalente() == true && t.getMarcarCoordenadas() != null) {
                ArrayList<Coordenadas> marcar = t.getMarcarCoordenadas();
                for (Coordenadas c : marcar) {
                    if ((verificaEqStates(tabelaMinimizacao, c.getCoordenadaA(), c.getCoordenadaB())) == false) {
                        t.setEquivalente(false);
                    }
                }

            }

        }
        ArrayList<Equivalente> listaEquivalentes = new ArrayList<Equivalente>();
        for (AFDequivalente t : tabelaMinimizacao) {
            if (t.isEquivalente() == true) {
                State state1 = (retornaState(afd, t.getId().getCoordenadaA()));
                State state2 = (retornaState(afd, t.getId().getCoordenadaB()));
                Equivalente equivalente = new Equivalente(state1, state2, true);
                listaEquivalentes.add(equivalente);
            }
        }
        return listaEquivalentes;
    }

    /**
     *
     * Verifica se dois Autômatos são equivalentes
     * @param afd1
     * @param afd2
     * @return boolean
     */
    public static  boolean equivalents(Afd afd1, Afd afd2){
        ArrayList<State> novoState = afd1.getEstado();
        novoState.addAll(retornaNovosStates(afd2.getEstado(),afd1.getEstado().size())); //Junta em apenas uma lista os estados do AFD1 e AFD2, atualizando os identificadores do AFD
        afd1.setEstado(novoState);
        ArrayList<Transition> novaFuncaoTransicao = afd1.getFuncTransicao();
        novaFuncaoTransicao.addAll(afd2.getFuncTransicao()); //Junta as Listas de Transição
        afd1.setFuncTransicao(novaFuncaoTransicao);
        ArrayList<State> finalStates = afd1.geteFinal();
        finalStates.addAll(afd2.geteFinal()); //Junta a Lista de Estados Finais
        ArrayList<Equivalente> equivalentes = equivalents(afd1);
        for (Equivalente e : equivalentes){
            if (e.getState1().iseInicial() && e.getState2().iseInicial()){ //Se o objeto que contem as coordenadas dos estados iniciais não tiverem sido marcados, então dois automatos são equivalentes
                return true;
            }
        }
        return false;
    }


    /**
     * Pega o identificador do Estado de determinado AFD e retorna o objeto
     * @param afd automato
     * @param idState Identificador do Estado
     * @return State Objeto do tipo State
     */
    private static State retornaState(Afd afd, int idState){
        State state = new State();
        for (State s : afd.getEstado()){
            if(s.getId()==idState){
                state.setId(s.getId());
                state.setX(s.getX());
                state.setY(s.getY());
                state.seteFinal(s.iseFinal());
                state.seteInicial(s.iseInicial());
            }
        }
        return state;
    }


    /**
     * A partir de um Estado, consumindo um Character, qual será o destino..
     * @param listaTransicao Lista de transição do AFD
     * @param idStateFrom Estado de partida
     * @param letra Consumo
     * @return Int Identificador do estado destino
     */
    private static int verificaDestinoState(ArrayList<Transition> listaTransicao, int idStateFrom, char letra) {
        for (Transition t : listaTransicao) {
            if (t.getFrom().getId() == idStateFrom && (t.getRead() == letra)) {
                return t.getTo().getId();
            }
        }
        return -1;
    }

    /**
     * Verifica se o objeto com aquelas coordenadas são equivalentes
     * @param tabelaMinimizacao
     * @param coordenadaA
     * @param coordenadaB
     * @return boolean se é ou nao equivalente
     */
    private static boolean verificaEqStates(ArrayList<AFDequivalente> tabelaMinimizacao, int coordenadaA, int coordenadaB) {
        for (AFDequivalente t : tabelaMinimizacao) {
            if (((t.getId().getCoordenadaA() == coordenadaA) && t.getId().getCoordenadaB() == coordenadaB) || ((t.getId().getCoordenadaA() == coordenadaB) && t.getId().getCoordenadaB() == coordenadaA)) {
                return t.isEquivalente();
            }
        }
        return true;
    }

    /**
     * Verifica se o estado é final.
     * É consultado se ele está presente na lista de Estados finais do automato
     * @param afd Automato
     * @param idEstado Identificador do estado que se deseja verificar
     * @return Boolean
     */
    private static boolean finalState(Afd afd, int idEstado) {
        ArrayList<State> listaFinalState = afd.geteFinal();
        for (State l : listaFinalState) {
            if (l.getId() == idEstado) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se um objeto com aquelas Coordenadas já existe
     * @param tabelaMinimizacao Lista de objetos AFDequivalente
     * @param coordenadaA
     * @param coordenadaB
     * @return Boolean
     */
    private static boolean verificaExistencia(ArrayList<AFDequivalente> tabelaMinimizacao, int coordenadaA, int coordenadaB) {
        for (AFDequivalente t : tabelaMinimizacao) {
            if (((t.getId().getCoordenadaA() == coordenadaA) && (t.getId().getCoordenadaB() == coordenadaB)) || ((t.getId().getCoordenadaA() == coordenadaB) && (t.getId().getCoordenadaB() == coordenadaA))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Para realizar a equivalencia de dois AFDs é necessario junta-los
     * Para que dois estados não fiquem com o mesmo identificador, é passado uma lista de States e um valor para incrementar cada identificador
     * Este incremento é a quantidade de estados que o outro AFD possui.
     * @param states Lista de Estados que será atualizado
     * @param incremento Valor que será somado em cada Identificador
     * @return Lista de State já atualizada
     */
    private static ArrayList<State> retornaNovosStates(ArrayList<State> states, int incremento){
        for (State s: states ){
            s.setId(s.getId()+incremento);
        }
        return (states);
    }
}
