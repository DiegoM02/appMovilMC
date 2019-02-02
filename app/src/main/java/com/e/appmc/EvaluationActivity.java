package com.e.appmc;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EvaluationActivity extends AppCompatActivity implements SecurityDimensionFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SecurityDimensionFragment newFragment = new SecurityDimensionFragment();
        transaction.replace(R.id.container, newFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
