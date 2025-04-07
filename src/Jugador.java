package src;

//Se importan las librerias que se usarán en esta clase
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

//Se declara la clase Jugador
public class Jugador {
    //Se declaran los atributos de la clase que serán necesarias para el funcionamiento de la clase
    private int puntuacionTotal;
    private int puntuacion;
    private int puntuacionEnTurno;
    private ArrayList<Dado> dadosTirados;
    private ArrayList<Dado> dadosDTomados;
    private ArrayList<Integer> dadosSeleccionados;
    private int dadosDisponibles = 6;
    private boolean opcJugador = true;
    private Rectangulo fondo;

    //Se declara el constructor de la clase en la cual se inicializan los atributos y se crean los ArrayList
    public Jugador() {
        this.puntuacionTotal = 0;
        this.puntuacion = 0;
        this.dadosTirados = new ArrayList<>();
        this.dadosDTomados = new ArrayList<>();
        this.dadosSeleccionados = new ArrayList<>();
        fondo = new Rectangulo();
    }
    //Metodo que tira los dados dependiendo los dados que le queden al jugador y se colocan en el canvas.
    public void tirarDados(int dadosDisponibles) {
        int delta = 150;
        dadosTirados.clear();
        for (int i = 0; i < dadosDisponibles; i++) {
            int xPosicion = 75 + i *  delta;
            int yPosicion = 200;
            Dado dado = new Dado();
            dado.moverDado(xPosicion,yPosicion);
            dado.lanzarDado();
            dadosTirados.add(dado);
        }
    }
    //Metodo para guardar los dados que el jugador tire
    public void guardarDadosTirados() {
        AtomicBoolean continuar = new AtomicBoolean(true);
        opcJugador = true;
        //Mientras que la opcion del jugador de tirar sea verdadera, se ejecutará el siguiente código
        while (opcJugador) {
            //Se crea el panel principal, determinando sus bordes y en que posición se pondrá
            JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panelPrincipal.setLocation(300, 100);

            //Se crea un panel  en el que se muestra una imagen del titulo del juego
            JPanel panelDeTitulo = new JPanel();
            //JLabel labelImagen = new JLabel(new ImageIcon("C:\\Users\\PC OSTRICH\\Pr-ctica-4\\Titulo.png"));
            JLabel labelImagen = new JLabel(new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Pr-ctica-4\\Titulo.png"));
            panelDeTitulo.add(labelImagen);

            //Se crea un panel en el cual se muestra el turno del jugador
            JPanel panelDeJugador = new JPanel();
            JLabel labelJugador = new JLabel("Turno de Jugador " + (Farkle.getTurnoActual() + 1), SwingConstants.CENTER);
            labelJugador.setFont(new Font("Arial", Font.BOLD, 16));
            panelDeJugador.add(labelJugador);
            //Se crea un panel en el cual se muestran los botones para seleccionar los dados que el jugador tiró en el turno
            JPanel panelDeDados = new JPanel(new GridLayout(1, 6, 5, 5));
            JButton[] botonesDados = new JButton[dadosTirados.size()];
            for (int i = 0; i < dadosTirados.size(); i++) {
                botonesDados[i] = new JButton(String.valueOf(dadosTirados.get(i).getValor()));
                panelDeDados.add(botonesDados[i]);
            }
            //Se crea un panel para mostrar la puntuación actual del jugador y la puntuación acumulada que lleva hasta ese punto
            JPanel panelDePuntuacion = new JPanel(new GridLayout(2, 1, 5, 5));
            JLabel labelPuntuacionActual = new JLabel("Puntuación Actual: " + puntuacionEnTurno, SwingConstants.CENTER);
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
            //Se crea un panel en el cual se muestran los botones con los que el usuario puede interactuar, ya sea para tirar dados,
            //hacer bank o mostrar la tabla de combinaciones.
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
            ventana.setLocation(950, 200);
            //Se itera sobre los botones de los dados que el jugador tiró identificando el indice del boton
            for (int i = 0; i < botonesDados.length; i++) {
                int index = i;
                botonesDados[i].addActionListener(e -> {
                    //Se toma el dado del indice del boton y su respectivo valor
                    Dado dado = dadosTirados.get(index);
                    int valor = dado.getValor();
                    //Se determina si el dado seleccionado es una jugada valida
                    if (esJugadaValida(index)) {
                        int totalEsteValor = contarDadosConValorEnTirados(valor) + contarDadosConValorEnTomados(valor);
                        //Si el valor del dado es 1  o 5 o hay al menos una tercia de dados de ese valor se le permite seleccionar ese dado
                        //y se muestra en el canvas una vez tomado
                        if (valor == 1 || valor == 5 || totalEsteValor >= 3) {
                            dadosSeleccionados.add(valor);
                            dado.esconder();
                            Dado dadoSeleccionado = dadosTirados.remove(index);
                            agregarDadoD(dadoSeleccionado);
                            dadosDisponibles--;
                            mostrarDadosTomadosEnCanvas();
                            ventana.dispose();
                        //Si no, se muestra un mensaje de advertencia
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Debes seleccionar al menos 3 dados de valor " + valor,
                                    "Selección incompleta",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    //Si no es una jugada valida se muestra un mensaje de error
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No puedes puntuar con este dado",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
          //Se configura la accion tirar
          botonTirar.addActionListener(e -> {
              //Si el usuario no selecciona ningun dado y quiere volver a tirar se muestra un mensaje de advertencia
              if (dadosSeleccionados.isEmpty()) {
                  JOptionPane.showMessageDialog(null,
                 "Debes seleccionar al menos un dado antes de volver a tirar.",
                 "Selección obligatoria",
                  JOptionPane.WARNING_MESSAGE);
                  //Si el usuario si puntuó con al menos un dado se le es permitido volver a tirar
                  } else {
                       opcJugador = true;
                       continuar.set(false);
                       ventana.dispose();
                  }
          });
            //Se configura la accion bank en el que cambia de turno y se guarda el puntaje
            botonBank.addActionListener(e -> {
                opcJugador = false;
                continuar.set(false);
                ventana.dispose();
            });
            //Se configura la accion combinaciones en el que muestra una tabla de combinaciones
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
    }
    //Metodo para esconder los dados Tirados en el canvas
    public void limpiarDadosTirados() {
        for (int i = 0; i < dadosTirados.size(); i++) {
            dadosTirados.get(i).esconder();
        }
    }
    //Metodo para reinciar los dados tomados por el jugador
    public void reiniciarDadosTomados() {
        dadosDTomados.clear();
    }
    //Metodo para mostrar la tabla de combinaciones
    public void mostrarCombinaciones() {
        JPanel panelDeCombinaciones = new JPanel();
        //JLabel labelDeCombinaciones = new JLabel(new ImageIcon("C:\\Users\\PC OSTRICH\\Pr-ctica-4\\Combinaciones.png"));
        JLabel labelDeCombinaciones = new JLabel(new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Pr-ctica-4\\Combinaciones.png"));
        panelDeCombinaciones.add(labelDeCombinaciones);
        JOptionPane optionPane = new JOptionPane(panelDeCombinaciones, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog ventanaC = optionPane.createDialog("Combinaciones");
        ventanaC.setLocation(1145, 70);
        ventanaC.setVisible(true);
    }
    //Metodo para esconder los dados Tomados
    public void limpiarDadosTomados() {
        for (int i = 0; i < dadosDTomados.size(); i++) {
            dadosDTomados.get(i).esconder();
        }
        fondo.makeInvisible();
    }
    //Metodo getter para obtener la opcion del jugador
    public boolean getOpcJugador() {
        return opcJugador;
    }
    //Metodo setter para la opcion del jugador
    public void setOpcJugador() {
        opcJugador = true;
    }
    //Metodo para mostrar los dados tomados en canvas
    public void mostrarDadosTomadosEnCanvas() {
        int delta = 150;
        int xPositionFondo = 50 + dadosDTomados.size() * delta;

        fondo.moveTo(50, 475);
        fondo.changeSize(xPositionFondo - 50, 150);
        fondo.changeColor("bone white");
        fondo.makeVisible();

        for (int i = 0; i < dadosDTomados.size(); i++) {
            int xPosicion = 75 + i *  delta;
            int yPosicion = 500;
            Dado dado = dadosDTomados.get(i);
            dado.moverDado(xPosicion, yPosicion);
            dado.mostrarValor(dadosDTomados.get(i).getValor());
        }
    }
    //Metodo getter para obtener la puntuacion total
    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }
    //Metodo para actualizar la puntuacion total
    public void actualizarPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
        puntuacionEnTurno = puntuacionEnTurno + puntuacion;
    }
    //Metodo para reinciar la puntuacion en turno
    public void reiniciarPuntuacionEnTurno() {
        puntuacionEnTurno = 0;
    }
    //Metodo para reiniciar los dados disponibles
    public void reiniciarDadosDisponibles() {
        this.dadosDisponibles = 6;
    }
    //Metodo para determinar si ha alcanzado la puntuacion
    public boolean haAlcanzadoPuntuacion(int limite) {
        return puntuacionTotal >= limite;
    }
    //Metodo para sumar la puntuacion total
    public void sumarPuntuacionTotal() {
        puntuacionTotal += puntuacionEnTurno;
        puntuacion = 0;
    }
    //Metodo getter para obtener los dados disponibles
    public int getDadosDisponibles() {
        return dadosDisponibles;
    }
    //Metodo para agregar dados
    public void agregarDadoD(Dado dado) {
        dadosDTomados.add(dado);
    }
    //Metodo para obtener el array de los dados seleccionados
    public ArrayList<Integer> getDadosTomados() {
        return new ArrayList<>(dadosSeleccionados);
    }
    //Metodo para saber cuantos dados de un valor hay en los dados seleccionados
    public int cuantosHayDe(int numero) {
        return (int) dadosSeleccionados.stream()
                .filter(valor -> valor == numero)
                .count();
    }
    //Metodo para saber cuantos dados de una valor hay en los dados tirados
    public int cuantosHayEnDadosTirados(int numero) {
        return (int) dadosTirados.stream()
                .filter(dado -> dado.getValor() == numero)
                .count();
    }
    //Metodo para limipiar los dados seleccionados
    public void limpiarDadosSeleccionados() {
        dadosSeleccionados.clear();
    }
    //Metodo para determinar si es una jugada valida dependiendo del indice del dado seleccionado
    public boolean esJugadaValida(int indice) {
        Dado dado = dadosTirados.get(indice);
        int valor = dado.getValor();
        //Si el valor del dado es 1 o 5 siempre será una jugada valida
        if (valor == 1 || valor == 5) {
            return true;
        }
        //Se determina cuantos dados iguales hay en dados tirados y tomados
        int contadorEnTirados = contarDadosConValorEnTirados(valor);
        int contadorEnTomados = contarDadosConValorEnTomados(valor);
        //Si la suma de ambos es mayor o igual  3 es una jugada valida
        return (contadorEnTirados + contadorEnTomados) >= 3;
    }
    //Metodo para contar cuantos dados con el valor determinado hay en los dados tirados
    private int contarDadosConValorEnTirados(int valor) {
        return (int) dadosTirados.stream()
                .filter(d -> d.getValor() == valor)
                .count();
    }
    //Metodo para contar cuantos dados con el valor determinado hay en los dados seleccionados
    private int contarDadosConValorEnTomados(int valor) {
        return (int) dadosSeleccionados.stream()
                .filter(d -> d == valor)
                .count();
    }
}