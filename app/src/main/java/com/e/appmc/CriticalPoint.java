package com.e.appmc;

import java.util.ArrayList;
import java.util.HashMap;


/*
* Clase de modelo de un punto critico,
* este alamacena el  nombre del punto, y un estructura que genera el resumen
* de ese punto.
*
* */
public class CriticalPoint {


    private String point;
    private String pregunta;
    private HashMap<String,ArrayList<String>> resume;

    public CriticalPoint(String point,String pregunta)
    {
        this.pregunta = pregunta;
        this.point = point;
    }


    public CriticalPoint( String point) {

        this.point = point;
        this.resume = new HashMap<>();
    }

    public String getPoint() {
        return point;
    }


    public ArrayList<String> put(String key, ArrayList<String> value) {
        return resume.put(key, value);
    }

    public HashMap<String, ArrayList<String>> getResume() {
        return resume;
    }
}
