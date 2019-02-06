package com.e.appmc;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.bd.appmc.Personal;

import java.util.ArrayList;

public class PersonalListFragment extends DialogFragment
{
    /*RecyclerView recyclerView;
    PersonalAdapter personalAdapter;
    ArrayList<Personal> personalList;*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*personalAdapter = new PersonalAdapter(personalList);

        recyclerView = (RecyclerView)findViewById(R.id.TvShows);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(personalAdapter);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal_list, container, false);



        return v;
    }


}
