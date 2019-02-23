package com.e.appmc;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bd.appmc.Question;
import com.e.bd.appmc.QuestionContract;
import com.e.bd.appmc.SQLiteOpenHelperDataBase;

import java.util.ArrayList;

public class QuestionSiNoAdapter extends PagerAdapter {
    LayoutInflater inflater;
    Context context;
    Button confirmarButton;
    Button cancelarButton;
    TextView textPunto;
    TextView textPregunta;
    TextView indadorPages;
    private SQLiteOpenHelperDataBase bd;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Fragment fragment;


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

    public LayoutInflater getInflater() {
        return inflater;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public QuestionSiNoAdapter(Context context, ArrayList<Question> questions,Fragment fragment) {
        this.context = context;
        this.questions = questions;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == ((LinearLayout)o));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.questions_evaluation_si_no,container,false);
        LinearLayout linear = view.findViewById(R.id.question_layout_si_no);
        bd =  new SQLiteOpenHelperDataBase(view.getContext(),"mcapp",null,1);
        confirmarButton = (Button) view.findViewById(R.id.button_confirmar);
        cancelarButton = (Button) view.findViewById(R.id.button_cancelar);
        colorChanger(position,questions.get(position).getDescription());
        textPregunta = (TextView) view.findViewById(R.id.text_quesion);
        textPunto = (TextView) view.findViewById(R.id.text_point);
        indadorPages = (TextView) view.findViewById(R.id.indicador_pages_si_no);
        String punto = obtenerPuntoDePregunta(questions.get(position).getPoint_id());

        Toast.makeText(this.context,"Count Row: "+position,Toast.LENGTH_LONG);

        textPunto.setText(punto);
        textPregunta.setText(questions.get(position).getDescription());

        indadorPages.setText(position+1+ " de "+ (questions.size()));

        if (position == getCount()-1) confirmarButton.setText("TERMINAR");

        container.addView(view);

        return view;



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);

    }



    public void colorChanger(int position,String name)
    {

        if(fragment instanceof SecurityDimensionFragment)
        {
            int answer = ((SecurityDimensionFragment)fragment).getQuestionAnswered(position).getAnswer();
            Toast.makeText(fragment.getContext(),"Answer" + answer + name,Toast.LENGTH_SHORT);
            if( answer==0)
            {
                confirmarButton.setBackgroundColor(Color.BLUE);

            }
            if(answer ==1)
            {
                cancelarButton.setBackgroundColor(Color.RED);
            }
        }
        if(fragment instanceof FragmentFiveDimension)
        {
            int answer = ((FragmentFiveDimension)fragment).getQuestionAnswered(position).getAnswer();
            Toast.makeText(fragment.getContext(),"Answer" + answer + name,Toast.LENGTH_SHORT);
            if( answer==0)
            {
                confirmarButton.setBackgroundColor(Color.BLUE);

            }
            if(answer ==1)
            {
                cancelarButton.setBackgroundColor(Color.RED);
            }

        }




    }
}
