package es.uniovi.balbuena.juegodianas.gestores;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import es.uniovi.balbuena.juegodianas.R;
import es.uniovi.balbuena.juegodianas.modelos.Diana;
import es.uniovi.balbuena.juegodianas.modelos.DianaEfimera;
import es.uniovi.balbuena.juegodianas.modelos.DianaFacil;
import es.uniovi.balbuena.juegodianas.modelos.DianaJefe;
import es.uniovi.balbuena.juegodianas.modelos.Obstaculo;
import es.uniovi.balbuena.juegodianas.modelos.ObstaculoFacil;
import es.uniovi.balbuena.juegodianas.modelos.ObstaculoIrrompible;
import es.uniovi.balbuena.juegodianas.modelos.PowerUp;
import es.uniovi.balbuena.juegodianas.modelos.PowerUpAtaque;
import es.uniovi.balbuena.juegodianas.modelos.PowerUpPuntuacion;
import es.uniovi.balbuena.juegodianas.modelos.utilidades.Ar;

/**
 * Created by Balbuena on 14/12/2017.
 */

public class GestorNiveles {
    private static GestorNiveles instancia = null;
    public static boolean modoInfinito = false;
    private int nivelActual = 1;
    private List<Diana> dianasNivel;

    private GestorNiveles() {
    }

    public static GestorNiveles getInstancia() {
        synchronized (GestorNiveles.class) {
            if (instancia == null) {
                instancia = new GestorNiveles();
            }
            return instancia;
        }
    }

    public List<Diana> getDianasNivelActual(Context context) {
        List<Diana> dianas = new LinkedList<Diana>();
        switch (nivelActual) {
            case 1:
                dianas = cargarEnemigosXML (context, R.raw.level1);
                modoInfinito = false;
                break;
                /*
            case 2:
                dianas = cargarEnemigosXML (context, R.raw.level2);
                modoInfinito = false;
                break;
            case 3:
                dianas = cargarEnemigosXML (context, R.raw.level3);
                modoInfinito = false;
                break;
                */
            default:
                modoInfinito = true;
                dianas = cargarEnemigosXML (context, R.raw.level1);
                break;
        }
        return dianas;
    }

    public List<Obstaculo> getObstaculosNivelActual(Context context) {
        List<Obstaculo> obstaculos = new LinkedList<Obstaculo>();
        switch (nivelActual) {
            case 1:
                obstaculos = cargarObstaculosXML(context, R.raw.level1);
                break;
                /*
            case 2:
                powerUps = cargarPowerUpsXML(context, R.raw.level2);
                break;
            case 3:
                powerUps = cargarPowerUpsXML(context, R.raw.level3);
                break;
                */
        }
        return obstaculos;
    }

    public List<PowerUp> getPowerUpsNivelActual(Context context) {
        List<PowerUp> powerUps = new LinkedList<PowerUp>();
        switch (nivelActual) {
            case 1:
                powerUps = cargarPowerUpsXML(context, R.raw.level1);
                break;
                /*
            case 2:
                powerUps = cargarPowerUpsXML(context, R.raw.level2);
                break;
            case 3:
                powerUps = cargarPowerUpsXML(context, R.raw.level3);
                break;
                */
        }
        return powerUps;
    }

    public LinkedList<Diana> cargarEnemigosXML(Context context, int recursoNivel) {

        ParserXML parser = new ParserXML();
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(recursoNivel);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }

        Document doc = parser.getDom(textoFicheroNivel);

        LinkedList<Diana> dianas = new LinkedList<Diana>();

        NodeList nodosDianaFacil = doc.getElementsByTagName("dianaF");
        for (int i = 0; i < nodosDianaFacil.getLength(); i++) {
            Element elementoActual = (Element) nodosDianaFacil.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            dianas.add(new DianaFacil(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }
        Log.i("lista dianas", "size " + dianas.size());

        NodeList nodosDianaEfimera = doc.getElementsByTagName("dianaE");
        for (int i = 0; i < nodosDianaEfimera.getLength(); i++) {
            Element elementoActual = (Element) nodosDianaEfimera.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            dianas.add(new DianaEfimera(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }
        Log.i("lista dianas", "size " + dianas.size());

        NodeList nodosDianaJefe = doc.getElementsByTagName("dianaJ");
        for (int i = 0; i < nodosDianaJefe.getLength(); i++) {
            Element elementoActual = (Element) nodosDianaJefe.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            dianas.add(new DianaJefe(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }
        Log.i("lista dianas", "size " + dianas.size());
        return dianas;
    }



    public LinkedList<PowerUp> cargarPowerUpsXML(Context context, int recursoNivel) {
        ParserXML parser = new ParserXML();
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(recursoNivel);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }
        Document doc = parser.getDom(textoFicheroNivel);
        LinkedList<PowerUp> powerUps = new LinkedList<PowerUp>();

        NodeList powerUps1 = doc.getElementsByTagName("powerUpA");
        for (int i = 0; i < powerUps1.getLength(); i++) {
            Element elementoActual = (Element) powerUps1.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            powerUps.add(new PowerUpAtaque(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }

        NodeList powerUps2 = doc.getElementsByTagName("powerUpP");
        for (int i = 0; i < powerUps2.getLength(); i++) {
            Element elementoActual = (Element) powerUps2.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            powerUps.add(new PowerUpPuntuacion(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }
        return powerUps;
    }

    public LinkedList<Obstaculo> cargarObstaculosXML(Context context, int recursoNivel) {
        ParserXML parser = new ParserXML();
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(recursoNivel);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }
        Document doc = parser.getDom(textoFicheroNivel);
        LinkedList<Obstaculo> obstaculos = new LinkedList<Obstaculo>();

        NodeList powerUps1 = doc.getElementsByTagName("obsF");
        for (int i = 0; i < powerUps1.getLength(); i++) {
            Element elementoActual = (Element) powerUps1.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            obstaculos.add(new ObstaculoFacil(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))) {
            });
        }

        NodeList powerUps2 = doc.getElementsByTagName("obsIrr");
        for (int i = 0; i < powerUps2.getLength(); i++) {
            Element elementoActual = (Element) powerUps2.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            obstaculos.add(new ObstaculoIrrompible(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }
        return obstaculos;
    }



    public int getNivelActual() {

        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {

        this.nivelActual = nivelActual;
    }
}
