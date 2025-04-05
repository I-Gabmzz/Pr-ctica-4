package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class Jugador {
    private int puntuacionTotal;
    private int puntuacion;
    private ArrayList<Dado> dadosTirados;
    private ArrayList<Dado> dadosDTomados;
    private int dadosDisponibles = 6;
    private boolean opcJugador = true;
    private int turnoActual = 0;
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
        AtomicBoolean continuar = new AtomicBoolean(true);
        while (opcJugador) {
            JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panelPrincipal.setLocation(800, 400);


            JPanel panelDeTitulo = new JPanel();
            JLabel labelImagen = new JLabel(new ImageIcon("C:\\Users\\PC OSTRICH\\Pr-ctica-4\\Titulo.png"));
            panelDeTitulo.add(labelImagen);

            JPanel panelDeJugador = new JPanel();
            JLabel labelJugador = new JLabel("Turno de Jugador " + (Farkle.getTurnoActual() + 1), SwingConstants.CENTER);
            labelJugador.setFont(new Font("Arial", Font.BOLD, 16));
            panelDeJugador.add(labelJugador);

            JPanel panelDeDados = new JPanel(new GridLayout(1, 6, 5, 5));
            JButton[] botonesDados = new JButton[dadosTirados.size()];
            for (int i = 0; i < dadosTirados.size(); i++) {
                botonesDados[i] = new JButton(String.valueOf(dadosTirados.get(i).getValor()));
                panelDeDados.add(botonesDados[i]);
            }

            JPanel panelDePuntuacion = new JPanel(new GridLayout(2, 1, 5, 5));
            JLabel labelPuntuacionActual = new JLabel("Puntuación Actual: " + puntuacion, SwingConstants.CENTER);
             labelPuntuacionActual.setFont(new Font("Arial", Font.BOLD, 14));
             labelPuntuacionActual.setForeground(Color.BLUE);
             panelDePuntuacion.add(labelPuntuacionActual);
            JLabel labelPuntuacionTotal = new JLabel("Puntuación Total: " + puntuacionTotal, SwingConstants.CENTER);
            labelPuntuacionTotal.setFont(new Font("Arial", Font.BOLD, 14));
            labelPuntuacionTotal.setForeground(Color.RED);
            panelDePuntuacion.add(labelPuntuacionTotal);


            JPanel panelCentro = new JPanel(new GridLayout(2, 1, 5, 5));
            panelCentro.add(panelDeJugador);
            panelCentro.add(panelDeDados);


            JPanel panelDeAcciones = new JPanel(new GridLayout(1, 3, 10, 5));
            JButton botonTirar = new JButton("Tirar Dados");
            JButton botonBank = new JButton("Bank");
            JButton botonCombinaciones = new JButton("Mostrar Combinaciones");


            panelDeAcciones.add(botonTirar);
            panelDeAcciones.add(botonBank);
            panelDeAcciones.add(botonCombinaciones);

            JPanel panelAbajo = new JPanel(new GridLayout(2, 1, 5, 5));
            panelAbajo.add(panelDeAcciones);
            panelAbajo.add(panelDePuntuacion);

            panelPrincipal.add(panelDeTitulo, BorderLayout.NORTH);
            panelPrincipal.add(panelCentro, BorderLayout.CENTER);
            panelPrincipal.add(panelAbajo, BorderLayout.SOUTH);

            JOptionPane optionPane = new JOptionPane(panelPrincipal, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog ventana = optionPane.createDialog("Ventana De Juego");
            ventana.setLocation(1100, 325);

            for (int i = 0; i < botonesDados.length; i++) {
                int index = i;
                botonesDados[i].addActionListener(e -> {
                    dadosTirados.get(index).esconder();
                    Dado dadoSeleccionado = dadosTirados.remove(index);
                    agregarDadoD(dadoSeleccionado);
                    dadosDisponibles--;
                    mostrarDadosTomadosEnCanvas();
                    ventana.dispose();
                });
            }

            botonTirar.addActionListener(e -> {
                opcJugador = true;
                continuar.set(false);
                ventana.dispose();
            });

            botonBank.addActionListener(e -> {
                opcJugador = false;
                continuar.set(false);
                ventana.dispose();
            });

            botonCombinaciones.addActionListener(e -> {
                mostrarCombinaciones();
                ventana.dispose();
            });

            ventana.setVisible(true);
            ventana.dispose();

            if (!continuar.get()) {
                break;
            }
        }
        return new ArrayList<>(dadosDTomados);
    }

    public void limpiarDadosTirados() {
        for (int i = 0; i < dadosTirados.size(); i++) {
            dadosTirados.get(i).esconder();
        }
    }

    public void mostrarCombinaciones() {
        JPanel panelDeCombinaciones = new JPanel();
        JLabel labelDeCombinaciones = new JLabel(new ImageIcon("C:\\Users\\PC OSTRICH\\Pr-ctica-4\\Combinaciones.png"));
        panelDeCombinaciones.add(labelDeCombinaciones);
        JOptionPane optionPane = new JOptionPane(panelDeCombinaciones, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog ventanaC = optionPane.createDialog("Combinaciones");
        ventanaC.setLocation(1145, 70);
        ventanaC.setVisible(true);
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
