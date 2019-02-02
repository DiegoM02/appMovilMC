package com.e.appmc;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class EvaluationActivity extends AppCompatActivity implements FragmentFiveDimension.OnFragmentInteractionListener {

    private Button buttonElegirUbicacionCentro;
    private FragmentFiveDimension fragmentoCincoDimensiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        fragmentoCincoDimensiones = new FragmentFiveDimension();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor_dimensiones,fragmentoCincoDimensiones).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
