
package com.e.appmc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.e.appmc.dummy.DummyContent;
import com.e.bd.appmc.SQLiteOpenHelperDataBase;

public class EvaluationActivity extends AppCompatActivity implements FragmentFiveDimension.OnFragmentInteractionListener, SecurityDimensionFragment.OnFragmentInteractionListener {

    private Button buttonElegirUbicacionCentro;
    //private FloatingActionButton buttonListaUsuarios;
    private FragmentFiveDimension fragmentoCincoDimensiones;
    private SecurityDimensionFragment fragmentoCuatroDimensiones;


    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        //buttonListaUsuarios = (Button)findViewById(R.id.fab);
        idUsuario = getIntent().getExtras().getInt("id");


        int opcion = comprobarServicio();
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

    private int comprobarServicio() {
        SQLiteOpenHelperDataBase db = new SQLiteOpenHelperDataBase(this, "mcapp", null, 1);
        String query = "SELECT service_id FROM facility WHERE user_Id = " + idUsuario;
        Cursor servicio = db.doSelectQuery(query);
        if (servicio.getCount() > 0) {
            servicio.moveToFirst();
            int serv = servicio.getInt(servicio.getColumnIndex("service_id"));
            return serv;

        }
        return 0;
    }

    public void listaPersonal(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = new PersonalListFragment();
        dialogFragment.show(ft, "dialog");
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

    public void realizarEvaluacion(View view) {
        obtenerFragmentoActivo(view);

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


}
