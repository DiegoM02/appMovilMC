package com.e.appmc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e.appmc.bd.Question;
import com.e.appmc.bd.Summary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFiveDimension.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFiveDimension#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFiveDimension extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Dialog dialogPregunta;
    private Dialog dialogPreguntaSiNo;
    private ViewPager pagerPregunta;
    private ViewPager pagerPreguntaSiNo;
    private QuestionAdpater adpter;
    private QuestionSiNoAdapter adapter_si_no;
    private CardView dimension;
    private CardView dimension1;
    private CardView dimension2;
    private CardView dimension3;
    private CardView dimension4;
    private View view;
    private Button confirmarButton;
    private Button cancelarButton;
    private TextView dimension1Valoracion;
    private TextView dimension2Valoracion;
    private TextView dimension3Valoracion;
    private TextView dimension4Valoracion;

    private OnFragmentInteractionListener mListener;
    private int contadorPreguntasNegativas;
    private ArrayList<CriticalPoint> puntoCritico;
    private ArrayList<QuestionRating> questionsRaitings;
    private boolean flagQuestionsRaitings;
    private TextView textPreguntasPositivas;
    private TextView textPreguntasNegativas;
    private Button buttonFinalizarEvaluacion;
    private SummaryAdapter resumenAdapter;
    private RecyclerView recyclerResumen;
    private Dialog dialogoResumen;
    private ArrayList<Assessment> valoraciones;
    private ArrayList<QuestionAnswered> questionsAnswered;
    private boolean flagQuestionsAnswered;
    private int contadorPreguntasReporbadas = 0;
    private int dimensionActiva = 0;
    private String puntoActual;
    private String preguntaActual;

    public FragmentFiveDimension() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFiveDimension.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFiveDimension newInstance(String param1, String param2) {
        FragmentFiveDimension fragment = new FragmentFiveDimension();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_five_dimension, container, false);
        dialogPregunta = new Dialog(view.getContext());
        dialogPreguntaSiNo = new Dialog(view.getContext());
        dimension = (CardView) view.findViewById(R.id.car_view);
        dialogoResumen = new Dialog(view.getContext());
        puntoCritico = new ArrayList<CriticalPoint>();
        valoraciones = new ArrayList<Assessment>();
        questionsRaitings = new ArrayList<QuestionRating>();
        flagQuestionsRaitings=true;
        questionsAnswered = new ArrayList<QuestionAnswered>();
        flagQuestionsAnswered = true;
        confirmarButton = (Button) dialogPregunta.findViewById(R.id.button_confirmar);
        cancelarButton = (Button) dialogPregunta.findViewById(R.id.button_cancelar);
        dimension = (CardView) view.findViewById(R.id.car_view);
        dimension1 = (CardView) view.findViewById(R.id.car_view_1);
        dimension2 = (CardView) view.findViewById(R.id.car_view_2);
        dimension3 = (CardView) view.findViewById(R.id.car_view_3);
        dimension4 = (CardView) view.findViewById(R.id.car_view_4);
        dimension1Valoracion = (TextView) view.findViewById(R.id.text_1);
        dimension2Valoracion = (TextView) view.findViewById(R.id.text_2);
        dimension3Valoracion = (TextView) view.findViewById(R.id.text_3);
        dimension4Valoracion = (TextView) view.findViewById(R.id.text_4);

        disableCardView();
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void realizarEvaluacionOtrasDimensiones(View view , ArrayList<Question> questions, int dimensionActiva) {
        this.dimensionActiva = dimensionActiva;
        dialogPregunta.setContentView(R.layout.contenedor_question);
        if(flagQuestionsRaitings)
        {
            this.fillQuestionPoints(questions);
            flagQuestionsRaitings=false;
        }
        adpter = new QuestionAdpater(view.getContext(),questions,this);
        pagerPregunta = (ViewPager) dialogPregunta.findViewById(R.id.viewPager) ;
        pagerPregunta.setAdapter(adpter);
        dialogPregunta.show();


    }




    public void construirDialogoPersonal(ArrayList<Question> questions, final String[] personal,
                                         LayoutInflater inflater)
    {
        final ArrayList<Integer> mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(inflater.inflate(R.layout.personal_dialogo, null));
        builder.setMultiChoiceItems(personal,null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                    }});
        builder.setNegativeButton("Cancelar" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Confirmar" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addCriticalPoint(personal,mSelectedItems);
                pagerPreguntaSiNo.setCurrentItem(pagerPreguntaSiNo.getCurrentItem()+1,true);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addCriticalPoint(String [] personal, ArrayList<Integer> selectedItems)
    {
        ArrayList<String> personals= new ArrayList<>();
        for(Integer selected : selectedItems)
        {
            personals.add(personal[selected]);
        }
        for(CriticalPoint cPoint: this.puntoCritico)
        {
            if(cPoint.getPoint().equals(this.puntoActual))
            {
                cPoint.put(this.preguntaActual,personals);
                return;
            }
        }
        CriticalPoint cPoint = new CriticalPoint(this.puntoActual);
        cPoint.put(this.preguntaActual,personals);
        this.puntoCritico.add(cPoint);


    }





    public void realizarEvaluacionDimensionNormasLaborales(View view , ArrayList<Question> questions, int dimensionActiva) {
        this.dimensionActiva = dimensionActiva;
        dialogPreguntaSiNo.setContentView(R.layout.contenedor_question_si_no);
        if(flagQuestionsAnswered)
        {
            this.fillQuestionAnswered(questions);
            flagQuestionsAnswered=false;
        }
        adapter_si_no = new QuestionSiNoAdapter(view.getContext(),questions,this);
        pagerPreguntaSiNo = (ViewPager) dialogPreguntaSiNo.findViewById(R.id.viewPager_Si_No) ;
        pagerPreguntaSiNo.setAdapter(adapter_si_no);
        dialogPreguntaSiNo.show();

    }

    public void noPreguntaSiNo(View view, ArrayList<Question> questions, String[] personal)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        int index =  pagerPreguntaSiNo.getCurrentItem();
        Question question = questions.get(index);
        this.addQuestionAnsweredNegative(question.getDescription(),index);
        this.contadorPreguntasNegativas+=1;
        if (question.getType() == 1)
        {
            this.puntoActual = ((EvaluationActivity)getActivity()).obtenerNombrePunto(question.getPoint_id());
            this.preguntaActual = question.getDescription();
            construirDialogoPersonal(questions,personal,inflater);
        }
    }






    public void setValoracionPromedioDimension1() {

        float valor = calcularValoracionSiNoPromedio();
        dimension1Valoracion.setText(String.valueOf(valor));
        if (valor < 3.0) {
            dimension1Valoracion.setBackgroundResource(R.color.negativo);
        } else if (valor > 3.0) {
            dimension1Valoracion.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }

    public float calcularValoracionSiNoPromedio() {

        float pregunta = pagerPreguntaSiNo.getAdapter().getCount() - this.contadorPreguntasNegativas;

        float resultado = (pregunta * 5) / pagerPreguntaSiNo.getAdapter().getCount();

        return Math.round(resultado);
    }


    public void setValoracionPromedioDimension2() {

        float valor = calcularValoracionPromedio();
        dimension2Valoracion.setText(String.valueOf(valor));
        if (valor < 3.0) {
            dimension2Valoracion.setBackgroundResource(R.color.negativo);
        } else if (valor > 3.0) {
            dimension2Valoracion.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }

    public void setValoracionPromedioDimension3() {

        float valor = calcularValoracionPromedio();
        dimension3Valoracion.setText(String.valueOf(valor));
        if (valor < 3.0) {
            dimension3Valoracion.setBackgroundResource(R.color.negativo);
        } else if (valor > 3.0) {
            dimension3Valoracion.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }

    public void setValoracionPromedioDimension4() {

        float valor = calcularValoracionPromedio();
        dimension4Valoracion.setText(String.valueOf(valor));
        if (valor < 3.0) {
        } else if (valor > 3.0) {
            dimension4Valoracion.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }


    public void elegirDimension() {
        switch (this.dimensionActiva) {
            case 1:
                setValoracionPromedioDimension1();
                break;
            case 2:
                setValoracionPromedioDimension2();
                break;
            case 3:
                setValoracionPromedioDimension3();
                break;
            case 4:
                setValoracionPromedioDimension4();
                break;
        }
    }


    public float calcularValoracionPromedio() {

        float suma = 0;
        for (Assessment v : this.valoraciones
        ) {

            suma = suma + v.getAssessment();
        }

        int numeroPreguntas = pagerPregunta.getAdapter().getCount();
        float promedio = Math.round(suma / numeroPreguntas);


        return Math.round(promedio);
    }



    public void construirDialogoResumen(final View view) {

        this.addSummaryToDB();
        resumenAdapter = new SummaryAdapter(puntoCritico,(EvaluationActivity) this.getActivity());
        if (dialogPreguntaSiNo.isShowing()) {
            dialogPreguntaSiNo.dismiss();
        } else if (dialogPregunta.isShowing()) {
            dialogPregunta.dismiss();
        }

        String numeroPreguntasPositivas = "";
        String numeroPreguntasNegativas = "";

        LayoutInflater layoutInflater = getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.summaryrecycler, null);
        buttonFinalizarEvaluacion = (Button) v.findViewById(R.id.button_finalizar);

        if (this.dimensionActiva == 1) {
            numeroPreguntasPositivas =
                    String.valueOf(pagerPreguntaSiNo.getAdapter().getCount() - this.contadorPreguntasNegativas);
            numeroPreguntasNegativas = String.valueOf(this.contadorPreguntasNegativas);
        } else {

            numeroPreguntasPositivas =
                    String.valueOf(pagerPregunta.getAdapter().getCount() - this.contadorPreguntasReporbadas);
            numeroPreguntasNegativas = String.valueOf(this.contadorPreguntasReporbadas);
        }

        textPreguntasPositivas = (TextView) v.findViewById(R.id.preguntas_positivas);
        textPreguntasNegativas = (TextView) v.findViewById(R.id.preguntas_negativas);
        textPreguntasPositivas.setText(numeroPreguntasPositivas);
        textPreguntasNegativas.setText(numeroPreguntasNegativas);
        recyclerResumen = (RecyclerView) v.findViewById(R.id.recyclerResumen);
        recyclerResumen.setAdapter(resumenAdapter);
        recyclerResumen.setItemAnimator(new DefaultItemAnimator());
        recyclerResumen.setLayoutManager(new LinearLayoutManager(this.getContext()));
        dialogoResumen.setContentView(v);
        dialogoResumen.show();

        buttonFinalizarEvaluacion.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (puntoCritico.size() > 0) puntoCritico.clear();
                elegirDimension();
                dialogoResumen.dismiss();
                contadorPreguntasNegativas = 0;
                contadorPreguntasReporbadas = 0;

                if (valoraciones.size() > 0) valoraciones.clear();


            }
        });


    }



    public void cancelarPregunta(View view)
    {
        dialogPregunta.dismiss();

    }

    public void alertaValoracionVacia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Debes evaluar la pregunta");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog diaologo = builder.create();
        diaologo.show();

    }

    public void agregarValoracion(float valoracion, int posicion, String pregunta) {

        int opcion = existeValoracion(posicion,pregunta);
        if(opcion ==-1) {
            Assessment valoraciones = new Assessment(posicion, valoracion, pregunta);
            this.valoraciones.add(valoraciones);
        }
        else{
            this.valoraciones.get(opcion).setAssessment(valoracion);
        }
    }

    public int existeValoracion(int posicion,String pregunta)
    {
        for(int i =0; i<this.valoraciones.size();i++)
        {
            Assessment valoracion= this.valoraciones.get(i);
            if(valoracion.getQuestion().equals(pregunta) && valoracion.getPosition()== posicion )
            {
                return i;
            }
        }
        return -1;
    }



    public void confirmarPregunta(View view, ArrayList<Question> questions) {


        if (pagerPregunta.getCurrentItem() == pagerPregunta.getAdapter().getCount() - 1) {
            if(checkAllQuestionPointed()) {
                this.questionsRaitings.clear();
                this.flagQuestionsRaitings=true;
                if (adpter.getValoracion() < 3) {
                    int index = pagerPregunta.getCurrentItem();
                    Question question = questions.get(index);
                    CriticalPoint punto = new CriticalPoint(adpter.obtenerPuntoDePregunta(question.getPoint_id()), question.getDescription());
                    puntoCritico.add(punto);
                    this.contadorPreguntasReporbadas += 1;
                }

                agregarValoracion(adpter.getValoracion(), pagerPregunta.getCurrentItem(),
                        questions.get(pagerPregunta.getCurrentItem()).getDescription());
                construirDialogoResumen(view);
            }
            else {
                Toast.makeText(this.getContext(),"Aun quedan preguntas que responder",Toast.LENGTH_LONG).show();
            }

        } else {

            if (adpter.getValoracion() == 0) {

                alertaValoracionVacia();
            } else if (adpter.getValoracion() > 0) {

                agregarValoracion(adpter.getValoracion(), pagerPregunta.getCurrentItem(),
                        questions.get(pagerPregunta.getCurrentItem()).getDescription());
                pagerPregunta.setCurrentItem(pagerPregunta.getCurrentItem() + 1, true);
                if (adpter.getValoracion() < 3) {
                    int index = pagerPregunta.getCurrentItem();
                    Question question = questions.get(index);
                    CriticalPoint punto = new CriticalPoint( adpter.obtenerPuntoDePregunta(question.getPoint_id()), question.getDescription());
                    puntoCritico.add(punto);
                    this.contadorPreguntasReporbadas += 1;
                }

                adpter.setValoracion(0);


            }



        }


    }

    public void confirmarPreguntaSiNo(View view,ArrayList<Question> questions)
    {
        Question question = questions.get(pagerPreguntaSiNo.getCurrentItem());
        this.addQuestionAnsweredPositive(question.getDescription(),pagerPreguntaSiNo.getCurrentItem());
        if (pagerPreguntaSiNo.getCurrentItem() == pagerPreguntaSiNo.getAdapter().getCount()-1)
        {
            if(checkAllQuestionAnswered()) {
                this.flagQuestionsAnswered=true;
                this.questionsAnswered.clear();
                construirDialogoResumen(view);
            }
            else{
                Toast.makeText(this.getContext(),"Aun quedan preguntas que responder",Toast.LENGTH_LONG).show();
            }

        }else
        {
            pagerPreguntaSiNo.setCurrentItem(pagerPreguntaSiNo.getCurrentItem()+1,true);
        }

    }

    public void addQuestionAnsweredPositive(String name, int position)
    {

        this.questionsAnswered.get(position).setAnswer(0);

    }

    public void addQuestionAnsweredNegative(String name,int position)
    {

        this.questionsAnswered.get(position).setAnswer(1);

    }

    public void fillQuestionAnswered(ArrayList<Question> questions)
    {
        for(int i=0;i<questions.size();i++)
        {
            Question question =questions.get(i);
            this.questionsAnswered.add(new QuestionAnswered(question.getDescription(),i,-1));
        }
    }

    public QuestionAnswered getQuestionAnswered(int position)
    {
        return this.questionsAnswered.get(position);
    }

    public void disableCardView()
    {
        dimension.setEnabled(false);
        dimension1.setEnabled(false);
        dimension2.setEnabled(false);
        dimension3.setEnabled(false);
        dimension4.setEnabled(false);
    }

    public void enableCardView()
    {
        dimension.setEnabled(true);
        dimension1.setEnabled(true);
        dimension2.setEnabled(true);
        dimension3.setEnabled(true);
        dimension4.setEnabled(true);
    }

    public void fillQuestionPoints(ArrayList<Question> questions)
    {
        for(int i =0;i<questions.size();i++)
        {
            Question question = questions.get(i);
            QuestionRating questionRating = new QuestionRating(question.getDescription(),0);
            questionsRaitings.add(questionRating);
        }
    }

    public void setRating(int index, float rating)
    {
        this.questionsRaitings.get(index).setPoint(rating);
    }

    public float getRating(int index)
    {
        return this.questionsRaitings.get(index).getPoint();
    }

    public void confirmClick(View view, ArrayList<Question> questions,int i ,float valoracion)
    {
        setRating(i,valoracion);
        confirmarPregunta(view,questions);
    }

    public boolean checkAllQuestionPointed()
    {
        for(int i=0; i<this.questionsRaitings.size()-1;i++)
        {
            QuestionRating question = questionsRaitings.get(i);
            if(question.getPoint()==0){ return false;}
        }
        return true;
    }

    public boolean checkAllQuestionAnswered()
    {
        for(int i =0;i<this.questionsAnswered.size();i++)
        {
            QuestionAnswered question = questionsAnswered.get(i);
            if(question.getAnswer()==-1){
                return false;
            }
        }
        return true;
    }

    public String createContentSummary()
    {
        String content = "";
        content = "Realizado por " + ((EvaluationActivity)getActivity()).obtenerNombreUsuario() + "\n";
        for(CriticalPoint point : this.puntoCritico)
        {
            content =content + point.getPoint() + "\n";
            HashMap<String,ArrayList<String>> resume = point.getResume();
            ArrayList<String> questions = new ArrayList<>(resume.keySet());
            for(String question : questions)
            {
                content = content + question +"\n";
                ArrayList<String> personal = resume.get(question);
                for(int i =0;i<personal.size();i++)
                {
                    content = content + "- " + personal.get(i) +"\n";
                }
            }

            content = content + "\n\n";
        }

        return content;
    }

    public void addSummaryToDB()
    {
        String content = createContentSummary();
        int faccilityId = ((EvaluationActivity)getActivity()).getIdCentroActual();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = dateformat.format(c.getTime());
        com.e.appmc.bd.Summary summary = new Summary(content,date,faccilityId,"no");
        ((EvaluationActivity)getActivity()).insertarResumen(summary);

    }
}
