package com.e.appmc;

import java.util.ArrayList;

public class Summary {


    private int numeroPreguntasPositivas;
    private int numeroPreguntasNegativas;
    private ArrayList<CriticalPoint> puntosCriticos;


    public Summary(int numeroPreguntasPositivas, int numeroPreguntasNegativas, ArrayList<CriticalPoint> puntosCriticos) {
        this.numeroPreguntasPositivas = numeroPreguntasPositivas;
        this.numeroPreguntasNegativas = numeroPreguntasNegativas;
        this.puntosCriticos = puntosCriticos;
    }

    public int getNumeroPreguntasPositivas() {
        return numeroPreguntasPositivas;
    }

    public int getNumeroPreguntasNegativas() {
        return numeroPreguntasNegativas;
    }

    public ArrayList<CriticalPoint> getPuntosCriticos() {
        return puntosCriticos;
    }
}
