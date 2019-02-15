package com.e.appmc;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bd.appmc.Question;
import com.e.bd.appmc.SQLiteOpenHelperDataBase;

import java.util.ArrayList;

public class QuestionAdpater extends PagerAdapter {

     LayoutInflater inflater;
     Context context;
     Button confirmarButton;
     Button cancelarButton;
     TextView textPunto;
     TextView textPregunta;
     TextView indicadorPages;
     SQLiteOpenHelperDataBase bd;
     ArrayList<Question> questions;





    public QuestionAdpater(Context context, ArrayList<Question> questions) {
       this.context = context;
       this.questions = questions;
    }

    @Override
    public int getCount() {
        return this.questions.size();
    }


    public String obtenerPuntoDePregunta(int id)
    {
        String query = "SELECT point.name FROM point WHERE point.id = " + id;
        String name_point = "";

        Cursor data = bd.doSelectQuery(query);

        Toast.makeText(this.context,"Count Row: "+data.getCount(),Toast.LENGTH_LONG);
        if (data.moveToFirst())
        {
            name_point = data.getString(data.getColumnIndex("name"));

        }

        return name_point;
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == ((LinearLayout)o));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.questions_evaluation,container,false);
        LinearLayout linear = view.findViewById(R.id.question_layout);
        bd = new SQLiteOpenHelperDataBase(view.getContext(),"mcapp",null,1);
        confirmarButton = (Button) view.findViewById(R.id.button_confirmar);
        cancelarButton = (Button) view.findViewById(R.id.button_cancelar);
        textPregunta = (TextView) view.findViewById(R.id.text_quesion);
        textPunto = (TextView) view.findViewById(R.id.text_point);
        indicadorPages = (TextView) view.findViewById(R.id.indicador_pages);
        textPunto.setText(obtenerPuntoDePregunta(this.questions.get(position).getPoint_id()));
        textPregunta.setText(questions.get(position).getDescription());
        indicadorPages.setText(position+1+" de "+questions.size());
        if (position == getCount()-1) confirmarButton.setText("TERMINAR");
        container.addView(view);

        return view;



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);

    }
}
