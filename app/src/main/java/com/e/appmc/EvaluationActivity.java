<<<<<<< HEAD
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
=======
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
>>>>>>> InterfazEvaluacionFragmentSeguridad
