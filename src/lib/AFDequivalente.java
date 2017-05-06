package lib;

import modelo.*;

import java.util.ArrayList;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende@gmail.com
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

    public static ArrayList<AFDequivalente> CarregaAfdMinimizacao(ArrayList<State> listaEstados) {
        ArrayList<AFDequivalente> tabelaMinimizacao = new ArrayList<>();
        for (State s : listaEstados) {
            for (State s2 : listaEstados) {
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
     *  Estados equivalentes
     * @param afd
     * @return ArrayList<Equivalente>
     */
    public static ArrayList<Equivalente> equivalents(Afd afd) {

        ArrayList<AFDequivalente> tabelaMinimizacao = CarregaAfdMinimizacao(afd.getEstado());
        for (AFDequivalente t : tabelaMinimizacao) {
            if (finalState(afd, t.getId().getCoordenadaA()) != finalState(afd, t.getId().getCoordenadaB())) {
                t.setEquivalente(false);
            }
        }
        ArrayList<Transition> listTransition = afd.getFuncTransicao();
        ArrayList<Character> alfabeto = afd.getAlfabeto();
        for (AFDequivalente t : tabelaMinimizacao) {
            if (t.isEquivalente() == true) {
                for (Character a : alfabeto) {
                    int num1 = verificaDestinoState(listTransition, t.getId().getCoordenadaA(), a);
                    int num2 = verificaDestinoState(listTransition, t.getId().getCoordenadaB(), a);
                    if ((verificaEqStates(tabelaMinimizacao, num1, num2) == false) && t.isEquivalente() == true) {
                        t.setEquivalente(false);
                    } else {
                        t.marcarCoordenadas.add(new Coordenadas(num1, num2));
                    }
                }
                ArrayList<Coordenadas> marcar = t.getMarcarCoordenadas();
                for (Coordenadas c : marcar) {
                    if ((verificarMarcacaoEq(tabelaMinimizacao, c.getCoordenadaA(), c.getCoordenadaB())) == false) {
                        t.setEquivalente(false);
                    }
                }

            }
        }
        for (AFDequivalente t : tabelaMinimizacao) {
            if (t.isEquivalente() == true && t.getMarcarCoordenadas() != null) {
                ArrayList<Coordenadas> marcar = t.getMarcarCoordenadas();
                for (Coordenadas c : marcar) {
                    if ((verificarMarcacaoEq(tabelaMinimizacao, c.getCoordenadaA(), c.getCoordenadaB())) == false) {
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
     * Autômatos equivalentes
     * @param afd1
     * @param afd2
     * @return boolean
     */
    public static  boolean equivalents(Afd afd1, Afd afd2){

        ArrayList<State> novoState = afd1.getEstado();
        novoState.addAll(retornaNovosStates(afd2.getEstado(),afd1.getEstado().size()));
        afd1.setEstado(novoState);
        ArrayList<Transition> novaFuncaoTransicao = afd1.getFuncTransicao();
        novaFuncaoTransicao.addAll(afd2.getFuncTransicao());
        afd1.setFuncTransicao(novaFuncaoTransicao);
        ArrayList<State> finalStates = afd1.geteFinal();
        finalStates.addAll(afd2.geteFinal());
        ArrayList<Equivalente> equivalentes = equivalents(afd1);
        for (Equivalente e : equivalentes){
          //  System.out.println("estados iniciais eq: " +e.getState1().getId()+"-"+e.getState2().getId());
            if (e.getState1().iseInicial() && e.getState2().iseInicial()){
                return true;
            }
        }


        return false;
    }

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

    private static boolean verificarMarcacaoEq(ArrayList<AFDequivalente> tabelaMinimizacao, int coordenadaA, int coordenadaB) {
        for (AFDequivalente t : tabelaMinimizacao) {
            if ((t.getId().getCoordenadaA() == coordenadaA && t.getId().getCoordenadaB() == coordenadaB) || (t.getId().getCoordenadaA() == coordenadaB && t.getId().getCoordenadaB() == coordenadaA)) {
                return t.isEquivalente();
            }
        }
        return true;
    }

    private static int verificaDestinoState(ArrayList<Transition> listaTransicao, int idStateFrom, char letra) {
        for (Transition t : listaTransicao) {
            if (t.getFrom().getId() == idStateFrom && (t.getRead() == letra)) {
                return t.getTo().getId();
            }
        }
        return -1;
    }

    private static boolean verificaEqStates(ArrayList<AFDequivalente> tabelaMinimizacao, int coordenadaA, int coordenadaB) {
        for (AFDequivalente t : tabelaMinimizacao) {
            if (((t.getId().getCoordenadaA() == coordenadaA) && t.getId().getCoordenadaB() == coordenadaB) || ((t.getId().getCoordenadaA() == coordenadaB) && t.getId().getCoordenadaB() == coordenadaA)) {
                return t.isEquivalente();
            }
        }
        return true;
    }

    private static boolean finalState(Afd afd, int idEstado) {
        ArrayList<State> listaFinalState = afd.geteFinal();
        for (State l : listaFinalState) {
            if (l.getId() == idEstado) {
                return true;
            }
        }
        return false;
    }

    private static boolean verificaExistencia(ArrayList<AFDequivalente> tabelaMinimizacao, int coordenadaA, int coordenadaB) {
        for (AFDequivalente t : tabelaMinimizacao) {
            if (((t.getId().getCoordenadaA() == coordenadaA) && (t.getId().getCoordenadaB() == coordenadaB)) || ((t.getId().getCoordenadaA() == coordenadaB) && (t.getId().getCoordenadaB() == coordenadaA))) {
                return true;
            }
        }
        return false;
    }

    private static ArrayList<State> retornaNovosStates(ArrayList<State> states, int incremento){

        for (State s: states ){
            s.setId(s.getId()+incremento);
        }
        return (states);
    }
}
