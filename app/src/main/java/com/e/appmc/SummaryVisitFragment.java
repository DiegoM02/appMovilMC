package com.e.appmc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link SummaryVisitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryVisitFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DBMediator mediator;




    public SummaryVisitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SummaryVisitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryVisitFragment newInstance(String param1, String param2) {
        SummaryVisitFragment fragment = new SummaryVisitFragment();
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
        mediator = new DBMediator(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary_visit, container, false);

        HashMap<String,Integer> quantityVisits = mediator.obtenerCentroVisitas();
        TextView mostVisitName = view.findViewById(R.id.most_visit_name);
        TextView mostVisitNumber = view.findViewById(R.id.most_visit_number);
        TextView lessVisitName = view.findViewById(R.id.less_visit_name);
        TextView lessVisitNumber = view.findViewById(R.id.less_visit_number);
        ArrayList<String> keys = new ArrayList<>(quantityVisits.keySet());
        mostVisitName.setText(keys.get(0));
        mostVisitNumber.setText("" + quantityVisits.get(keys.get(0)));
        if(keys.size()>1)
        {
            lessVisitName.setText(keys.get(1));
            lessVisitNumber.setText("" + quantityVisits.get(keys.get(1)));
        }
        else
        {
            lessVisitName.setText(keys.get(0));
            lessVisitNumber.setText("" + quantityVisits.get(keys.get(0)));
        }
        TextView lastVisitName = view.findViewById(R.id.last_visit_name);
        TextView lastVisitNumber = view.findViewById(R.id.last_visit_date);
        try {
            HashMap<String,String> lastVisit = mediator.visitaReciente();
            ArrayList<String> key = new ArrayList<>(lastVisit.keySet());
            lastVisitName.setText(key.get(0));
            lastVisitNumber.setText("" + lastVisit.get(key.get(0)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

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
    //public interface OnFragmentInteractionListener {
     //   // TODO: Update argument type and name
      //  void onFragmentInteraction(Uri uri);
    //}
}
