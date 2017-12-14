package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;

import es.uniovi.balbuena.juegodianas.R;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 12/12/2017.
 */

public class DisparoCannon extends Modelo {
    public int aceleracionY = -6;
    public int aceleracionX;
    private double pendienteRecta;
    private double n;


    public DisparoCannon (Context context, double x, double y, double pendiente,  double n, int acelx) {
        super(context, x, y);
        altura = Ar.altura(20);
        ancho = Ar.ancho(20);

        aceleracionX = acelx;
        pendienteRecta = pendiente;
        this.n = n;
        imagen = context.getResources().getDrawable(R.drawable.disparo_cannon);
    }

    public void moverAutomaticamente() {
        //y= mx x = y/m
        //double newY = y + aceleracionY;
        if (x >= mCanvasAncho ){
            rebotarDerecha();
        }
        if ( x <= 0){
            rebotarIzquierda();
        }
        double newX = x + aceleracionX;
        y = pendienteRecta*newX + n;
        x = newX;
    }

    public void moverRapido(){


        y = y + aceleracionY -150;
    }

    public int estaEnPantalla(){
        if ( y - altura/2 < 0){
            return 0;
        } if ( y - altura/2 >= 0 && y + altura/2 < mCanvasAltura){
            return 1;
        }
        return -1;
    }

    private void rebotarIzquierda() {        //invertir la pendiente
        double newX = 0;
        double newY = (pendienteRecta * newX) + n;
        this.pendienteRecta = pendienteRecta *(-1);
        //y = mCanvasAltura;
        this.n = newY - (pendienteRecta*newX);
        this.aceleracionX = aceleracionX * -1;

    }

    private void rebotarDerecha() {
        double newX = mCanvasAncho;
        double newY = (pendienteRecta * newX) + n;
        this.pendienteRecta = pendienteRecta *(-1);
        //y = mCanvasAltura;
        this.n = newY - (pendienteRecta*newX);
        this.aceleracionX = aceleracionX * -1;
    }


}
