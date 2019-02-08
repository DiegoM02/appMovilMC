package com.e.appmc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionSiNoAdapter extends PagerAdapter {
    LayoutInflater inflater;
    Context context;
    Button confirmarButton;
    Button cancelarButton;
    TextView textPunto;
    TextView textPregunta;
    TextView indadorPages;

    public String [] puntos = {"Aseo","Jardin","Baños"};
    public  String [] questions = {"Pregunta de prueba1","Pregunta de prueba2","Pregunta de prueba3"};


    public QuestionSiNoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return puntos.length;
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
        confirmarButton = (Button) view.findViewById(R.id.button_confirmar);
        cancelarButton = (Button) view.findViewById(R.id.button_cancelar);
        textPregunta = (TextView) view.findViewById(R.id.text_quesion);
        textPunto = (TextView) view.findViewById(R.id.text_point);
        indadorPages = (TextView) view.findViewById(R.id.indicador_pages_si_no);
        textPunto.setText(puntos[position]);
        textPregunta.setText(questions[position]);
        indadorPages.setText(position+1+ " de "+ questions.length);
        container.addView(view);

        return view;



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);

    }
}
