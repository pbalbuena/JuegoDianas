package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;

import es.uniovi.balbuena.juegodianas.R;
import es.uniovi.balbuena.juegodianas.global.Estados;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 08/12/2017.
 */

public class PowerUpPuntuacion extends PowerUp {
    public PowerUpPuntuacion(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(20);
        ancho = Ar.ancho(20);
        imagen = context.getResources().getDrawable(R.drawable.florpowerup);
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {
        if(estado != Estados.INACTIVO) {
            int yArriba = (int) y - altura / 2;
            int xIzquierda = (int) x - ancho / 2;

            imagen.setBounds(xIzquierda, yArriba, xIzquierda
                    + ancho, yArriba + altura);
            imagen.draw(canvas);
        }
    }

    @Override
    public int estaEnPantalla() {
        if ( y - altura/2 < 0){
            return 0;
        } if ( y - altura/2 >= 0 && y + altura/2 < mCanvasAltura){
            return 1;
        }
        return -1;
    }

    @Override
    public void mover() {

    }
}
