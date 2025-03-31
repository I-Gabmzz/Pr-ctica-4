package src;

import javax.swing.*;

public class Farkle {
    private int opcion;
    private int jugadores;

    public Farkle()
    {
        menuInicial();
        if (opcion == 0) {
            iniciarJuego();
        } else if (opcion == 1) {
            mostrarCreditos();
        } else if (opcion == 2) {
            System.exit(0);
        }
    }

    private void iniciarJuego(int jugadores, int puntos) {

    }

    private void mostrarCreditos() {
    }

    private void menuInicial() {
        Object[] botones = {"Jugar", "Creditos", "Salir"};
        int opcion = JOptionPane.showOptionDialog(null, "Bienvenido al juego Farkle, Seleccione una opcion:", "Juego Farkle",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones, botones[0]);
    }
}
