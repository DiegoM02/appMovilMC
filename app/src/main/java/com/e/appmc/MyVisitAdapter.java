package com.e.appmc;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.e.appmc.bd.Visit;

import java.util.ArrayList;
import java.util.List;

public class MyVisitAdapter extends RecyclerView.Adapter<MyVisitAdapter.ViewHolder>{



    List<VisitModel> visitList;
    Context context;
    DBMediator dbMediator;

    public MyVisitAdapter(List<VisitModel> visitlList, Activity activity) {
        this.visitList = new ArrayList<VisitModel>();
        this.visitList.addAll(visitlList);
        dbMediator = new DBMediator((AppCompatActivity) activity);
    }

    @NonNull
    @Override
    public MyVisitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_visit,viewGroup,false);
         MyVisitAdapter.ViewHolder viewHolder = new MyVisitAdapter.ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyVisitAdapter.ViewHolder viewHolder, final int i) {
        final VisitModel visit = visitList.get(i);

        viewHolder.textNameFacility.setText(visit.getName().toString());
        viewHolder.textDateVisit.setText(visit.getDate().toString());



    }

    @Override
    public int getItemCount() {
        return visitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textNameFacility;
        TextView textDateVisit;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textNameFacility = (TextView)itemView.findViewById(R.id.text_name_facility);
            textDateVisit = (TextView)itemView.findViewById(R.id.text_date_visit);
            cv = (CardView)itemView.findViewById(R.id.car_visit);

        }
    }





}
