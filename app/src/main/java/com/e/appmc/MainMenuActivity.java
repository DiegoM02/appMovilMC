package com.e.appmc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.e.bd.appmc.User;

public class MainMenuActivity extends AppCompatActivity {
    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";
    private TextView nombreUsuario;
    private Bundle datosUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        nombreUsuario= (TextView)findViewById(R.id.userText);
        datosUsuario = getIntent().getExtras();
        nombreUsuario.setText(datosUsuario.getString("name"));
    }


    public void evaluar(View view)
    {
        Intent goToEvaluation = new Intent(MainMenuActivity.this,EvaluationActivity.class);
        goToEvaluation.putExtra("id",datosUsuario.getInt("id"));
        startActivity(goToEvaluation);
    }


    public void salir(View view)
    {
       MainActivity.changeEstadoSession(MainMenuActivity.this,false);
        Intent goToLogin = new Intent(MainMenuActivity.this,MainActivity.class);
        startActivity(goToLogin);
    }

    public void evaluaciones(View view)
    {
        Intent goToEvaluation = new Intent(MainMenuActivity.this, EvaluationActivity.class);
        startActivity(goToEvaluation);
    }

}
