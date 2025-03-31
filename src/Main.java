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
    }
}