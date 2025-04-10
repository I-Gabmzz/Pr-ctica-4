package src;

import java.util.Random;

public class Dado {
    private Square Cuerpo;
    private Circle Punto1;
    private Circle Punto2;
    private Circle Punto3;
    private Circle Punto4;
    private Circle Punto5;
    private Circle Punto6;
    private Circle Punto7;

    private int valor;
    private int xPosition = 0;
    private int yPosition = 0;

    private int tamaño = 100;
    private int tamañoPunto = 20;
    private int delta = 10;

    private String colorPunto = "black";

    public Dado() {
        dibujar();
    }

    public void dibujar () {
        Cuerpo = new Square () ;
        Cuerpo.changeColor ("beach");
        Punto1 = new Circle () ;
        Punto1.changeColor (colorPunto);
        Punto2 = new Circle () ;
        Punto2.changeColor (colorPunto);
        Punto3 = new Circle () ;
        Punto3.changeColor (colorPunto);
        Punto4 = new Circle () ;
        Punto4.changeColor (colorPunto);
        Punto5 = new Circle () ;
        Punto5.changeColor (colorPunto);
        Punto6 = new Circle () ;
        Punto6.changeColor (colorPunto);
        Punto7 = new Circle () ;
        Punto7.changeColor (colorPunto);

        acomodarDado();
    }

    public void acomodarDado (){
        Cuerpo.moveTo (xPosition, yPosition);
        Cuerpo.changeSize (tamaño);
        Punto1.changeSize (tamañoPunto);
        Punto2.changeSize (tamañoPunto);
        Punto3.changeSize (tamañoPunto);
        Punto4.changeSize (tamañoPunto);
        Punto5.changeSize (tamañoPunto);
        Punto6.changeSize (tamañoPunto);
        Punto7.changeSize (tamañoPunto);


        Punto1.moveTo(xPosition + tamañoPunto - delta, yPosition + tamañoPunto - delta);
        Punto2.moveTo(xPosition + tamañoPunto - delta, yPosition + 2 * tamañoPunto);
        Punto3.moveTo(xPosition + tamañoPunto - delta, yPosition + 3 * tamañoPunto + delta);

        Punto4.moveTo(xPosition + 3 * tamañoPunto + delta, yPosition + tamañoPunto - delta);
        Punto5.moveTo(xPosition + 3 * tamañoPunto + delta, yPosition + 2 * tamañoPunto);
        Punto6.moveTo(xPosition + 3 * tamañoPunto + delta, yPosition + 3 * tamañoPunto + delta);

        Punto7.moveTo(xPosition + 2 * tamañoPunto, yPosition + 2 * tamañoPunto);
    }

    public void esconder (){
        Punto1.makeInvisible();
        Punto2.makeInvisible();
        Punto3.makeInvisible();
        Punto4.makeInvisible();
        Punto5.makeInvisible();
        Punto6.makeInvisible();
        Punto7.makeInvisible();
        Cuerpo.makeInvisible();
    }

    public void mostrarValor (int i){
        esconder ();
        Cuerpo.makeVisible();
        switch(i){
            case 1:
                Punto7.makeVisible();
                break;
            case 2:
                Punto2.makeVisible();
                Punto5.makeVisible();
                break;
            case 3:
                Punto1.makeVisible();
                Punto7.makeVisible();
                Punto6.makeVisible();
                break;
            case 4:
                Punto1.makeVisible();
                Punto3.makeVisible();
                Punto4.makeVisible();
                Punto6.makeVisible();
                break;
            case 5:
                Punto1.makeVisible();
                Punto3.makeVisible();
                Punto4.makeVisible();
                Punto6.makeVisible();
                Punto7.makeVisible();
                break;
            case 6:
                Punto1.makeVisible();
                Punto2.makeVisible();
                Punto3.makeVisible();
                Punto4.makeVisible();
                Punto5.makeVisible();
                Punto6.makeVisible();
            default:
                break;
        }
    }

    public void moverDado (int x, int y){
        xPosition = x;
        yPosition = y;
        acomodarDado();
    }

    public void lanzarDado ( ) {
        Random valorRnd = new Random();
        this.valor  = valorRnd.nextInt(6) + 1;
        mostrarValor(valor);
    }

    public int getValor() {
        return valor;
    }



}
