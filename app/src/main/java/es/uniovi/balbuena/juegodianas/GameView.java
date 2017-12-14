package es.uniovi.balbuena.juegodianas;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.uniovi.balbuena.juegodianas.modelos.Diana;
import es.uniovi.balbuena.juegodianas.modelos.DianaFacil;
import es.uniovi.balbuena.juegodianas.modelos.escenario.Fondo;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 08/12/2017.
 */

public class GameView extends View {

    // Elementos del juego
    Context context;
    GameLoop gameloop;


    //objetos, personajes y demas

    private Fondo fondo;
    private List<Diana> dianas;

    public GameView(Context context) {
        super(context);
        setFocusable(true);
        inicializar(context);
        this.context = context;
    }

    private void inicializar(Context context) {

        fondo = new Fondo (context, Ar.x(320/2), Ar.y(480/2));
        Diana d = new DianaFacil(context, 75, 75);

        dianas = new LinkedList<Diana>();
        dianas.add(d);



        // Bucle del Juego.
        gameloop = new GameLoop();
        gameloop.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            fondo.dibujarEnPantalla(canvas);

            for (Diana diana : dianas
                 ) {
                diana.dibujarEnPantalla(canvas);
            }

        }catch (Exception e){

        }
    }

    private void gl_comprobarColisiones(){

    }

    private void gl_comprobarNivelFinalizado(){

    }

    private void gl_moverElementos(){

        fondo.mover();

        for (Diana d : dianas
             ) {
            d.moverAutomaticamente();
        }
    }

    private void gl_comprobarDisparos(){

    }

    public boolean onTouchEvent(MotionEvent event){
        return false;
    }

    private void procesarEventosTouch(){

    }

            private class GameLoop extends Thread{
        public void run() {
            try {
                while (true) {
                    // 1000 / 25 = 50fps aproximadamente.
                    TimeUnit.MILLISECONDS.sleep(25);

                      gl_moverElementos();
                  //  gl_comprobarColisiones();


                    // Re-dibujar onDraw(Canvas canvas)
                    postInvalidate();
                }
            } catch (InterruptedException ex) {

            }
        }

    }
}
