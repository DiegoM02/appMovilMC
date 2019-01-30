package com.e.appmc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;
    private CheckBox session;
    private boolean estaActivadoCheckBox;
    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";
    private static final String ESTADO_CHECK_BOX = "estado_check";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*seteo de instancias de los objetos*/
        usuario = (EditText)findViewById(R.id.EditUsuario);
        contraseña = (EditText)findViewById(R.id.EditContraseña);
        session = (CheckBox) findViewById(R.id.checkbox_session);

        estaActivadoCheckBox = session.isChecked();

        if (obtenerEstadoRecordarSession()) enterSession();

    }


    public void activarGuardarSesion(View view)
    {
        if (estaActivadoCheckBox)
        {
            session.setChecked(false);
        }

        estaActivadoCheckBox = session.isChecked();
    }


    public  void guadarEstadoRecordarSesion()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        sesionPreferencias.edit().putBoolean(ESTADO_CHECK_BOX,session.isChecked()).apply();
    }

    public boolean obtenerEstadoRecordarSession()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getBoolean(ESTADO_CHECK_BOX,false);
    }

    public void logear(View view)
    {
        String usr = usuario.getText().toString();
        String pass = contraseña.getText().toString();
        if(usr.isEmpty())
        {
            usuario.setError("Campo necesario");
        }
        if(pass.isEmpty())
        {
            contraseña.setError("Campo necesario");
        }
        if(!usr.isEmpty() && !pass.isEmpty())
        {
            int opcion = this.validar(usr,pass);
            switch(opcion){
                case 0:
                        enterSession();
                    break;
                case 1:
                    usuario.setError("Usuario Incorrecto");
                    break;
                case 2:
                    contraseña.setError("Contraseña Incorrecta");
                    break;
            }

        }
    }

    private int validar(String usr,String pass) {
        String testUsr = "ariel";
        String testKey = "12345678";
        //Agregar despues con conexion a BD
        if(!usr.equals(testUsr))//correccion para funcionar con BD
        {
            return 1;
        }
        if(!pass.equals(testKey))//similar
        {
            return 2;
        }
        return 0;
    }




    private  void enterSession() {
            guadarEstadoRecordarSesion();
            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();

    }
}
