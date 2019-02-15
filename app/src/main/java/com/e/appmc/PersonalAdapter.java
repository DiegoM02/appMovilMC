package com.e.appmc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bd.appmc.Personal;

import java.util.ArrayList;
import java.util.List;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.ViewHolder>
{
    List<Personal> personalList;
    Context context;
    DBMediator dbMediator;
    EvaluationActivity activity;


    public PersonalAdapter(List<Personal> personalList,EvaluationActivity activity,Context mContext) {
        this.personalList = personalList;
        dbMediator = new DBMediator(activity);
        context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_personal,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Personal personal = personalList.get(i);

        viewHolder.textName.setText(personal.getName() + " " + personal.getSurname());
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"The position is:"+i,Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof EvaluationActivity )
                {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder((EvaluationActivity) context);
                    alertDialog.setMessage("¿Estas seguro de eliminarlo?");
                    alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbMediator.actualizarEstadoPesonal(personal.getId());
                            ((EvaluationActivity)context).recargarListaPersonal();
                        }
                    });
                    alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return personalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textName;
        CardView cv;
        FloatingActionButton delete;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textName = (TextView)itemView.findViewById(R.id.textName);
            cv = (CardView)itemView.findViewById(R.id.cv);
            delete = (FloatingActionButton)itemView.findViewById(R.id.delete);
        }
    }
}
