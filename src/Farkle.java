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
            if(esFarkle()){
                JOptionPane.showMessageDialog(null,
                        "¡Farkle! No has obtenido puntos en esta tirada.",
                        "Farkle",
                        JOptionPane.ERROR_MESSAGE);
                int puntuacionObtenida = 0;
                jugador.actualizarPuntuacion(puntuacionObtenida);
                jugador.limpiarDadosTirados();
                jugador.limpiarDadosTomados();
                jugador.sumarPuntuacionTotal();
                break;
            }else{
                jugador.guardarDadosTirados();
                int puntuacionObtenida = determinarPuntaje(jugador.getDadosTomados());
                jugador.actualizarPuntuacion(puntuacionObtenida);
                jugador.limpiarDadosTirados();
            }
        }
        jugador.limpiarDadosTomados();
        jugador.sumarPuntuacionTotal();
    }


    public int determinarPuntaje(ArrayList<Integer> dadosTomados) {
        int puntuacion = 0;
        Jugador jugador = jugadores.get(turnoActual);
        int unos = jugador.cuantosHayDe(1);
        int doses = jugador.cuantosHayDe(2);
        int treses = jugador.cuantosHayDe(3);
        int cuatros = jugador.cuantosHayDe(4);
        int cincos = jugador.cuantosHayDe(5);
        int seises = jugador.cuantosHayDe(6);
        if (unos >= 3) {
            puntuacion += 1000;
            unos -= 3;
        }
        if (doses >= 3) {
            puntuacion += 200;
            doses -= 3;
        }
        if (treses >= 3) {
            puntuacion += 300;
            treses -= 3;
        }
        if (cuatros >= 3) {
            puntuacion += 400;
            cuatros -= 3;
        }
        if (cincos >= 3) {
            puntuacion += 500;
            cincos -= 3;
        }
        if (seises >= 3) {
            puntuacion += 600;
            seises -= 3;
        }

        if(unos == 4 || doses == 4 || treses == 4 || cuatros == 4 || cincos == 4 || seises == 4){
            puntuacion += 1000;
            unos -= 4;
            doses -= 4;
            treses -= 4;
            cuatros -= 4;
            cincos -= 4;
            seises -= 4;
        }

        if(unos == 5 || doses == 5 || treses == 5 || cuatros == 5 || cincos == 5 || seises == 5){
            puntuacion += 2000;
            unos -= 5;
            doses -= 5;
            treses -= 5;
            cuatros -= 5;
            cincos -= 5;
            seises -= 5;
        }
        puntuacion += unos * 100;
        puntuacion += cincos * 50;

        JOptionPane.showMessageDialog(null,
                "Puntaje obtenido con los dados tomados: " + puntuacion,
                "Puntaje",
                JOptionPane.INFORMATION_MESSAGE);
        return puntuacion;
    }

    public boolean esFarkle() {
        Jugador jugador = jugadores.get(turnoActual);
        int unos = jugador.cuantosHayEnDadosTirados(1);
        int doses = jugador.cuantosHayEnDadosTirados(2);
        int treses = jugador.cuantosHayEnDadosTirados(3);
        int cuatros = jugador.cuantosHayEnDadosTirados(4);
        int cincos = jugador.cuantosHayEnDadosTirados(5);
        int seises = jugador.cuantosHayEnDadosTirados(6);

        if (unos > 0 || cincos > 0) {
            return false;
        }

        if (doses >= 3 || treses >= 3 || cuatros >= 3 || seises >= 3) {
            return false;
        }

        return true;
    }

    public static int getTurnoActual() {
        return turnoActual;
    }


}
