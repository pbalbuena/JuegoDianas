package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Balbuena on 08/12/2017.
 */

public class DianaFacil extends Diana {

    public DianaFacil(Context context, double x, double y) {
        super(context, x, y);
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {

    }

    @Override
    public void moverAutomaticamente() {

    }

    @Override
    public void destruir() {

    }

    @Override
    public boolean colisiona(Modelo modelo) {
        return false;
    }

    @Override
    public int estaEnPantalla() {
        return 0;
    }
}
