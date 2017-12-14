package es.uniovi.balbuena.juegodianas.modelos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.HashMap;

import es.uniovi.balbuena.juegodianas.R;
import es.uniovi.balbuena.juegodianas.graficos.Sprite;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 12/12/2017.
 */

public class Cannon extends Modelo {

    public static final String BASICO = "Basico";
    public static final String DISPARAR = "Disparar";

    private boolean disparando;

    private Sprite sprite;
    private HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();

    public Cannon(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(150);
        ancho = Ar.ancho(100);

        Sprite basico = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.sprite_cannon_basico),
                ancho, altura,
                4, 8, true, 2);
        sprites.put(BASICO, basico);

        Sprite disparar = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.sprite_cannon_disparar),
                ancho, altura,
                12, 8, false, 2);
        sprites.put(DISPARAR, disparar);

        sprite = basico;
    }

    public DisparoCannon disparar(double pendiente, int acelx){
        if (disparando) {
            disparando = false;
            return new DisparoCannon(context, x, y, pendiente, acelx );
        }
        return null;
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x, (int) y);
    }

    public void moverAutomaticamente() {

        boolean finalizaSprite = sprite.actualizar(System.currentTimeMillis());

    }

    public boolean isDisparando() {
        return disparando;
    }

    public void setDisparando(boolean disparando) {
        this.disparando = disparando;
    }
}

/*
public class Nave extends Modelo {
    public static final String BASICO = "Basico";
    public static final String IMPACTADO = "Explotar";
    private Sprite sprite;
    private HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();


    private double aceleracionX;
    protected boolean disparando = false;

    public Nave(Context context, double x, double y) {
        super(context, x, y);
        altura = 63;
        ancho = 50;

        Sprite basico = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_nave),
                ancho, altura,
                12, 4, true);
        sprites.put(BASICO, basico);

        Sprite impactado = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_nave_explota),
                ancho, altura,
                4, 12, false);
        sprites.put(IMPACTADO, impactado);

        sprite = basico;


    }

    public void acelerar(float factorMovimiento) {
        if ( factorMovimiento < 0){
            aceleracionX = 2;
        }
        if ( factorMovimiento >= 0){
            aceleracionX = -2;
        }
    }

    public void frenar(){
        aceleracionX = 0;
    }

    public DisparoNave disparar() {
        if (disparando) {
            disparando = false;
            return new DisparoNave(context, x, y);
        }
        return null;

    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x, (int) y);
    }


    public void mover(){
        boolean finalizaSprite =
                sprite.actualizar(System.currentTimeMillis());

        if (sprite == sprites.get(IMPACTADO) && finalizaSprite) {
            sprite = sprites.get(BASICO);
        }

        x = x + aceleracionX;

        if( x  < 0){
            x = 0;
        }
        if ( x  > mCanvasAncho  ){
            x  = mCanvasAncho;
        }

    }

    public void impactado(){
        sprite = sprites.get(IMPACTADO);
        sprite.setFrameActual(0); // Reiniciar el Sprite
    }


    public boolean isDisparando() {
        return disparando;
    }

    public void setDisparando(boolean disparando) {
        this.disparando = disparando;
    }
}

 */
