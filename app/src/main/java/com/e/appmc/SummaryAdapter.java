package com.e.appmc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>{



    private ArrayList<CriticalPoint> puntosCriticos;

    public SummaryAdapter(ArrayList<CriticalPoint> puntosCriticos) {
        this.puntosCriticos =  puntosCriticos;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.summary_layout,viewGroup,false);

        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder summaryViewHolder, int i) {
        CriticalPoint puntoCritico = this.puntosCriticos.get(i);
        summaryViewHolder.textPunto.setText(puntoCritico.getPoint());
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
