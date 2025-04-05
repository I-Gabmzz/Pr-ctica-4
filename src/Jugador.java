package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Jugador {
    private int puntuacionTotal;
    private int puntuacion;
    private ArrayList<Dado> dadosTirados;
    private ArrayList<Dado> dadosDTomados;
    private int dadosDisponibles = 6;
    private boolean opcJugador = true;
    private Rectangulo fondo;


    public Jugador() {
        this.puntuacionTotal = 0;
        this.puntuacion = 0;
        this.dadosTirados = new ArrayList<>();
        this.dadosDTomados = new ArrayList<>();
        fondo = new Rectangulo();
    }

    public void tirarDados(int dadosDisponibles) {
        int delta = 150;
        dadosTirados.clear();
        for (int i = 0; i < dadosDisponibles; i++) {
            int xPosicion = 75 + i *  delta;
            int yPosicion = 450;
            Dado dado = new Dado();
            dado.moverDado(xPosicion,yPosicion);
            dado.lanzarDado();
            int resultado = dado.getValor();
            dadosTirados.add(dado);
        }
    }

    public ArrayList<Dado> guardarDadosTirados() {
        while (opcJugador) {
            Object[] botones = new Object[dadosTirados.size() + 2];
            for (int i = 0; i < dadosTirados.size(); i++) {
                botones[i] = dadosTirados.get(i).getValor();
            }
            botones[dadosTirados.size()] = "Tirar Dados";
            botones[dadosTirados.size() + 1] = "Bank";

            int opcion = JOptionPane.showOptionDialog(null, "Estos son los dados tirados, seleccione los que desea guardar en el banco:", "Tomar Dados",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones, botones[0]);

            if (opcion == dadosTirados.size()) {
                opcJugador = true;
                break;
            }

            if (opcion == dadosTirados.size() + 1) {
                opcJugador = false;
                break;
            }

            if (opcion >= 0 && opcion < dadosTirados.size()) {
                dadosTirados.get(opcion).esconder();
                Dado dadoSeleccionado = dadosTirados.remove(opcion);
                agregarDadoD(dadoSeleccionado);
                dadosDisponibles--;
            }
            mostrarDadosTomadosEnCanvas();
        }
        return new ArrayList<>(dadosDTomados);
    }

    public void limpiarDadosTirados() {
        for (int i = 0; i < dadosTirados.size(); i++) {
            dadosTirados.get(i).esconder();
        }
    }

    public void limpiarDadosTomados() {
        for (int i = 0; i < dadosDTomados.size(); i++) {
            dadosDTomados.get(i).esconder();
        }
        fondo.makeInvisible();
    }

    public boolean getOpcJugador() {
        return opcJugador;
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
        int xPositionFondo = 50 + dadosDTomados.size() * delta;

        fondo.moveTo(50, 800);
        fondo.changeSize(xPositionFondo - 50, 150);
        fondo.changeColor("bone white");
        fondo.makeVisible();

        for (int i = 0; i < dadosDTomados.size(); i++) {
            int xPosicion = 75 + i *  delta;
            int yPosicion = 825;
            Dado dado = dadosDTomados.get(i);
            dado.moverDado(xPosicion, yPosicion);
            dado.mostrarValor(dadosDTomados.get(i).getValor());
        }
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
        dadosDTomados.add(dado);
    }

    public void agregarDadoD(Dado dado) {
        dadosDTomados.add(dado);
    }

    public ArrayList<Integer> getDadosTomados() {
        ArrayList<Integer> dadosTomados = new ArrayList<>();
        for (Dado dado : dadosDTomados) {
            dadosTomados.add(dado.getValor());
        }
        return dadosTomados;
    }
}
