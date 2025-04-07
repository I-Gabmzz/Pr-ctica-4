package src;
//Se importan las librerias que se usarán en esta clase
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//Se declara la clase Farkle
public class Farkle {
    //Se declaran los atributos de la clase que serán necesarias para el funcionamiento de la clase
    private int numeroDejugadores;
    private int cantidadDepuntosAlcanzar;
    private ArrayList<Jugador> jugadores;
    private static int turnoActual;

    //Se declara el constructor de la clase
    public Farkle()
    { }
    //Metodo para obtener el numero de jugadores
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
    //Metodo para mostrar creditos
    public void mostrarCreditos() {
        JOptionPane.showMessageDialog(null,"Desarrollado por: Diego Erik Alfonso Montoya y Angel Gabriel Manjarrez Moreno.\nMatriculas: 1198520 y 1197503.\nVersion: 31/ 03 / 2025.","Creditos",JOptionPane.INFORMATION_MESSAGE);
    }
    //Metodo del menu inicial
    public void menuInicial() {
        AtomicBoolean menuActivo = new AtomicBoolean(true);
        //Si el menu esta activo se hace lo siguiente
        while (menuActivo.get()) {
            //Se crea un nuevo panel
            JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            //Se crea un panel en el que se muestra una imagen
            JPanel panelDeTitulo = new JPanel();
            //JLabel labelImagen = new JLabel(new ImageIcon("C:\\Users\\PC OSTRICH\\Pr-ctica-4\\PantallaInicial.png"));
            JLabel labelImagen = new JLabel(new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Pr-ctica-4\\PantallaInicial.png"));
            panelDeTitulo.add(labelImagen);
            //Se crea un panel con un mensaje de bienvenida
            JPanel panelCentro = new JPanel(new GridLayout(2, 1, 5, 1));
            JLabel labelBienvenida = new JLabel("Bienvenido a Farkle Game", SwingConstants.CENTER);
            labelBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
            labelBienvenida.setForeground(Color.black);
            panelCentro.add(labelBienvenida);
            //Se crean paneles con los botones para jugar, créditos y salir
            JPanel panelDeAcciones = new JPanel(new GridLayout(1, 3, 10, 1));
            JButton botonJugar = new JButton("Jugar");
            JButton botonCreditos = new JButton("Creditos");
            JButton botonSalir = new JButton("Salir");

            Stream.of(botonJugar, botonCreditos, botonSalir)
                    .forEach(boton -> {
                        boton.setFont(new Font("Arial", Font.BOLD, 20));
                        boton.setBackground(Color.LIGHT_GRAY);
                    });

            panelDeAcciones.add(botonJugar);
            panelDeAcciones.add(botonCreditos);
            panelDeAcciones.add(botonSalir);

            panelPrincipal.add(panelDeTitulo, BorderLayout.NORTH);
            panelPrincipal.add(panelCentro, BorderLayout.CENTER);
            panelPrincipal.add(panelDeAcciones, BorderLayout.SOUTH);

            JOptionPane optionPane = new JOptionPane(panelPrincipal, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog ventana = optionPane.createDialog("Farkle Game");
            //Se configura el boton de jugar
            botonJugar.addActionListener(e -> {
                menuActivo.set(false);
                numeroDejugadores = obtenerNumeroDeJugadores();
                cantidadDepuntosAlcanzar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de puntos para ganar"));
                ventana.dispose();
                iniciarJuego(numeroDejugadores, cantidadDepuntosAlcanzar);
            });
            //Se configura el boton de créditos
            botonCreditos.addActionListener(e -> {
                mostrarCreditos();
            });
            //Se configura el boton de salir
            botonSalir.addActionListener(e -> {
                System.exit(0);
                ventana.dispose();
            });

            ventana.setVisible(true);
        }
    }
    //Metodo para iniciar le juego dependiendo del numero de jugadores y la cantidad de puntos a alcanzar
    private void iniciarJuego(int NumeroDejugadores, int CantidadDepuntosAlcanzar) {
        //Se llena el ArrayList de jugadores con la cantidad de jugadores
        jugadores = new ArrayList<>();
        for (int i = 0; i < NumeroDejugadores; i++) {
            jugadores.add(new Jugador());
        }
        turnoActual = 0;
        int jugadorObjetivo = -1;

        while (true) {
            //Se obtiene el jugador actual
            Jugador jugador = jugadores.get(turnoActual);
            jugador.setOpcJugador();
            turnoDeJugador(jugador);
            jugador.sumarPuntuacionTotal();
            jugador.reiniciarPuntuacionEnTurno();
            //Se determina si es hotDice el jugador tiene un turno extra
            if(esHotDice()){
                turnoDeJugador(jugador);
            }
            //Se determina si el jugador ha alcanzado la puntuación máxima
            if (jugador.haAlcanzadoPuntuacion(CantidadDepuntosAlcanzar)) {
                if (jugadorObjetivo == -1) {
                    jugadorObjetivo = turnoActual;
                    JOptionPane.showMessageDialog(null,
                            "¡El jugador " + (turnoActual + 1) + " ha alcanzado la meta! Última ronda para los demás.",
                            "Meta alcanzada",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //Se actualiza el turno
            turnoActual = (turnoActual + 1) % NumeroDejugadores;

            if (jugadorObjetivo != -1 && turnoActual == jugadorObjetivo) {
                break;
            }
        }
        //Se determina un ganador
        determinarGanadorFinal();
    }
    //Metodo del turno del jugador en el que tira los dados
    private void turnoDeJugador(Jugador jugador) {
        jugador.reiniciarDadosTomados();
        jugador.reiniciarDadosDisponibles();
        //Mientras el jugador vuelva a tirar o le queden dados disponibles se ejecuta lo siguiente
        while (jugador.getOpcJugador() && jugador.getDadosDisponibles() > 0) {
            //Se tiran los dados con la cantidad de dados disponibles que tiene el jugador
            jugador.tirarDados(jugador.getDadosDisponibles());
            //Se evalua si es un farkle y pierde toda la puntuacion de la ronda
            if(esFarkle()){
                int puntuacionObtenida = 0;
                jugador.actualizarPuntuacion(puntuacionObtenida);
                jugador.limpiarDadosTirados();
                jugador.limpiarDadosTomados();
                jugador.limpiarDadosSeleccionados();
                jugador.reiniciarPuntuacionEnTurno();
                break;
            //Si no es un farkle se guardan los dados tirados y se determina la puntuación de la ronda
            }else{
                jugador.guardarDadosTirados();
                int puntuacionObtenida = determinarPuntaje(jugador.getDadosTomados());
                jugador.actualizarPuntuacion(puntuacionObtenida);
                jugador.limpiarDadosTirados();
                jugador.limpiarDadosSeleccionados();
            }
        }
        //Se limpian los dados tomados
        jugador.limpiarDadosTomados();
    }
    //Metodo para determinar el puntaje dependiendo del ArrayList de los dados tomados
    public int determinarPuntaje(ArrayList<Integer> dadosTomados) {
        int puntuacion = 0;
        int trios = 0;
        int[] conteo = new int[7];
        Jugador jugador = jugadores.get(turnoActual);
        int unos = jugador.cuantosHayDe(1);
        int doses = jugador.cuantosHayDe(2);
        int treses = jugador.cuantosHayDe(3);
        int cuatros = jugador.cuantosHayDe(4);
        int cincos = jugador.cuantosHayDe(5);
        int seises = jugador.cuantosHayDe(6);
        //Si la combinacion se agarran 6 dados iguales la puntuación es de 3000
        if(unos == 6 || doses == 6 || treses == 6 || cuatros == 6 || cincos == 6 || seises == 6){
            puntuacion += 3000;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }
        //Si la puntuacion es de 4 unos iguales es de 2000 y si es de 5 unos iguales es de 1000
        switch(unos){
            case 5:
                puntuacion += 2000;
                unos = 0;
                break;
            case 4:
                puntuacion += 1000;
                unos = 0;
                break;
        }
        //Si la puntuacion son 5 dados de 2 la puntuación es de 2000, si la puntación son 4 dados de 2 la puntuacion es de 1000
        switch(doses){
            case 5:
                puntuacion += 2000;
                doses = 0;
                break;
            case 4:
                puntuacion += 1000;
                doses = 0;
                break;
        }
        //Si la puntuacion son 5 dados de 3 la puntuación es de 2000, si la puntación son 4 dados de 3 la puntuacion es de 1000
        switch(treses){
            case 5:
                puntuacion += 2000;
                treses = 0;
                break;
            case 4:
                puntuacion += 1000;
                treses = 0;
                break;
        }
        //Si la puntuacion son 5 dados de 4 la puntuación es de 2000, si la puntación son 4 dados de 4 la puntuacion es de 1000
        switch(cuatros){
            case 5:
                puntuacion += 2000;
                cuatros = 0;
                break;
            case 4:
                puntuacion += 1000;
                cuatros = 0;
                break;
        }
        //Si la puntuacion son 5 dados de 5 la puntuación es de 2000, si la puntación son 4 dados de 5 la puntuacion es de 1000
        switch(cincos){
            case 5:
                puntuacion += 2000;
                cincos = 0;
                break;
            case 4:
                puntuacion += 1000;
                cincos = 0;
                break;
        }
        //Si la puntuacion son 5 dados de 6 la puntuación es de 2000, si la puntación son 4 dados de 6 la puntuacion es de 1000
        switch(seises){
            case 5:
                puntuacion += 2000;
                seises = 0;
                break;
            case 4:
                puntuacion += 1000;
                seises = 0;
                break;
        }
        //Se cuentan los dados con un valor en dados tomados
        for (int valor : dadosTomados) {
            conteo[valor]++;
        }
        //Se determina cuantos trios hay
        for (int i = 1; i <= 6; i++) {
            if (conteo[i] == 3) {
                trios++;
            }
        }
        //Si hay 2 trios la puntuacion 2500
        if (trios == 2) {
            puntuacion += 2500;
            unos = 0;
            doses = 0;
            treses = 0;
            cuatros = 0;
            cincos = 0;
            seises = 0;
        }
        //Si hay una tercia de 1 la puntuación es 1000
        if (unos >= 3) {
            puntuacion += 1000;
            unos = 0;
        }
        //Si hay una tercia de 2 la puntuación es de 200
        if (doses >= 3) {
            puntuacion += 200;
        }
        //Si hay una tercia de 3 la puntuación es de 300
        if (treses >= 3) {
            puntuacion += 300;
        }
        //Si hay una tercia de 4 la puntuación es de 400
        if (cuatros >= 3) {
            puntuacion += 400;
        }
        //Si hay una tercia de 5 la puntuación es de 500
        if (cincos >= 3) {
            puntuacion += 500;
            cincos = 0;
        }
        //Si hay una tercia de 6 la puntuación es de 600
        if (seises >= 3) {
            puntuacion += 600;
        }

        //Se suma la puntuacion de la combinacion con unos o cincos individualmente
        puntuacion += unos * 100;
        puntuacion += cincos * 50;

        JOptionPane.showMessageDialog(null,
                "Puntaje obtenido con los dados tomados: " + puntuacion,
                "Puntaje",
                JOptionPane.INFORMATION_MESSAGE);
        return puntuacion;
    }
    //Metodo para determinar si es un farkle
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

        JOptionPane.showMessageDialog(null,
                "¡Farkle! No has obtenido puntos en esta tirada.",
                "Farkle",
                JOptionPane.ERROR_MESSAGE);

        return true;
    }
    //Metodo getter para obtener el turno actual
    public static int getTurnoActual() {
        return turnoActual;
    }
    //Metodo para determinar si es un hotDice
    public boolean esHotDice() {
        Jugador jugador = jugadores.get(turnoActual);
        if(jugador.getDadosDisponibles() == 0){
            JOptionPane.showMessageDialog(null,
                    "!Hot Dice, puedes volver a tirar!",
                    "HotDice",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    //Metodo para determinar el ganador
    private void determinarGanadorFinal() {
        int maxPuntos = jugadores.stream()
                .mapToInt(Jugador::getPuntuacionTotal)
                .max()
                .orElse(0);

        int ganadorFinal = IntStream.range(0, jugadores.size())
                .filter(i -> jugadores.get(i).getPuntuacionTotal() == maxPuntos)
                .findFirst()
                .orElse(-1);

        JOptionPane.showMessageDialog(null,
                "¡El jugador " + (ganadorFinal + 1) + " es el ganador con " + maxPuntos + " puntos!",
                "Ganador final",
                JOptionPane.INFORMATION_MESSAGE);
    }
}