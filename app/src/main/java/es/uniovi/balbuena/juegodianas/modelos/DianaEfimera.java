package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.HashMap;

import es.uniovi.balbuena.juegodianas.R;
import es.uniovi.balbuena.juegodianas.global.Estados;
import es.uniovi.balbuena.juegodianas.graficos.Sprite;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 15/12/2017.
 */

public class DianaEfimera extends Diana {
    public static final String BASICO = "basico";
    public static final String EXPLOTAR = "Explotar";

    private Sprite sprite;
    private HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();


    public DianaEfimera(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(40);
        ancho = Ar.ancho(40);

        //puntuacion
        setPuntuacion(5);

        imagen = context.getResources().getDrawable(R.drawable.diana2);

        Sprite basico = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.diana2), ancho, altura, 1, 1, false);
        sprites.put(BASICO, basico);

        Sprite explotar = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_enemigo_explotar), ancho, altura, 6, 6, false);
        sprites.put(EXPLOTAR, explotar);

        sprite = basico;

        aceleracionX = 6;
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

        boolean finalizaSprite = sprite.actualizar(System.currentTimeMillis());

        if (sprite == sprites.get(EXPLOTAR) && finalizaSprite) {
            imagen = null;
            //sprite = sprites.get(EXPLOTAR);
            estado = Estados.INACTIVO;
        }

        //si sale por la derecha, desaparece
        if (x - ancho / 2 >= mCanvasAncho && aceleracionX >0){
            estado = Estados.INACTIVO;
        }
        if ( x + ancho / 2 <= 0 && aceleracionX < 0){
            estado = Estados.INACTIVO;
        }

        x = x + aceleracionX;
    }

    @Override
    public void destruir() {
        estado = Estados.EXPLOTANDO;
        sprite = sprites.get(EXPLOTAR);
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
