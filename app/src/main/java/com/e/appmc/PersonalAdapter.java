package com.e.appmc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.bd.appmc.Personal;

import java.util.List;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.ViewHolder>
{
    List<Personal> personalList;
    Context context;

    public PersonalAdapter(List<Personal> personalList) {
        personalList = personalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Personal personal = personalList.get(i);

        viewHolder.textRut.setText(personal.getRut());
        viewHolder.textName.setText(personal.getName());

    }

    @Override
    public int getItemCount() {
        return personalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textRut;
        TextView textName;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textRut = (TextView) itemView.findViewById(R.id.textRut);
            textName = (TextView)itemView.findViewById(R.id.textName);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}