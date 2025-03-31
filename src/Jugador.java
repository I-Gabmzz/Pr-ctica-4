package src;

import java.util.ArrayList;

public class Jugador {
    private int puntuacionTotal;
    private int puntuacion;
    private ArrayList<Dado> dadosTirados;
    private ArrayList<Dado> dadosTomados;


    public Jugador() {
        this.puntuacionTotal = 0;
        this.puntuacion = 0;
        this.dadosTirados = new ArrayList<>();
        this.dadosTomados = new ArrayList<>();
    }

    public void tirarDados(int cantidad) {
        dadosTirados.clear();

        for (int i = 0; i < cantidad; i++) {
            Dado dado = new Dado();
            int resultado = dado.lanzarDado();
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
