package modelo;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende94@gmail.com
 *
 *      on 19/04/2017.
 */
public class StateNew extends State {
    private int n1;
    private int n2;

    public StateNew() {
    }

    public StateNew(int n1, int n2,int id, String x, String y, boolean eFinal, boolean eInicial) {
        super(id, x, y, eFinal, eInicial);
        this.n1 = n1;
        this.n2 = n2;
    }

    public StateNew(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public int getN1() {

        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }
}
