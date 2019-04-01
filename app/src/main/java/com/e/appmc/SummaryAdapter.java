package com.e.appmc;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>{



    private ArrayList<CriticalPoint> puntosCriticos;
    private ArrayList<QuestionRating> questions;
    private HashMap<String,ArrayList<String>> resumes;
    private HashMap<String,Float> datos;
    private Activity activity;
    private int mode;
    /*
     * Constructor utiizado cuando es creado un resumen de preguntas si y no.
     * Recibe como parametro un ArrayList de Critical Point y una instancia de EvaluationActivity.
     */
    public SummaryAdapter(ArrayList<CriticalPoint> puntosCriticos, EvaluationActivity activity) {
        this.puntosCriticos =  puntosCriticos;
        this.activity = activity;
        mode=0;
    }
    /*
     * Constructor utiizado cuando es creado un resumen de preguntas con valoracion.
     * Recibe como parametro una instancia de EvaluationActivity y un Hashmap con clave un
     * String que es el nombre de la pregunta y valor un float que corresponde a la valoracion de esta.
     */
    public SummaryAdapter(EvaluationActivity activity,HashMap<String,Float> datos)
    {
        this.activity = activity;
        mode=1;
        puntosCriticos = new ArrayList<>();
        puntosCriticos.add(new CriticalPoint("resumen","lol"));
        this.datos = datos;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.summary_layout,viewGroup,false);

        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder summaryViewHolder, int i) {
        final CriticalPoint puntoCritico = this.puntosCriticos.get(i);

        if(mode==0) {
            summaryViewHolder.textPunto.setText(puntoCritico.getPoint());

            summaryViewHolder.textPunto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent enter = new Intent(activity, PersonalSummaryActivity.class);
                    HashMap<String, ArrayList<String>> hash = puntoCritico.getResume();
                    enter.putExtra("hash", hash);
                    enter.putExtra("rating",0);
                    enter.putExtra("point", puntoCritico.getPoint());
                    activity.startActivity(enter);
                }
            });
        }
        else{
            summaryViewHolder.textPunto.setText("Resumenadsd");
            summaryViewHolder.textPunto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent enter = new Intent(activity, PersonalSummaryActivity.class);
                    enter.putExtra("rating",1);
                    enter.putExtra("hash",datos);
                    activity.startActivity(enter);
                }
            });
        }




    }

    @Override
    public int getItemCount() {
        return this.puntosCriticos.size();
    }

    public class SummaryViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textPunto;

        public SummaryViewHolder(View view)
        {
            super(view);

            textPunto = (TextView) view.findViewById(R.id.textPunto);


        }
    }



}
