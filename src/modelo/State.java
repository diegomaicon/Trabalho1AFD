package modelo;

/**
 * Created by Diego on 19/04/2017.
 */
public class State {
    private int id;            // Identificador do Estado
    private String x;          // Posição no Eixo X no JFLAP
    private String y;          // Posição no Eixo Y no JFLAP
    private boolean eFinal;    // Se é estado Final
    private boolean eInicial;  // Se é etado Inicial

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
