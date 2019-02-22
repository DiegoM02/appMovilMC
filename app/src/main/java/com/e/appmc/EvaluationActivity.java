
package com.e.appmc;

import android.app.AlertDialog;
import android.database.Cursor;
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
import com.e.bd.appmc.FacilityContract;
import com.e.bd.appmc.Personal;
import com.e.bd.appmc.Point;
import com.e.bd.appmc.Question;
import com.e.bd.appmc.QuestionContract;
import com.e.bd.appmc.SQLiteOpenHelperDataBase;

import java.util.ArrayList;

public class EvaluationActivity extends AppCompatActivity implements
        FragmentFiveDimension.OnFragmentInteractionListener,
        SecurityDimensionFragment.OnFragmentInteractionListener {


    private FragmentFiveDimension fragmentoCincoDimensiones;
    private SecurityDimensionFragment fragmentoCuatroDimensiones;
    private Spinner centroActual;
    private SQLiteOpenHelperDataBase bd;
    private ArrayList<Question> questions;
    private ArrayList<Personal> personal;
    private DBMediator mediador;
    private FloatingActionButton personalButton;
    private AlertDialog listaPersonalFlotante;
    private int idUsuario;
    private int idCentroActual;
    private FacilitySpinnerAdapter adapter;
    private ArrayList<Point> points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evaluation);
        idUsuario = getIntent().getExtras().getInt("id");
        mediador = new DBMediator(this);
        personalButton = (FloatingActionButton) findViewById(R.id.personal);
        funcionalidadBotonPersonal();
        activarSpinnerCentros();
        this.questions = new ArrayList<Question>();
        this.personal = new ArrayList<Personal>();
        this.points = mediador.obtenerPuntos();
        int opcion = mediador.comprobarServicio(idUsuario);
        if (opcion == 1) {
            fragmentoCincoDimensiones = new FragmentFiveDimension();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor_dimensiones,
                    fragmentoCincoDimensiones).commit();
        } else {
            fragmentoCuatroDimensiones = new SecurityDimensionFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor_dimensiones,
                    fragmentoCuatroDimensiones).commit();
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void realizarEvaluacion(View view) {

        obtenerFragmentoActivo(view);
    }

    public void listaPersonal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.personal_container, null);

        ArrayList<Personal> personals = mediador.rellenarPersonal(idCentroActual);
        PersonalAdapter personal = new PersonalAdapter(personals, this, this);
        RecyclerView list = (RecyclerView) customView.findViewById(R.id.list);
        list.setAdapter(personal);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(this));
       /* SwipeController swipeController = new SwipeController();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(list);*/


        builder.setView(customView);
        listaPersonalFlotante = builder.create();
        listaPersonalFlotante.show();
        enableDimensiones();
    }


    public void enableDimensiones() {
        android.support.v4.app.Fragment f =
                getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);
        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.enableCardView();
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.enableCardView();
        }
    }


    public void obtenerFragmentoActivo(View view) {
        android.support.v4.app.Fragment f =
                getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {

            switch (view.getId()) {
                case R.id.car_view:
                    this.questions = mediador.llenarPreguntas(1,
                            1, this.idCentroActual);
                    fragmentoCincoDimensiones.realizarEvaluacionDimensionNormasLaborales(view,
                            this.questions,1);
                    break;
                case R.id.car_view_1:
                    this.questions = mediador.llenarPreguntas(2, 2,
                            this.idCentroActual);
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,
                            this.questions,2);
                    break;
                case R.id.car_view_2:
                    this.questions = mediador.llenarPreguntas(3, 3,
                            this.idCentroActual);
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,
                            this.questions,3);
                    break;
                case R.id.car_view_3:
                    this.questions = mediador.llenarPreguntas(4, 4,
                            this.idCentroActual);
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,
                            this.questions,4);
                    break;
                case R.id.car_view_4:
                    this.questions = mediador.llenarPreguntas(5, 5,
                            this.idCentroActual);
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,
                            this.questions,5);
            }

        } else if (f instanceof SecurityDimensionFragment) {
            switch (view.getId()) {
                case R.id.cardView:
                    this.questions = mediador.llenarPreguntas(1, 1,
                            this.idCentroActual);
                    fragmentoCuatroDimensiones.realizarEvaluacionDimensionNormasLaborales(view,
                            this.questions,1);
                    break;
                case R.id.cardView2:
                    this.questions = mediador.llenarPreguntas(2, 2,
                            this.idCentroActual);
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view,
                            this.questions,2);
                    break;
                case R.id.cardView3:
                    this.questions = mediador.llenarPreguntas(3, 3,
                            this.idCentroActual);
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view,
                            this.questions,3);
                    break;
                case R.id.cardView4:
                    this.questions = mediador.llenarPreguntas(4, 4,
                            this.idCentroActual);
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view,
                            this.questions,4);
            }
        }


    }

    public void confirmarPregunta(View view) {
        android.support.v4.app.Fragment f =
                getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.confirmarPregunta(view,this.questions);
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.confirmarPregunta(view, this.questions);
        }

    }

    public void confirmarPreguntaSiNo(View view) {
        android.support.v4.app.Fragment f =
                getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.confirmarPreguntaSiNo(view);
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.confirmarPreguntaSiNo(view,this.questions);
        }
    }


    public void cancelarEvaluacion(View view) {
        android.support.v4.app.Fragment f =
                getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);
        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.cancelarPregunta(view);
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.cancelarPregunta(view);
        }
    }


    public String[] arrayPersonal() {
        this.personal = mediador.rellenarPersonal(this.idCentroActual);
        String[] personal = new String[this.personal.size()];

        for (int i = 0; i < this.personal.size(); i++) {
            personal[i] = this.personal.get(i).getName() + " " + this.personal.get(i).getSurname();
        }

        return personal;
    }

    public void buttonClickNegative(View view) {

        android.support.v4.app.Fragment f =
                getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);
        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.noPreguntaSiNo(view, this.questions, arrayPersonal());
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.noPreguntaSiNo(view, this.questions, arrayPersonal());
        }
    }


    public void activarSpinnerCentros() {
        centroActual = (Spinner) findViewById(R.id.centroActual);
        adapter = new FacilitySpinnerAdapter(this, R.layout.spinner_facility_item,
                mediador.obtenerCentros(idUsuario));
        centroActual.setAdapter(adapter);
        centroActual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                Facility user = adapter.getItem(i);
                Toast.makeText(EvaluationActivity.this, "ID: " +
                                user.getId() + "\nName: " + user.getName(),
                        Toast.LENGTH_SHORT).show();
                idCentroActual = user.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void agregarPersonal(View view) {
        Intent EnterAdittion = new Intent(this, PersonalAdittionActivity.class);
        EnterAdittion.putExtra("idCentro", idCentroActual);
        EnterAdittion.putExtra("idUsuario", idUsuario);
        startActivityForResult(EnterAdittion, 1);


    }

    private void funcionalidadBotonPersonal() {
        this.personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaPersonal();
            }
        });
    }

    public void recargarListaPersonal() {
        listaPersonalFlotante.cancel();
        listaPersonal();
    }

    public String obtenerNombrePunto(int id)
    {
        for(Point point : this.points)
        {
            if(point.getId()==id)
            {
                return point.getName();
            }
        }

        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recargarListaPersonal();
        }
    }

}


