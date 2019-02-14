
package com.e.appmc;

import android.app.AlertDialog;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.e.bd.appmc.Facility;
import com.e.bd.appmc.FacilityContract;
import com.e.bd.appmc.Personal;
import com.e.bd.appmc.Question;
import com.e.bd.appmc.QuestionContract;
import com.e.bd.appmc.SQLiteOpenHelperDataBase;
import java.util.ArrayList;

public class EvaluationActivity extends AppCompatActivity implements FragmentFiveDimension.OnFragmentInteractionListener, SecurityDimensionFragment.OnFragmentInteractionListener {

    private FragmentFiveDimension fragmentoCincoDimensiones;
    private SecurityDimensionFragment fragmentoCuatroDimensiones;
    private Spinner centroActual;
    private SQLiteOpenHelperDataBase bd;
    private ArrayList<Question> questions;
    private  ArrayList<Personal> personal;
    private int idUsuario;
    private int idCentroActual;
    private FailitySpinnerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evaluation);
        idUsuario = getIntent().getExtras().getInt("id");
        bd =  new SQLiteOpenHelperDataBase(this,"mcapp",null,1);
        centroActual = (Spinner)findViewById(R.id.centroActual);
        adapter = new FailitySpinnerAdapter(this,R.layout.spinner_facility_item,this.obtnereCentros());
        centroActual.setAdapter(adapter);
        centroActual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                // Here you get the current item (a User object) that is selected by its position
                Facility user = adapter.getItem(i);
                // Here you can do the action you want to...
                Toast.makeText(EvaluationActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                        Toast.LENGTH_SHORT).show();
                idCentroActual = user.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

    public void realizarEvaluacion(View view)
    {

        obtenerFragmentoActivo(view);
    }

    private int comprobarServicio()
    {
        String query = "SELECT service_id FROM facility WHERE user_Id = " + idUsuario;
        Cursor servicio =bd.doSelectQuery(query);
        if(servicio.getCount()>0)
        {
            servicio.moveToFirst();
            int serv = servicio.getInt(servicio.getColumnIndex("service_id"));
            return serv;

        }
        return 0;
    }

    public void listaPersonal(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.personal_container,null);

        ArrayList<Personal> personals = rellenarPersonal();
        PersonalAdapter personal = new PersonalAdapter(personals);

        RecyclerView list = (RecyclerView) customView;
        list.setAdapter(personal);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(this));


        builder.setView(customView);
        builder.create();
        builder.show();
        enableDimensiones();
    }

    public Facility[] obtnereCentros()
    {
        //Adaptar para usar con GPS

        String query = "SELECT * FROM facility WHERE user_id = " + idUsuario;
        Cursor data = bd.doSelectQuery(query);
        Facility[] centros = new Facility[data.getCount()];
        if(data.moveToFirst())
        {
            int i =0;
            do{
                int id = data.getInt(data.getColumnIndex("id"));
                //int user_id= data.getInt(data.getColumnIndex("user_id"));
                String created = data.getString(data.getColumnIndex("created"));
                String code = data.getString(data.getColumnIndex("code"));
                String name = data.getString(data.getColumnIndex("name"));
                String address = data.getString(data.getColumnIndex("address"));
                int service_id = data.getInt(data.getColumnIndex("service_id"));
                int evaluation_id = data.getInt(data.getColumnIndex("evaluation_id"));
                centros[i] = new Facility(id,idUsuario,created,code,name,address,service_id,evaluation_id);
                i=i+1;
            }while(data.moveToNext());

        }

        return centros;


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


    public ArrayList<Personal> rellenarPersonal() {
       personal = new ArrayList<>();
        String query = "SELECT * FROM personal WHERE facility_id = " + idCentroActual;
        Cursor data = bd.doSelectQuery(query);
        if (data.moveToFirst()) {
            do {
                int id = data.getInt(data.getColumnIndex("id"));
                String name = data.getString(data.getColumnIndex("name"));
                String surname = data.getString(data.getColumnIndex("surname"));
                String rut = data.getString(data.getColumnIndex("rut"));
                personal.add(new Personal(id, name, surname, rut, "", "", idCentroActual));

            } while (data.moveToNext());

        }

        return personal;
    }

    public void llenarPreguntas(int idAspect, int idEvaluation)
    {

        if (this.questions != null)  this.questions.clear();

        this.questions = new ArrayList<>();
        String query = "SELECT question.id, question.description, question.aproval_porcentage, question.type, question.aspect_id, question.point_id, question.evaluation_id FROM "+ FacilityContract.FacilityEntry.TABLE_NAME
                +" , "
                + QuestionContract.questionEntry.TABLE_NAME+ " WHERE facility.id = "+ this.idCentroActual + " AND facility.evaluation_id = " + 1
                + " AND question.evaluation_id = "+idEvaluation+" AND question.aspect_id = "+idAspect+";";
        Cursor cursor = bd.doSelectQuery(query);

        if (cursor.moveToFirst())
        do {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String descripcion = cursor.getString(cursor.getColumnIndex("description"));
            double aproval_porcentage = cursor.getDouble(cursor.getColumnIndex("aproval_porcentage"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            int aspect_id = cursor.getInt(cursor.getColumnIndex("aspect_id"));
            int point_id = cursor.getInt(cursor.getColumnIndex("point_id"));
            int evaluation_id = cursor.getInt(cursor.getColumnIndex("evaluation_id"));
            this.questions.add(new Question(id,descripcion,aproval_porcentage,type,aspect_id,point_id,evaluation_id));


        } while (cursor.moveToNext());

        cursor.close();


    }

    public void obtenerFragmentoActivo(View view) {
        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {

            switch (view.getId()) {
                case R.id.car_view:
                    fragmentoCincoDimensiones.realizarEvaluacionDimensionNormasLaborales(view,this.questions);
                    break;
                case R.id.car_view_1:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,this.questions);
                    break;
                case R.id.car_view_2:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,this.questions);
                    break;
                case R.id.car_view_3:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,this.questions);
                    break;
                case R.id.car_view_4:
                    fragmentoCincoDimensiones.realizarEvaluacionOtrasDimensiones(view,this.questions);
            }

        } else if (f instanceof SecurityDimensionFragment) {
            switch (view.getId()) {
                case R.id.cardView:
                    llenarPreguntas(1,1);
                    fragmentoCuatroDimensiones.realizarEvaluacionDimensionNormasLaborales(view,this.questions);
                    break;
                case R.id.cardView2:
                    llenarPreguntas(2,2);
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view, this.questions);
                    break;
                case R.id.cardView3:
                    llenarPreguntas(3,3);
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view,this.questions);
                    break;
                case R.id.cardView4:
                    llenarPreguntas(4,4);
                    fragmentoCuatroDimensiones.realizarEvaluacionOtrasDimensiones(view, this.questions);
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

    public void cancelarEvaluacion(View view)
    {
        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);

        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.cancelarPregunta(view);
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.cancelarPregunta(view);
        }
    }


    public String [] arrayPersonal()
    {
        rellenarPersonal();
        String [] personal = new String[this.personal.size()];

        for (int i = 0 ; i < this.personal.size(); i++)
        {
            personal[i] = this.personal.get(i).getName()+" "+this.personal.get(i).getSurname();
        }

        return personal;
    }

    public void buttonClickNegative(View view)
    {

        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.contenedor_dimensiones);
        if (f instanceof FragmentFiveDimension) {
            fragmentoCincoDimensiones.noPreguntaSiNo(view);
        } else if (f instanceof SecurityDimensionFragment) {
            fragmentoCuatroDimensiones.noPreguntaSiNo(view, this.questions , arrayPersonal());
        }
    }


}
