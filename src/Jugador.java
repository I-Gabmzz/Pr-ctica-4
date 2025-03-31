package src;

import java.util.ArrayList;
import java.util.Random;

public class Jugador {
    private int puntuacionTotal;
    private int puntuacion;
    private ArrayList<Dado> dadosTirados;
    private ArrayList<Dado> dadosTomados;
    private int dadosDisponibles;


    public Jugador() {
        this.puntuacionTotal = 0;
        this.puntuacion = 0;
        this.dadosTirados = new ArrayList<>();
        this.dadosTomados = new ArrayList<>();
    }

    public void tirarDados(int dadosDisponibles) {
        Random rand = new Random();
        int delta = 150;
        dadosTirados.clear();
        for (int i = 0; i < dadosDisponibles; i++) {
            int xPosicion = 75 + i *  delta ;
            int yPosicion = 450 ;
            Dado dado = new Dado();
            dado.lanzarDado();
            dado.moverDado(xPosicion,yPosicion);
            int resultado = dado.getValor();
            dadosTirados.add(dado);
        }
    }


    public void mostrarDadosTirados() {
        for (Dado dado : dadosTirados) {
            System.out.println(dado.getValor());
        }
    }

    public void mostrarDadosTomados() {
        for (Dado dado : dadosTomados) {
            System.out.println(dado.getValor());
        }
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public int getPuntuacion() {
        return puntuacion;
    }



}
