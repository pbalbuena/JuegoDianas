package es.uniovi.balbuena.juegodianas.modelos.escenario;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import es.uniovi.balbuena.juegodianas.R;
import es.uniovi.balbuena.juegodianas.modelos.Modelo;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 29/11/2017.
 */

public class Fondo extends Modelo {

    Drawable imagenAux;

    public Fondo(Context context, double x, double y) {
        super(context, x, y);
        altura =(int)  mCanvasAltura;
        ancho = (int) mCanvasAncho;
        imagen = context.getResources().getDrawable(R.drawable.fondo4);

        imagenAux = imagen;
    }

    public void mover(){
        y += Ar.y(1);
        if ( y > mCanvasAltura ){
            y = 0;
        }
        if ( y < - mCanvasAltura){
            y = mCanvasAltura;
        }
    }


    public void dibujarEnPantalla(Canvas canvas) {
        int yArriba = (int) y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriba, xIzquierda + ancho, yArriba
                + altura);
        imagen.draw(canvas);

        if (yArriba < 0) {
            imagenAux.setBounds(xIzquierda, (int) (yArriba + mCanvasAltura),
                    xIzquierda + ancho, (int) (yArriba + mCanvasAltura * 2));
            imagenAux.draw(canvas);
        }

        if (yArriba > 0) {
            imagenAux.setBounds(xIzquierda, (int) (yArriba - mCanvasAltura),
                    xIzquierda + ancho, yArriba);
            imagenAux.draw(canvas);

        }
    }


}
