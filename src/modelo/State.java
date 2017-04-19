package modelo;

/**
 * Created by Diego on 19/04/2017.
 */
public class State {
    private int id;
    private String x;
    private String y;
    private boolean eFinal;
    private boolean eInicial;

    public State(int id, String x, String y, boolean eFinal, boolean eInicial) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.eFinal = eFinal;
        this.eInicial = eInicial;
    }

    public State() {
    }

    public State(int id, double v, double v1, boolean ini, boolean fin) {
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean iseFinal() {
        return eFinal;
    }

    public void seteFinal(boolean eFinal) {
        this.eFinal = eFinal;
    }

    public boolean iseInicial() {
        return eInicial;
    }

    public void seteInicial(boolean eInicial) {
        this.eInicial = eInicial;
    }
}
