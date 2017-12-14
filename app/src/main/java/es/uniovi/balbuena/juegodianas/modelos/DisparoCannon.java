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


    private boolean haciaDerecha;


    public DisparoCannon (Context context, double x, double y, double pendiente, int acelx) {
        super(context, x, y);
        altura = Ar.altura(20);
        ancho = Ar.ancho(20);

        aceleracionX = acelx;
        pendienteRecta = pendiente;
        imagen = context.getResources().getDrawable(R.drawable.disparo_cannon);
    }

    public void moverAutomaticamente() {
        //y= mx x = y/m

        double newY = y + aceleracionY;
        double newX = x + aceleracionX;
        y = pendienteRecta*newX;
        x = newY/pendienteRecta;
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


}
