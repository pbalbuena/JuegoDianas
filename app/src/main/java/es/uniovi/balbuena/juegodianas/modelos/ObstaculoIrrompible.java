package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;

import es.uniovi.balbuena.juegodianas.R;
import es.uniovi.balbuena.juegodianas.global.Estados;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 15/12/2017.
 */

public class ObstaculoIrrompible extends Obstaculo {
    public ObstaculoIrrompible(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(20);
        ancho = Ar.ancho(100);
        imagen = context.getResources().getDrawable(R.drawable.ladrillo1);
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
    public void moverAutomaticamente() {

    }

    @Override
    public void destruir() {

    }

    public void destruirIrrompible() {
        imagen = null;
    }

    @Override
    public boolean colisiona(Modelo modelo) {
        boolean colisiona = false;

        if (modelo.getX() - modelo.getAncho() / 2 <= (x + ancho / 2)
                && (modelo.getX() + modelo.getAncho() / 2) >= (x - ancho / 2)
                && (y + altura / 2) >= (modelo.getY() - modelo.getAltura() / 2)
                && (y - altura / 2) < (modelo.getY() + modelo.getAltura() / 2)) {
            colisiona = true;
        }
        return colisiona;
    }

    @Override
    public int estaEnPantalla() {
        if ( y + altura/2 < 0){
            return 0;
        } if ( y + altura/2 >= 0 && y - altura/2 < mCanvasAltura){
            return 1;
        }
        return -1;
    }
}
