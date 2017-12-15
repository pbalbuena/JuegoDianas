package es.uniovi.balbuena.juegodianas.modelos.controles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import es.uniovi.balbuena.juegodianas.modelos.Modelo;

/**
 * Created by Balbuena on 15/12/2017.
 */

public class Marcador extends Modelo {
    int puntos;

    public Marcador(Context context, double x, double y) {

        super(context, x, y);
    }

    public int getPuntos() {

        return puntos;
    }

    public void setPuntos(int puntos) {

        this.puntos = puntos;
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        canvas.drawText(String.valueOf(puntos), (int)x, (int)y, paint);
    }
}
