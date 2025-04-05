package src;

import javax.swing.*;
import java.util.ArrayList;

public class Farkle {
    private int NumeroDejugadores;
    private int CantidadDepuntosAlcanzar;
    private ArrayList<Jugador> jugadores;
    private static int turnoActual;

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
        int opcion;
        boolean menuActivo = true;
        while (menuActivo) {
            Object[] botones = {"Jugar", "Créditos", "Salir"};
            opcion = JOptionPane.showOptionDialog(null, "Bienvenido al juego Farkle, seleccione una opción:", "Juego Farkle",
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
        jugadores = new ArrayList<>();
        for (int i = 0; i < NumeroDejugadores; i++) {
            jugadores.add(new Jugador());
        }
        turnoActual = 0;
        while (true) {
            Jugador jugador = jugadores.get(turnoActual);
            // jOption
            turnoDeJugador(jugador);

            if (jugador.haAlcanzadoPuntuacion(CantidadDepuntosAlcanzar)) {
                JOptionPane.showMessageDialog(null, "¡El jugador " + (turnoActual + 1) + " ha ganado!", "Ganador", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
            turnoActual = (turnoActual + 1) % NumeroDejugadores;
        }
    }

    private void turnoDeJugador(Jugador jugador) {
        ArrayList<Integer> dadosTomados = new ArrayList<>();
        jugador.reiniciarDadosDisponibles();

        while (jugador.getOpcJugador() && jugador.getDadosDisponibles() > 0) {
            jugador.tirarDados(jugador.getDadosDisponibles());
            jugador.guardarDadosTirados();
            int puntuacionObtenida = determinarPuntaje(jugador.getDadosTomados());
            jugador.actualizarPuntuacion(puntuacionObtenida);
            jugador.limpiarDadosTirados();
        }
        jugador.limpiarDadosTomados();
        jugador.sumarPuntuacionTotal();
    }


    public int determinarPuntaje(ArrayList<Integer> dadosTomados) {
        int puntuacion = 0;
        int[] contador = new int[6];

        for (Integer dado : dadosTomados) {
            if (dado >= 1 && dado <= 6) {
                contador[dado - 1]++;
            }
        }
        if (contador[0] >= 3) {
            puntuacion += 1000;
            contador[0] -= 3;
        }
        if (contador[1] >= 3) {
            puntuacion += 200;
            contador[1] -= 3;
        }
        if (contador[2] >= 3) {
            puntuacion += 300;
            contador[2] -= 3;
        }
        if (contador[3] >= 3) {
            puntuacion += 400;
            contador[3] -= 3;
        }
        if (contador[4] >= 3) {
            puntuacion += 500;
            contador[4] -= 3;
        }
        if (contador[5] >= 3) {
            puntuacion += 600;
            contador[5] -= 3;
        }


        puntuacion += contador[0] * 100;
        puntuacion += contador[4] * 50;
        JOptionPane.showMessageDialog(null, "Puntaje obtenido con los dados tomados: " + puntuacion,
                "Puntaje", JOptionPane.INFORMATION_MESSAGE);
        return puntuacion;
    }

    public static int getTurnoActual() {
        return turnoActual;
    }


}
