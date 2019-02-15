package com.e.appmc;

import java.util.ArrayList;

public class CriticalPoint {

    private ArrayList<String> personal;
    private String point;
    private String pregunta;

    public CriticalPoint(ArrayList<String> personal, String point, String pregunta) {
        this.personal = personal;
        this.point = point;
        this.pregunta = pregunta;
    }

    public ArrayList<String> getPersonal() {
        return personal;
    }

    public String getPoint() {
        return point;
    }

    public String getPregunta() {
        return pregunta;
    }
}
