package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
        AtomicBoolean menuActivo = new AtomicBoolean(true);
        while (menuActivo.get()) {
            JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel panelDeTitulo = new JPanel();
            JLabel labelImagen = new JLabel(new ImageIcon("C:\\Users\\PC OSTRICH\\Pr-ctica-4\\PantallaInicial.png"));
            //JLabel labelImagen = new JLabel(new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Pr-ctica-4\\PantallaInicial.png"));
            panelDeTitulo.add(labelImagen);

            JPanel panelCentro = new JPanel(new GridLayout(2, 1, 5, 5));
            JLabel labelBienvenida = new JLabel("Bienvenido a Farkle Game", SwingConstants.CENTER);
            labelBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
            labelBienvenida.setForeground(Color.black);
            panelCentro.add(labelBienvenida);

            JPanel panelDeAcciones = new JPanel(new GridLayout(1, 3, 10, 5));
            JButton botonJugar = new JButton("Jugar");
            JButton botonCreditos = new JButton("Creditos");
            JButton botonSalir = new JButton("Salir");

            panelDeAcciones.add(botonJugar);
            panelDeAcciones.add(botonCreditos);
            panelDeAcciones.add(botonSalir);

            panelPrincipal.add(panelDeTitulo, BorderLayout.NORTH);
            panelPrincipal.add(panelCentro, BorderLayout.CENTER);
            panelPrincipal.add(panelDeAcciones, BorderLayout.SOUTH);

            JOptionPane optionPane = new JOptionPane(panelPrincipal, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog ventana = optionPane.createDialog("Farkle Game");

            botonJugar.addActionListener(e -> {
                menuActivo.set(false);
                NumeroDejugadores = obtenerNumeroDeJugadores();
                CantidadDepuntosAlcanzar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de puntos para ganar"));
                ventana.dispose();
                iniciarJuego(NumeroDejugadores, CantidadDepuntosAlcanzar);
            });

            botonCreditos.addActionListener(e -> {
                mostrarCreditos();
            });

            botonSalir.addActionListener(e -> {
                System.exit(0);
                ventana.dispose();
            });

            ventana.setVisible(true);
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
        int trios = 0;
        int[] conteo = new int[7];
        int pares = 0;
        int[] conteo2 = new int[7];
        boolean esEscalera = true;
        int[] numero = new int[7];
        Jugador jugador = jugadores.get(turnoActual);
        int unos = jugador.cuantosHayDe(1);
        int doses = jugador.cuantosHayDe(2);
        int treses = jugador.cuantosHayDe(3);
        int cuatros = jugador.cuantosHayDe(4);
        int cincos = jugador.cuantosHayDe(5);
        int seises = jugador.cuantosHayDe(6);

        if(unos == 6 || doses == 6 || treses == 6 || cuatros == 6 || cincos == 6 || seises == 6){
            puntuacion += 3000;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }

        if(unos == 5 || doses == 5 || treses == 5 || cuatros == 5 || cincos == 5 || seises == 5){
            puntuacion += 2000;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }

        if(unos == 4 || doses == 4 || treses == 4 || cuatros == 4 || cincos == 4 || seises == 4){
            puntuacion += 1000;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }

        for (int valor : dadosTomados) {
            conteo[valor]++;
        }
        for (int i = 1; i <= 6; i++) {
            if (conteo[i] == 3) {
                trios++;
            }
        }
        if (trios == 2) {
            puntuacion += 2500;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }

        for (int valor : dadosTomados) {
            conteo2[valor]++;
        }
        for (int i = 1; i <= 6; i++) {
            if (conteo2[i] == 2) {
                pares++;
            }
        }
        if (pares == 3) {
            puntuacion += 1500;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }

        for (int valor : dadosTomados) {
            numero[valor]++;
        }

        for (int i = 1; i <= 6; i++) {
            if (numero[i] != 1) {
                esEscalera = false;
                break;
            }
        }

        if (esEscalera) {
            puntuacion += 2500;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }

        if (unos >= 3) {
            puntuacion += 1000;
            unos = 0;
        }
        if (doses >= 3) {
            puntuacion += 200;
        }
        if (treses >= 3) {
            puntuacion += 300;
        }
        if (cuatros >= 3) {
            puntuacion += 400;
        }
        if (cincos >= 3) {
            puntuacion += 500;
            cincos = 0;
        }
        if (seises >= 3) {
            puntuacion += 600;
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
