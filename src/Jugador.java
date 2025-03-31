package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        int delta = 150;
        dadosTirados.clear();
        for (int i = 0; i < dadosDisponibles; i++) {
            int xPosicion = 75 + i *  delta;
            int yPosicion = 450;
            Dado dado = new Dado();
            dado.lanzarDado();
            dado.moverDado(xPosicion,yPosicion);
            int resultado = dado.getValor();
            dadosTirados.add(dado);
        }
    }

    public ArrayList<Integer> guardarDadosTirados() {
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
        return dadosTomados;
    }

    public void mostrarDadosTirados() {
        StringBuilder mensaje = new StringBuilder("Valores de los dados tirados:\n");
        for (Dado dado : dadosTirados) {
            mensaje.append(dado.getValor()).append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Dados Tirados", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarDadosTomadosEnCanvas(int valor, int turno) {
        int delta = 150;
        int xPositionFondo = 50 + dadosTomados.size() * delta;
        int xPosition = 75 + dadosTomados.size() *  delta;
        int yPosicion = 825;

        Rectangulo fondo = new Rectangulo();
        fondo.moveTo(xPositionFondo, 800);
        fondo.changeSize(xPosition + 50, 150);
        fondo.makeVisible();

        switch(turno) {
            case 0:
                fondo.changeColor("red");
                break;
            case 1:
                fondo.changeColor("blue");
                break;
            case 2:
                fondo.changeColor("green");
                break;
            case 3:
                fondo.changeColor("yellow");
                break;
        }

        for (int i = 0; i < dadosTomados.size(); i++) {
            Dado dado = new Dado();
            dado.mostrarValor(valor);
            dado.moverDado(xPosition, yPosicion);
        }
    }

    public void mostrarDadosTomados() {
        StringBuilder mensaje = new StringBuilder("Valores de los dados tomados:\n");
        for (int valor : dadosTomados) {
            mensaje.append(valor).append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Dados Tomados", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarPuntuacion() {
        JOptionPane.showMessageDialog(null, "Tu puntuación actual es: " + puntuacion, "Puntuación del Jugador", JOptionPane.INFORMATION_MESSAGE);
    }


    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public int getPuntuacion() {
        return puntuacion;
    }



}
