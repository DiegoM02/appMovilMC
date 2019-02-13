
package com.e.appmc;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.bd.appmc.Facility;
import com.e.bd.appmc.Personal;

import java.util.ArrayList;

public class EvaluationActivity extends AppCompatActivity implements FragmentFiveDimension.OnFragmentInteractionListener, SecurityDimensionFragment.OnFragmentInteractionListener {


    private FragmentFiveDimension fragmentoCincoDimensiones;
    private SecurityDimensionFragment fragmentoCuatroDimensiones;
    private Spinner centroActual;
    private DBMediator mediador;
    private FloatingActionButton personalButton;

    private int idUsuario;
    private int idCentroActual;
    private FacilitySpinnerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        idUsuario = getIntent().getExtras().getInt("id");
        mediador = new DBMediator(this);
        personalButton = (FloatingActionButton) findViewById(R.id.personal);
        funcionalidadBotonPersonal();
        activarSpinnerCentros();
        int opcion = mediador.comprobarServicio(idUsuario);
        if (opcion == 1) {
            fragmentoCincoDimensiones = new FragmentFiveDimension();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor_dimensiones, fragmentoCincoDimensiones).commit();
        } else {
            fragmentoCuatroDimensiones = new SecurityDimensionFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor_dimensiones, fragmentoCuatroDimensiones).commit();
        }



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void realizarEvaluacion(View view)
    {
        obtenerFragmentoActivo(view);
    }

    public void listaPersonal()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.personal_container,null);

        ArrayList<Personal> personals = mediador.rellenarPersonal(idCentroActual);
        PersonalAdapter personal = new PersonalAdapter(personals);

        RecyclerView list = (RecyclerView) customView.findViewById(R.id.list);
        list.setAdapter(personal);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(this));
       /* SwipeController swipeController = new SwipeController();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(list);*/


        builder.setView(customView);
        builder.create();
        builder.show();
        enableDimensiones();
    }


    public void enableDimensiones()
    {
        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);
        if (f instanceof FragmentFiveDimension)
        {
            fragmentoCincoDimensiones.enableCardView();
        }else if (f instanceof SecurityDimensionFragment)
        {
            fragmentoCuatroDimensiones.enableCardView();
        }
    }


    public void obtenerFragmentoActivo(View view) {
        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {

            switch (view.getId()) {
                case R.id.car_view:
                    fragmentoCincoDimensiones.realizarEvaluacionDimensionNormasLaborales(view);
                    break;
                case R.id.car_view_1:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view);
                    break;
                case R.id.car_view_2:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view);
                    break;
                case R.id.car_view_3:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view);
                    break;
                case R.id.car_view_4:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view);
            }

        } else if (f instanceof SecurityDimensionFragment) {
            switch (view.getId()) {
                case R.id.cardView:
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view);
                    break;
                case R.id.cardView2:
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view);
                    break;
                case R.id.cardView3:
                    fragmentoCuatroDimensiones.realizarEvaluacionDimensionNormasLaborales(view);
                    break;
                case R.id.cardView4:
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view);
            }
        }


    }

    public void confirmarPregunta(View view) {
        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.confirmarPregunta(view);
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.confirmarPregunta(view);
        }

    }

    public void confirmarPreguntaSiNo(View view) {
        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.confirmarPreguntaSiNo(view);
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.confirmarPreguntaSiNo(view);
        }
    }

    public void activarSpinnerCentros()
    {
        centroActual = (Spinner)findViewById(R.id.centroActual);
        adapter = new FacilitySpinnerAdapter(this,R.layout.spinner_facility_item,mediador.obtenerCentros(idUsuario));
        centroActual.setAdapter(adapter);
        centroActual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                Facility user = adapter.getItem(i);
                Toast.makeText(EvaluationActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                        Toast.LENGTH_SHORT).show();
                idCentroActual = user.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void agregarPersonal(View view)
    {
        Intent EnterAdittion = new Intent(this,PersonalAdittionActivity.class);
        EnterAdittion.putExtra("idCentro",idCentroActual);
        EnterAdittion.putExtra("idUsuario",idUsuario);
        startActivity(EnterAdittion);


    }

    private void funcionalidadBotonPersonal()
    {
        this.personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaPersonal();
            }
        });
    }

}
