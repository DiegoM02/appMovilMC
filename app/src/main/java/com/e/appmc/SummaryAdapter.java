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
    private HashMap<String,ArrayList<String>> resumes;
    private Activity activity;
    public SummaryAdapter(ArrayList<CriticalPoint> puntosCriticos, EvaluationActivity activity) {
        this.puntosCriticos =  puntosCriticos;
        this.activity = activity;
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


        summaryViewHolder.textPunto.setText(puntoCritico.getPoint());

        summaryViewHolder.textPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enter = new Intent(activity,PersonalSummaryActivity.class);
                HashMap<String,ArrayList<String>> hash = puntoCritico.getResume();
                enter.putExtra("hash",hash);
                enter.putExtra("point",puntoCritico.getPoint());
                activity.startActivity(enter);
            }
        });




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
