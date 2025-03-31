package src;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Farkle juego = new Farkle();
        ArrayList<Integer> dadosTomados = new ArrayList<>();

        // Ejemplo de dados tomados
        dadosTomados.add(1);
        dadosTomados.add(1);
        dadosTomados.add(3);
        dadosTomados.add(3);
        dadosTomados.add(3);
        dadosTomados.add(5);
        dadosTomados.add(5);
        dadosTomados.add(5);

        juego.determinarPuntaje(dadosTomados);
      
        // Dado prueba = new Dado();
        Farkle farkle = new Farkle();
        farkle.menuInicial();
        // prueba.lanzarDado();
        Jugador j1 = new Jugador();
        j1.tirarDados(6);
        j1.guardarDadosTirados();
        j1.mostrarDadosTomadosEnCanvas();

    }
}