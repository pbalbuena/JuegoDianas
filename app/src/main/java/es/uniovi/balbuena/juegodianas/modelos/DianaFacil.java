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
 * Created by Balbuena on 08/12/2017.
 */

public class DianaFacil extends Diana {

    public static final String EXPLOTAR = "Explotar";

    private Sprite sprite;
    private HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();


    public DianaFacil(Context context, double x, double y) {
        super(context, x, y);
        altura = Ar.altura(75);
        ancho = Ar.ancho(75);

        Sprite explotar = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.animacion_enemigo_explotar), ancho, altura, 6, 6, false);
        sprites.put(EXPLOTAR, explotar);


        aceleracionX = 5;
    }

    @Override
    public void dibujarEnPantalla(Canvas canvas) {

    }

    @Override
    public void moverAutomaticamente() {
        if(estado == Estados.ACTIVO){
            aceleracionX = (float) (0.5 + Math.random()*2.5 * -1);
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
        return false;
    }

    @Override
    public int estaEnPantalla() {
        return 0;
    }
}
