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

import com.e.bd.appmc.Point;
import com.e.bd.appmc.Question;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecurityDimensionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecurityDimensionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityDimensionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




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
    private View view;
    private Button confirmarButton;
    private Button cancelarButton;
    private RecyclerView recyclerResumen;
    private SummaryAdapter resumenAdapter;
    private Dialog dialogoResumen;
    private Button buttonFinalizarEvaluacion;
    ArrayList<CriticalPoint> puntoCritico;
    private String puntoActual;
    private String preguntaActual;
    private OnFragmentInteractionListener mListener;
    ArrayList<QuestionRating> questionsRaitings;

    public SecurityDimensionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecurityDimensionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecurityDimensionFragment newInstance(String param1, String param2) {
        SecurityDimensionFragment fragment = new SecurityDimensionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.puntoCritico = new ArrayList<>();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_security_dimension, container, false);
        dialogPregunta = new Dialog(view.getContext());
        dialogPreguntaSiNo = new Dialog(view.getContext());
        dialogoResumen = new Dialog(view.getContext());
        dimension = (CardView) view.findViewById(R.id.cardView);
        dimension1 = (CardView) view.findViewById(R.id.cardView2);
        dimension2 = (CardView) view.findViewById(R.id.cardView3);
        dimension3 = (CardView) view.findViewById(R.id.cardView4);
        disableCardView();
        confirmarButton = (Button) dialogPregunta.findViewById(R.id.button_confirmar);
        cancelarButton = (Button) dialogPregunta.findViewById(R.id.button_cancelar);




        return view;
    }


    public void disableCardView()
    {
        dimension.setEnabled(false);
        dimension1.setEnabled(false);
        dimension2.setEnabled(false);
        dimension3.setEnabled(false);
    }

    public void enableCardView()
    {
        dimension.setEnabled(true);
        dimension1.setEnabled(true);
        dimension2.setEnabled(true);
        dimension3.setEnabled(true);
    }

    public void realizarEvaluacionOtrasDimensiones(View view, ArrayList<Question> questions) {
        dialogPregunta.setContentView(R.layout.contenedor_question);
        adpter = new QuestionAdpater(view.getContext(), questions);
        pagerPregunta = (ViewPager) dialogPregunta.findViewById(R.id.viewPager) ;
        pagerPregunta.setAdapter(adpter);
        dialogPregunta.show();


    }


    public void realizarEvaluacionDimensionNormasLaborales(View view, ArrayList<Question>  questions) {
        dialogPreguntaSiNo.setContentView(R.layout.contenedor_question_si_no);
        adapter_si_no = new QuestionSiNoAdapter(view.getContext(), questions);
        pagerPreguntaSiNo = (ViewPager) dialogPreguntaSiNo.findViewById(R.id.viewPager_Si_No) ;
        pagerPreguntaSiNo.setAdapter(adapter_si_no);
        dialogPreguntaSiNo.show();

    }


    public void confirmarPregunta(View view)
    {
        if (pagerPregunta.getCurrentItem() == pagerPregunta.getAdapter().getCount()-1)
        {
           construirDialogoResumen();
        }else
        {
            pagerPregunta.setCurrentItem(pagerPregunta.getCurrentItem()+1,true);
        }



    }

    public void noPreguntaSiNo(View view, ArrayList<Question> questions, String[] personal)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
       int index =  pagerPreguntaSiNo.getCurrentItem();
       Question question = questions.get(index);

       if (question.getType() == 1)
       {
           this.puntoActual = ((EvaluationActivity)getActivity()).obtenerNombrePunto(question.getPoint_id());
           this.preguntaActual = question.getDescription();
           construirDialogoPersonal(questions,personal,inflater);
       }
    }

    public void construirDialogoPersonal(ArrayList<Question> questions, final String[] personal,
                                         LayoutInflater inflater)
    {
        final ArrayList<Integer> mSelectedItems = new ArrayList();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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



    public void cancelarPregunta(View view)
    {
        dialogPregunta.dismiss();

    }

    public void construirDialogoResumen()
    {
        //puntoCritico = new ArrayList<CriticalPoint>();

        //puntoCritico.add(new CriticalPoint(new ArrayList<String>(),"Operativo","Prueba"));
        //puntoCritico.add(new CriticalPoint(new ArrayList<String>(),"Recursos Humanos","Prueba"));
        //puntoCritico.add(new CriticalPoint(new ArrayList<String>(),"Prevencion de Riesgos","Prueba"));


        resumenAdapter = new SummaryAdapter(puntoCritico,(EvaluationActivity) this.getActivity());

        if (dialogPreguntaSiNo.isShowing())
        {
            dialogPreguntaSiNo.dismiss();
        }else if (dialogPregunta.isShowing())
        {
           dialogPregunta.dismiss();
        }

        LayoutInflater layoutInflater = getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.summaryrecycler,null);
        buttonFinalizarEvaluacion = (Button) v.findViewById(R.id.button_finalizar);

        recyclerResumen = (RecyclerView) v.findViewById(R.id.recyclerResumen);
        recyclerResumen.setAdapter(resumenAdapter);
        recyclerResumen.setItemAnimator(new DefaultItemAnimator());
        recyclerResumen.setLayoutManager(new LinearLayoutManager(this.getContext()));
        dialogoResumen.setContentView(v);
        dialogoResumen.show();

        buttonFinalizarEvaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoResumen.dismiss();
                puntoCritico.clear();
            }
        });


    }

    public void confirmarPreguntaSiNo(View view)
    {
        if (pagerPreguntaSiNo.getCurrentItem() == pagerPreguntaSiNo.getAdapter().getCount()-1)
        {
            construirDialogoResumen();

        }else
        {
            pagerPreguntaSiNo.setCurrentItem(pagerPreguntaSiNo.getCurrentItem()+1,true);
        }

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

    public void fillQuestionPoints(ArrayList<Question> questions)
    {
        for(int i =0;i<questions.size();i++)
        {
            Question question = questions.get(i);
            QuestionRating questionRating = new QuestionRating(question.getDescription(),0);
            questionsRaitings.add(questionRating);
        }
    }

    public void setRating(int index, int rating)
    {
        this.questionsRaitings.get(index).setPoint(rating);
    }

    public int getRating(int index)
    {
        return this.questionsRaitings.get(index).getPoint();
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
}
