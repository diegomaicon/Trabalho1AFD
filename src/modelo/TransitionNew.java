package modelo;

/**
 * Created by Diego on 24/04/2017.
 */
public class TransitionNew {
    private StateNew from;
    private StateNew to;
    private Character read;

    public TransitionNew() {
    }

    public TransitionNew(StateNew from, StateNew to, Character read) {
        this.from = from;
        this.to = to;
        this.read = read;
    }

    public StateNew getFrom() {
        return from;
    }

    public void setFrom(StateNew from) {
        this.from = from;
    }

    public StateNew getTo() {
        return to;
    }

    public void setTo(StateNew to) {
        this.to = to;
    }

    public Character getRead() {
        return read;
    }

    public void setRead(Character read) {
        this.read = read;
    }
}
