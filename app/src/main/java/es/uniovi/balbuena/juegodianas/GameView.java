package es.uniovi.balbuena.juegodianas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.uniovi.balbuena.juegodianas.gestores.GestorNiveles;
import es.uniovi.balbuena.juegodianas.global.Estados;
import es.uniovi.balbuena.juegodianas.modelos.Cannon;
import es.uniovi.balbuena.juegodianas.modelos.Diana;
import es.uniovi.balbuena.juegodianas.modelos.DianaFacil;
import es.uniovi.balbuena.juegodianas.modelos.DisparoCannon;
import es.uniovi.balbuena.juegodianas.modelos.Obstaculo;
import es.uniovi.balbuena.juegodianas.modelos.ObstaculoFacil;
import es.uniovi.balbuena.juegodianas.modelos.ObstaculoIrrompible;
import es.uniovi.balbuena.juegodianas.modelos.PowerUp;
import es.uniovi.balbuena.juegodianas.modelos.controles.Marcador;
import es.uniovi.balbuena.juegodianas.modelos.escenario.Fondo;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

import static android.support.v4.content.ContextCompat.startActivity;

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
    private Marcador marcador;
    private List<Diana> dianas;
    private List<Obstaculo> obstaculos;
    private List<DisparoCannon> disparoCannons;
    private List<PowerUp> powerUps;
    private boolean finJuego;
    private boolean isPowerUpAtaque;
    private boolean isPowerUpPuntuacion;

    public GameView(Context context) {
        super(context);
        setFocusable(true);
        inicializar(context);
        this.context = context;
    }

    private void inicializar(Context context) {
        isPowerUpAtaque = false;
        isPowerUpPuntuacion=false;


        fondo = new Fondo (context, Ar.x(320/2), Ar.y(480/2));
        cannon = new Cannon(context,  Ar.x(320/2), Ar.y(390));
        marcador = new Marcador(context, Ar.x(30), Ar.y(30));
        ObstaculoFacil obstaculoFacil = new ObstaculoFacil(context, Ar.x(50), Ar.y(250));
        ObstaculoIrrompible obstaculoIrrompible = new ObstaculoIrrompible(context, Ar.x(250), Ar.y(150));


        disparoCannons = new LinkedList<DisparoCannon>();

        dianas = GestorNiveles.getInstancia().getDianasNivelActual(context);

        obstaculos = new LinkedList<Obstaculo>();
        obstaculos.add(obstaculoFacil);
        obstaculos.add(obstaculoIrrompible);

        powerUps = new LinkedList<PowerUp>();

        // Bucle del Juego.
        gameloop = new GameLoop();
        gameloop.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            fondo.dibujarEnPantalla(canvas);
            cannon.dibujarEnPantalla(canvas);
            marcador.dibujarEnPantalla(canvas);
            for (Diana diana : dianas
                 ) {
                diana.dibujarEnPantalla(canvas);
            }
            for (DisparoCannon disparos : disparoCannons
                    ) {
                disparos.dibujarEnPantalla(canvas);
            }
            for (Obstaculo obstaculo : obstaculos
                 ) {
                obstaculo.dibujarEnPantalla(canvas);
            }

        }catch (Exception e){

        }
    }

    private void gl_comprobarColisiones(){
        Diana dianaSacarLista = null;
        DisparoCannon disparoSacarLista = null;
        PowerUp powerUpSacarLista = null;
        Obstaculo obstaculoSacarLista = null;

        for (Diana d: dianas
             ) {
            if (/*d.estaEnPantalla() == -1 ||*/ d.estado == Estados.INACTIVO) {
                Log.i("esta en pantalla?", " " + d.estaEnPantalla());
                Log.i("esta en activo?", " " + d.estado);
                Log.i("diana ", d.toString() + " destruida");
                dianaSacarLista  = d;
            }

            for (DisparoCannon disparoCannon1 : disparoCannons) {

                if (d.estaEnPantalla() == 1
                        && d.colisiona(disparoCannon1) && d.estado == Estados.ACTIVO) {

                    d.destruir();

                    marcador.setPuntos(marcador.getPuntos() + d.getPuntuacion());
                    if(isPowerUpPuntuacion){ //si ha cogido un powerUp, se dobla la puntuacion
                        marcador.setPuntos(marcador.getPuntos() + d.getPuntuacion());
                    }

                    disparoSacarLista = disparoCannon1;
                }
                if (disparoCannon1.estaEnPantalla() != 1) {
                    disparoSacarLista = disparoCannon1;
                }
            }
        }
        for (Obstaculo o : obstaculos
             ) {
            if (o.estaEnPantalla() == -1 || o.estado == Estados.INACTIVO) {
                obstaculoSacarLista  = o;
            }

            for (DisparoCannon disparoCannon1 : disparoCannons) {

                if (o.estaEnPantalla() == 1
                        && o.colisiona(disparoCannon1) && o.estado == Estados.ACTIVO) {

                    o.destruir();
                    if(isPowerUpAtaque){
                        o.estado = Estados.INACTIVO;
                        o.setImagen(null);
                    }
                    disparoSacarLista = disparoCannon1;
                }
                if (disparoCannon1.estaEnPantalla() != 1) {
                    disparoSacarLista = disparoCannon1;
                }
            }
        }

        //sacar de las listas
        if(dianaSacarLista != null){
            dianas.remove(dianaSacarLista);
        }
        if(disparoSacarLista != null){
            disparoCannons.remove(disparoSacarLista);
        }
        if(obstaculoSacarLista != null){
            obstaculos.remove(obstaculoSacarLista);
        }




    }

    private void gl_comprobarNivelFinalizado(){
        if(dianas.size() == 0){
            finJuego = true;
            ((MainActivity) context).finish();/*
            Intent actividadJuego = new Intent(MenuActivity.class, MainActivity.class);
            startActivity(actividadJuego);*/
        }
    }

    private void gl_moverElementos(){

        fondo.mover();
        cannon.moverAutomaticamente();

        for (Diana d : dianas
             ) {
            d.moverAutomaticamente();
           //Log.i("coord disparo x y", d.getX() + " " + d.getY());
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
                      gl_comprobarColisiones();
                    gl_comprobarNivelFinalizado();

                    // Re-dibujar onDraw(Canvas canvas)
                    postInvalidate();
                }
            } catch (InterruptedException ex) {

            }
        }

    }
}
