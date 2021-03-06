package com.e.appmc;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.appmc.bd.Question;
import com.e.appmc.bd.SQLiteOpenHelperDataBase;

import java.util.ArrayList;

public class QuestionAdpater extends PagerAdapter   {

    LayoutInflater inflater;
    Context context;
    Button confirmarButton;
    Button cancelarButton;
    TextView textPunto;
    TextView textPregunta;
    TextView indicadorPages;
    RatingBar barPregunta;
    SQLiteOpenHelperDataBase bd;
    ArrayList<Question> questions;
    private float valoracion;
    private Fragment fragment;
    private static final String BUNDLE_PAGER_VIEW_STATE = "state_pager";


    public QuestionAdpater(Context context, ArrayList<Question> questions,Fragment fragment) {
        this.context = context;
        this.questions = questions;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return this.questions.size();
    }

    /*
     * Metodo encargado de obtener el nombre de un punto determinado realizando una consulta
     * a la base de datos interna del dispositivo.
     * Recibe como parametro un entero con el id del punto.
     * Retorna un String con el nombre del punto buscado o un string vacio en caso de no
     * encontrar coincidencias.
     */
    public String obtenerPuntoDePregunta(int id) {
        String query = "SELECT point.name FROM point WHERE point.id = " + id;
        String name_point = "";

        Cursor data = bd.doSelectQuery(query);

        Toast.makeText(this.context, "Count Row: " + data.getCount(), Toast.LENGTH_LONG);
        if (data.moveToFirst()) {
            name_point = data.getString(data.getColumnIndex("name"));

        }

        return name_point;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == ((LinearLayout) o));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.questions_evaluation, container, false);
        LinearLayout linear = view.findViewById(R.id.question_layout);
        bd = new SQLiteOpenHelperDataBase(view.getContext(), "mcapp", null, 1);
        confirmarButton = (Button) view.findViewById(R.id.button_confirmar);
        barPregunta = (RatingBar) view.findViewById(R.id.rating_bar_pregunta);
        barPregunta.setRating(fillRating(position));
        barPregunta.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                valoracion = rating;

            }
        });
        cancelarButton = (Button) view.findViewById(R.id.button_cancelar);
        textPregunta = (TextView) view.findViewById(R.id.text_quesion);
        textPunto = (TextView) view.findViewById(R.id.text_point);
        indicadorPages = (TextView) view.findViewById(R.id.indicador_pages);
        textPunto.setText(obtenerPuntoDePregunta(this.questions.get(position).getPoint_id()));
        textPregunta.setText(questions.get(position).getDescription());
        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callerConfirmClick(view,position);
            }
        });
        indicadorPages.setText(position + 1 + " de " + questions.size());
        if (position == getCount() - 1) confirmarButton.setText("TERMINAR");
        container.addView(view);

        return view;


    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);

    }
    /*
     * Metodo encargado de llamar a confirmClick dentro de los fragmentos.
     * Recibe como parametro de entrada un objeto de clase view y un entero
     * con la posicion de la pregunta que se modificara.
     */
    public void callerConfirmClick(View view,int position)
    {

        if(fragment instanceof SecurityDimensionFragment)
        {
            ((SecurityDimensionFragment)fragment).confirmClick(view,questions,position,valoracion);
        }
        else
        {
            ((FragmentFiveDimension)fragment).confirmClick(view,questions,position,valoracion);
        }
    }
    /*
     * Metodo encargado de obtener el rating guardado de una preunta en el arreglo presente en los
     * fragment.
     * Recibe como parametro un entero con la posicion de a pregunta.
     * Retorna un float con la valoracion de dicha pregunta.
     */
    public float fillRating(int position)
    {
        Toast.makeText(fragment.getContext(),"entre",Toast.LENGTH_SHORT);
        if(fragment instanceof SecurityDimensionFragment)
        {
            return ((SecurityDimensionFragment)fragment).getRating(position);
        }
        else
        {
            return ((FragmentFiveDimension)fragment).getRating(position);
        }

    }





}
