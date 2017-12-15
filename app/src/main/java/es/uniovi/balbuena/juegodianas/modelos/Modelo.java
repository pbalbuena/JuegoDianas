package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 29/11/2017.
 */

public class Modelo {
    // Propiedades del canvas
    protected Context context;
    protected double mCanvasAltura;
    protected double mCanvasAncho;

    // Propiedades del Modelo
    protected double x;
    protected double y;
    protected int altura;
    protected int ancho;
    protected Drawable imagen;

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public Modelo(Context context, double x, double y){
        this.context = context;
        this.x = x;
        this.y = y;
        this.mCanvasAltura = Ar.pantallaAltura;
        this.mCanvasAncho = Ar.pantallaAncho;
    }

    public double getY() {

        return y;
    }

    public void setY(double y) {

        this.y = y;
    }

    public double getX() {

        return x;
    }

    public void setX(double x) {

        this.x = x;
    }

    public int getAncho() {

        return ancho;
    }

    public void setAncho(int ancho) {

        this.ancho = ancho;
    }

    public int getAltura() {

        return altura;
    }

    public void setAltura(int altura) {

        this.altura = altura;
    }

    public void dibujarEnPantalla(Canvas canvas){
        int yArriba = (int)  y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriba, xIzquierda
                + ancho, yArriba + altura);
        imagen.draw(canvas);
    }

    public boolean colisiona (Modelo modelo){
        boolean colisiona = false;

        if (modelo.getX() - modelo.getAncho() / 2 <= (x + ancho / 2)
                && (modelo.getX() + modelo.getAncho() / 2) >= (x - ancho / 2)
                && (y + altura / 2) >= (modelo.getY() - modelo.getAltura() / 2)
                && (y - altura / 2) < (modelo.getY() + modelo.getAltura() / 2)) {
            colisiona = true;
        }
        return colisiona;
    }
}
