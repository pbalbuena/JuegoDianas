package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;

import es.uniovi.balbuena.juegodianas.global.Estados;

/**
 * Created by Balbuena on 08/12/2017.
 */

public abstract class Obstaculo extends Modelo {
    public float aceleracionX;
    public float aceleracionY;
    public int cadenciaDisparo;
    public int estado = Estados.ACTIVO;

    public Obstaculo(Context context, double x, double y) {

        super(context, x, y);
    }

    public abstract void dibujarEnPantalla(Canvas canvas);

    public abstract void moverAutomaticamente();

    public abstract void destruir();

    public abstract boolean colisiona(Modelo modelo);

    public abstract int estaEnPantalla();
}
