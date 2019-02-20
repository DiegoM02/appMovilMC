package com.e.appmc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Presentation;
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

import com.e.bd.appmc.Question;

import java.util.ArrayList;


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

    private OnFragmentInteractionListener mListener;
    private int contadorPreguntasNegativas;
    private ArrayList<CriticalPoint> puntoCritico;
    private TextView textPreguntasPositivas;
    private TextView textPreguntasNegativas;
    private Button buttonFinalizarEvaluacion;
    private SummaryAdapter resumenAdapter;
    private RecyclerView recyclerResumen;
    private Dialog dialogoResumen;

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
        confirmarButton = (Button) dialogPregunta.findViewById(R.id.button_confirmar);
        cancelarButton = (Button) dialogPregunta.findViewById(R.id.button_cancelar);
        dimension = (CardView) view.findViewById(R.id.car_view);
        dimension1 = (CardView) view.findViewById(R.id.car_view_1);
        dimension2 = (CardView) view.findViewById(R.id.car_view_2);
        dimension3 = (CardView) view.findViewById(R.id.car_view_3);
        dimension4 = (CardView) view.findViewById(R.id.car_view_4);

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


    public void realizarEvaluacionOtrasDimensiones(View view , ArrayList<Question> questions) {
        dialogPregunta.setContentView(R.layout.contenedor_question);
        adpter = new QuestionAdpater(view.getContext(),questions);
        pagerPregunta = (ViewPager) dialogPregunta.findViewById(R.id.viewPager) ;
        pagerPregunta.setAdapter(adpter);
        dialogPregunta.show();


    }




    public void construirDialogoPersonal(ArrayList<Question> questions, String[] personal,
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
                pagerPreguntaSiNo.setCurrentItem(pagerPreguntaSiNo.getCurrentItem()+1,true);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }





    public void realizarEvaluacionDimensionNormasLaborales(View view , ArrayList<Question> questions) {
        dialogPreguntaSiNo.setContentView(R.layout.contenedor_question_si_no);
        adapter_si_no = new QuestionSiNoAdapter(view.getContext(),questions);
        pagerPreguntaSiNo = (ViewPager) dialogPreguntaSiNo.findViewById(R.id.viewPager_Si_No) ;
        pagerPreguntaSiNo.setAdapter(adapter_si_no);
        dialogPreguntaSiNo.show();

    }

    public void noPreguntaSiNo(View view, ArrayList<Question> questions, String[] personal)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        int index =  pagerPreguntaSiNo.getCurrentItem();
        Question question = questions.get(index);
        this.contadorPreguntasNegativas+=1;
        CriticalPoint punto = new CriticalPoint(new ArrayList<String>(),adapter_si_no.obtenerPuntoDePregunta(question.getPoint_id()),question.getDescription());
        puntoCritico.add(punto);
        if (question.getType() == 1)
        {
            construirDialogoPersonal(questions,personal,inflater);
        }
    }



    public void construirDialogoResumen()
    {



        resumenAdapter = new SummaryAdapter(puntoCritico);


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
        String numeroPreguntasPositivas = String.valueOf(pagerPreguntaSiNo.getAdapter().getCount() - this.contadorPreguntasNegativas);
        String numeroPreguntasNegativas = String.valueOf(this.contadorPreguntasNegativas);
        textPreguntasPositivas = (TextView) v.findViewById(R.id.preguntas_positivas);
        textPreguntasNegativas = (TextView) v.findViewById(R.id.preguntas_negativas);
        textPreguntasPositivas.setText(numeroPreguntasPositivas);
        textPreguntasNegativas.setText(numeroPreguntasNegativas);
        recyclerResumen  = (RecyclerView) v.findViewById(R.id.recyclerResumen);
        recyclerResumen.setAdapter(resumenAdapter);
        recyclerResumen.setItemAnimator(new DefaultItemAnimator());
        recyclerResumen.setLayoutManager(new LinearLayoutManager(this.getContext()));
        dialogoResumen.setContentView(v);
        dialogoResumen.show();

        buttonFinalizarEvaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (puntoCritico.size()> 0) puntoCritico.clear();
                dialogoResumen.dismiss();
                contadorPreguntasNegativas = 0;

            }
        });
    }


    public void cancelarPregunta(View view)
    {
        dialogPregunta.dismiss();

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
}
