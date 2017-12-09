package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Balbuena on 08/12/2017.
 */

public class PowerUpAtaque extends PowerUp {
    public PowerUpAtaque(Context context, double x, double y) {
        super(context, x, y);
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {

    }

    @Override
    public int estaEnPantalla() {
        return 0;
    }

    @Override
    public void mover() {

    }
}
