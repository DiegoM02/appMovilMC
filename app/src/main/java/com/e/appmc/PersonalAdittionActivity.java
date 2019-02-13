package com.e.appmc;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.e.bd.appmc.Personal;

public class PersonalAdittionActivity extends AppCompatActivity {
    private EditText dateText;
    private EditText nameText;
    private EditText surnameText;
    private EditText rutText;
    private DBMediator dbMediator;
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
        if(dateText.getText().toString().isEmpty())
        {
            dateText.setError("Campo necesario");
            flag=1;
        }

        if (flag==0)
        {
            Personal personal = new Personal(-1,nameText.getText().toString(),surnameText.getText().toString(),rutText.getText().toString(),"","",idCentro,1);
            dbMediator.insertarPersonal(personal);

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
}
