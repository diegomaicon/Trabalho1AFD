package modelo;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende@gmail.com
 *
 *      on 19/04/2017.
 */
public class Equivalente {
    private State state1;
    private State state2;
    private boolean Status;


    public Equivalente(State state1, State state2, boolean status) {
        this.state1 = state1;
        this.state2 = state2;
        Status = status;
    }

    public boolean isStatus() {

        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public Equivalente() {
    }

    public State getState1() {
        return state1;
    }

    public void setState1(State state1) {
        this.state1 = state1;
    }

    public State getState2() {
        return state2;
    }

    public void setState2(State state2) {
        this.state2 = state2;
    }
}
