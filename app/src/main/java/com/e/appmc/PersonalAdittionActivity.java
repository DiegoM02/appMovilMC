package com.e.appmc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.e.appmc.bd.Personal;
import com.e.appmc.sync.SyncDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalAdittionActivity extends AppCompatActivity {
    private EditText dateText;
    private EditText nameText;
    private EditText surnameText;
    private EditText rutText;
    private DBMediator dbMediator;
    private SyncDatabase sincroniza;
    private int idCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_adittion);
        dateText = (EditText)findViewById(R.id.dateText);
        nameText = (EditText)findViewById(R.id.name);
        surnameText = (EditText)findViewById(R.id.surname);
        rutText = (EditText)findViewById(R.id.rut);
        dbMediator = new DBMediator(this);
        sincroniza = new SyncDatabase(this);
        idCentro = getIntent().getExtras().getInt("idCentro");

    }


    public void agregarFechaContrato (View view)
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " - " + (month+1) + " - " + year;
                dateText.setText(selectedDate);
            }
        });

        newFragment.show(this.getFragmentManager(),"DatePicker");
    }

    public void añadirPersonal(View view)
    {
        int flag =0;
        if(nameText.getText().toString().isEmpty())
        {
            nameText.setError("Campo necesario");
            flag=1;
        }
        if(surnameText.getText().toString().isEmpty())
        {
            surnameText.setError("Campo necesario");
            flag=1;
        }
        if(rutText.getText().toString().isEmpty())
        {
            rutText.setError("Campo necesario");
            flag=1;
        }
        if(!rutText.getText().toString().isEmpty() && validarRut(rutText.getText().toString()))
        {
            rutText.setError("Formato incorrecto");
            flag=1;
        }
        if(dateText.getText().toString().isEmpty())
        {
            dateText.setError("Campo necesario");
            flag=1;
        }
        if(!nameText.getText().toString().isEmpty() && validarTexto(nameText.getText().toString()))
        {
            nameText.setError("Formato incorrecto");
            flag=1;
        }
        if(!surnameText.getText().toString().isEmpty() && validarTexto(surnameText.getText().toString()))
        {
            surnameText.setError("Formato incorrecto");
            flag=1;
        }

        if (flag==0)
        {
            Personal personal = new Personal(-1,nameText.getText().toString(),surnameText.getText().toString(),rutText.getText().toString(),"","",idCentro,1,
                    dateText.getText().toString(),"no");
            dbMediator.insertarPersonal(personal);
            Intent intent=new Intent();
            setResult(2,intent);
            this.finish();
        }
        else
        {
            return;
        }

    }

    public void cancelar(View view)
    {
        this.finish();
    }

    public boolean validarRut(String rut)
    {
        Pattern pattern = Pattern.compile("^0*(\\d{1,3}(\\.?\\d{3})*)\\-?([\\dkK])$");
        Matcher matcher = pattern.matcher(rut);
        if(matcher.matches())
        {
            return false;
        }


        return true;
    }

    public boolean validarTexto(String texto )
    {
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(texto);
        if(matcher.matches())
        {
            return false;
        }
        return true;
    }
}
