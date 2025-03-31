package src;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Jugador {
    private int puntuacionTotal;
    private int puntuacion;
    private ArrayList<Dado> dadosTirados;
    private ArrayList<Integer> dadosTomados;
    private int dadosDisponibles = 6;


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
            int xVariable = 975 - dadosDisponibles * delta;
            int xPosicion = 75 + i *  delta ;
            int yPosicion = 450 ;
            Dado dado = new Dado();
            dado.lanzarDado();
            dado.moverDado(xPosicion,yPosicion);
            int resultado = dado.getValor();
            dadosTirados.add(dado);
        }
    }

    public void guardarDadosTirados() {
        while (true) {
            Object[] botones = new Object[dadosTirados.size() + 1];
            for (int i = 0; i < dadosTirados.size(); i++) {
                botones[i] = dadosTirados.get(i).getValor();
            }
            botones[dadosTirados.size()] = "Continuar";

            int opcion = JOptionPane.showOptionDialog( null, "Estos son los dados tirados, seleccione los que desea guardar en el banco:", "Tomar Dados",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones, botones[0]);
            if (opcion == dadosTirados.size()) {
                break;
            }

            if (opcion >= 0 && opcion < dadosTirados.size()) {
                Dado dadoSeleccionado = dadosTirados.remove(opcion);
                int valorSeleccionado = dadoSeleccionado.getValor();
                dadosTomados.add(valorSeleccionado);
                dadosDisponibles--;

                JOptionPane.showMessageDialog(null, "Tomaste el dado con valor: " + valorSeleccionado);
            }
        }
    }

    public void mostrarDadosTirados() {
        for (Dado dado : dadosTirados) {
            System.out.println(dado.getValor());
        }
    }

    public void mostrarDadosTomados() {
        for (int valor : dadosTomados) {
            System.out.println(dadosTomados);
        }
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public int getPuntuacion() {
        return puntuacion;
    }



}
