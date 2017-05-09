package modelo;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende94@gmail.com
 *
 *      on 19/04/2017.
 */
public class Transition {
    private State from;
    private State to;
    private Character read;

    public Transition(State from, State to, Character read) {
        this.from = from;
        this.to = to;
        this.read = read;
    }

    public Transition() {
    }

    public State getFrom() {
        return from;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public State getTo() {
        return to;
    }

    public void setTo(State to) {
        this.to = to;
    }

    public Character getRead() {
        return read;
    }

    public void setRead(Character read) {
        this.read = read;
    }
}
