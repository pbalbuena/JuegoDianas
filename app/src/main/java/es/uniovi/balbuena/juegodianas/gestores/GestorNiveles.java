package es.uniovi.balbuena.juegodianas.gestores;

import android.content.Context;

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
import es.uniovi.balbuena.juegodianas.modelos.DianaFacil;
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

        LinkedList<Diana> enemigos = new LinkedList<Diana>();

        NodeList nodosEnemigoNave = doc.getElementsByTagName("enemyN");
        for (int i = 0; i < nodosEnemigoNave.getLength(); i++) {
            Element elementoActual = (Element) nodosEnemigoNave.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            enemigos.add(new DianaFacil(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }
/*
        NodeList nodosEnemigoHelicoptero = doc.getElementsByTagName("enemyH");
        for (int i = 0; i < nodosEnemigoHelicoptero.getLength(); i++) {
            Element elementoActual = (Element) nodosEnemigoHelicoptero.item(i);
            String x = parser.getValor(elementoActual, "x");
            String y = parser.getValor(elementoActual, "y");
            enemigos.add(new DianaNormal(context, Ar.x(Double.parseDouble(x)), Ar.y(Double.parseDouble(y))));
        }*/
        return enemigos;
    }





    public int getNivelActual() {

        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {

        this.nivelActual = nivelActual;
    }
}
