package modelo;

/**
 * Created by Debora on 02/05/2017.
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
