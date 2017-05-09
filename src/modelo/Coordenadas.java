package modelo;

/**
 * Created by
 *  	Diego Maicon Silva - 11913 - diego_maicon20@hotmail.com
 *      Déborah Aparecida Resende - 11823 - deboraharesende94@gmail.com
 *
 *      on 19/04/2017.
 */
public class Coordenadas {
    private int coordenadaA;
    private int CoordenadaB;

    public Coordenadas(int coordenadaA, int getCoordenadaB) {
        this.coordenadaA = coordenadaA;
        this.CoordenadaB = getCoordenadaB;
    }

    public Coordenadas() {
    }

    public int getCoordenadaA() {
        return coordenadaA;
    }

    public int getCoordenadaB() {
        return CoordenadaB;
    }

    public void setCoordenadaA(int coordenadaA) {
        this.coordenadaA = coordenadaA;
    }

    public void setGetCoordenadaB(int getCoordenadaB) {
        this.CoordenadaB = getCoordenadaB;
    }
}
