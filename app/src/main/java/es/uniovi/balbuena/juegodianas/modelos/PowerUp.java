package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Balbuena on 08/12/2017.
 */

public abstract class PowerUp extends Modelo {

    public float aceleracionY;

    public PowerUp(Context context, double x, double y) {

        super(context, x, y);
    }

    public abstract void dibujarEnPantalla(Canvas canvas);
    public abstract int estaEnPantalla();
    public abstract void mover();

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
