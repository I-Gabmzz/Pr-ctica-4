package src;

import javax.swing.*;

public class Farkle {
    private int opcion;
    private int NumeroDejugadores;
    private int CantidadDepuntosAlcanzar;

    public Farkle()
    { }

    public int obtenerNumeroDeJugadores() {
        int jugadores;
        do {
            jugadores = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de jugadores (mínimo 2)"));
            if (jugadores < 2) {
                JOptionPane.showMessageDialog(null, "Debe haber al menos 2 jugadores. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (jugadores < 2);
        return jugadores;
    }

    public void mostrarCreditos() {
        JOptionPane.showMessageDialog(null,"Desarrollado por: Diego Erik Alfonso Montoya y Angel Gabriel Manjarrez Moreno.\nMatriculas: 1198520 y 1197503.\nVersion: 31/ 03 / 2025.","Creditos",JOptionPane.INFORMATION_MESSAGE);
    }

    public void menuInicial() {
        boolean menuActivo = true;
        while (menuActivo) {
            Object[] botones = {"Jugar", "Créditos", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Bienvenido al juego Farkle, seleccione una opción:", "Juego Farkle",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones, botones[0]);

            if (opcion == 0) {
                menuActivo = false;
                NumeroDejugadores = obtenerNumeroDeJugadores();
                CantidadDepuntosAlcanzar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de puntos para ganar"));
                iniciarJuego(NumeroDejugadores, CantidadDepuntosAlcanzar);
            } else if (opcion == 1) {
                mostrarCreditos();
            } else if (opcion == 2) {
                System.exit(0);
            }
        }
    }

    private void iniciarJuego(int NumeroDejugadores, int CantidadDepuntosAlcanzar) {

    }
}
