package es.uniovi.balbuena.juegodianas;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.uniovi.balbuena.juegodianas.modelos.Cannon;
import es.uniovi.balbuena.juegodianas.modelos.Diana;
import es.uniovi.balbuena.juegodianas.modelos.DianaFacil;
import es.uniovi.balbuena.juegodianas.modelos.DisparoCannon;
import es.uniovi.balbuena.juegodianas.modelos.escenario.Fondo;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 08/12/2017.
 */

public class GameView extends View {

    // Elementos del juego
    Context context;
    GameLoop gameloop;

    int NO_ACTION = 0;
    int ACTION_MOVE = 1;
    int ACTION_UP = 2;
    int ACTION_DOWN = 3;
    int accion[] = new int[6];
    float x[] = new float[6];
    float y[] = new float[6];


    //objetos, personajes y demas

    private Fondo fondo;
    private Cannon cannon;
    private List<Diana> dianas;
    private List<DisparoCannon> disparoCannons;

    public GameView(Context context) {
        super(context);
        setFocusable(true);
        inicializar(context);
        this.context = context;
    }

    private void inicializar(Context context) {

        fondo = new Fondo (context, Ar.x(320/2), Ar.y(480/2));
        cannon = new Cannon(context,  Ar.x(320/2), Ar.y(390));
        Diana d = new DianaFacil(context, 75, 75);

        disparoCannons = new LinkedList<DisparoCannon>();
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
            cannon.dibujarEnPantalla(canvas);
            for (Diana diana : dianas
                 ) {
                diana.dibujarEnPantalla(canvas);
            }
            for (DisparoCannon disparos : disparoCannons
                    ) {
                disparos.dibujarEnPantalla(canvas);
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
        cannon.moverAutomaticamente();

        for (Diana d : dianas
             ) {
            d.moverAutomaticamente();
        }
        for (DisparoCannon disparos : disparoCannons
                ) {
            disparos.moverAutomaticamente();
        }
    }

    private void gl_comprobarDisparos(){

    }

    public boolean onTouchEvent(MotionEvent event){

        // valor a Binario
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        // Indice del puntero
        int pointerIndex = (event.getAction() &
                MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        int pointerId = event.getPointerId(pointerIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                accion[pointerId] = ACTION_DOWN;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                accion[pointerId] = ACTION_UP;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);
                    accion[pointerId] = ACTION_MOVE;
                    x[pointerId] = event.getX(pointerIndex);
                    y[pointerId] = event.getY(pointerIndex);
                }
                break;
        }

        procesarEventosTouch();
        return true;
    }

    private void procesarEventosTouch(){
        boolean pulsacionMover = false;
        for (int i = 0; i < 6; i++) {
            if (accion[i] != NO_ACTION) {

                    if (accion[i] == ACTION_DOWN) {
                        // Disparar
                        if(y[i] > fondo.getAltura()/3) {
                            cannon.setDisparando(true);
                            double x1 = cannon.getX();
                            double y1 = cannon.getY();
                            double x2 = x[i];
                            int aceleracionx = 0;
                            if(x2 > cannon.getX()){
                                aceleracionx = 5;
                            } else if(x2 < cannon.getX()){
                                aceleracionx = -5;
                            } else{
                                aceleracionx = 0;
                            }
                            double y2 = y[i];
                            //Log.i("coord x y", x.toString() + " " + y.toString());
                            double pendiente = (y1 - y2) / (x1 - x2);
                            double n = y1- (pendiente*x1);
                            disparoCannons.add(cannon.disparar(pendiente, n,  aceleracionx));
                        }
                    }

            }
        }
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
