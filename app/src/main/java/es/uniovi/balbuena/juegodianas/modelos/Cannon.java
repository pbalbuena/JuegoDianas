package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;

import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 12/12/2017.
 */

public class Cannon extends Modelo {

    public Cannon(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(75);
        ancho = Ar.ancho(75);
    }

    public DisparoCannon disparar(){
        return null;
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {
        super.dibujarEnPantalla(canvas);
    }
}
