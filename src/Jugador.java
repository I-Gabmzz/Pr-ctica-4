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

            int opcion = JOptionPane.showOptionDialog(null, "Estos son los dados tirados, seleccione los que desea guardar en el banco:", "Tomar Dados",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones, botones[0]);

            if (opcion == dadosTirados.size()) {
                break;
            }

            if (opcion >= 0 && opcion < dadosTirados.size()) {
                Dado dadoSeleccionado = dadosTirados.remove(opcion);
                agregarDado(dadoSeleccionado);
                dadosDisponibles--;

                JOptionPane.showMessageDialog(null, "Tomaste el dado con valor: " + dadoSeleccionado.getValor());
            }
        }
        return new ArrayList<>(dadosTomados);
    }

    public void mostrarDadosTirados() {
        StringBuilder mensaje = new StringBuilder("Valores de los dados tirados:\n");
        for (Dado dado : dadosTirados) {
            mensaje.append(dado.getValor()).append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Dados Tirados", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarDadosTomadosEnCanvas() {
        int delta = 150;
        int xPositionFondo = 50 + dadosTomados.size() * delta;

        Rectangulo fondo = new Rectangulo();
        fondo.moveTo(50, 800);
        fondo.changeSize(xPositionFondo - 50, 150);
        fondo.changeColor("bone white");
        fondo.makeVisible();

        for (int i = 0; i < dadosTomados.size(); i++) {
            int xPosicion = 75 + i *  delta;
            int yPosicion = 825;
            Dado dado = new Dado();
            dado.mostrarValor(dadosTomados.get(i));
            dado.moverDado(xPosicion, yPosicion);
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

    public void actualizarPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void reiniciarDadosDisponibles() {
        this.dadosDisponibles = 6;
    }

    public boolean haAlcanzadoPuntuacion(int limite) {
        return puntuacionTotal >= limite;
    }

    public void sumarPuntuacionTotal() {
        puntuacionTotal += puntuacion;
        puntuacion = 0;
    }

    public int getDadosDisponibles() {
        return dadosDisponibles;
    }
    public void agregarDado(Dado dado) {
        dadosTomados.add(dado.getValor());
    }
    public ArrayList<Integer> getDadosTomados() {
        return new ArrayList<>(dadosTomados);
    }
}
