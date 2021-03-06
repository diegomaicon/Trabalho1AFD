package modelo;

import java.util.ArrayList;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende94@gmail.com
 *
 *      on 19/04/2017.
 */
public class Afd {

    private ArrayList<State> estado;
    private ArrayList<Character> alfabeto;
    private ArrayList<Transition> funcTransicao;
    private ArrayList<State> eFinal;
    private State eInicial;

    public Afd() {
    }

    /**
     * teste
     *
     * @return
     */
    public ArrayList<State> geteFinal() {
        return eFinal;
    }

    public void seteFinal(ArrayList<State> eFinal) {
        this.eFinal = eFinal;
    }

    public ArrayList<State> getEstado() {
        return estado;
    }

    public void setEstado(ArrayList<State> estado) {
        this.estado = estado;
    }

    public ArrayList<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(ArrayList<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public ArrayList<Transition> getFuncTransicao() {
        return funcTransicao;
    }

    public void setFuncTransicao(ArrayList<Transition> funcTransicao) {
        this.funcTransicao = funcTransicao;
    }


    public State geteInicial() {
        return eInicial;
    }

    public void seteInicial(State eInicial) {
        this.eInicial = eInicial;
    }
}