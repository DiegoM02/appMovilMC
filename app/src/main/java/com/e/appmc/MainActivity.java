package com.e.appmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*seteo de instancias de los objetos*/
        usuario = (EditText)findViewById(R.id.EditUsuario);
        contraseña = (EditText)findViewById(R.id.EditContraseña);
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
                    Intent intent = new Intent(this,MainMenuActivity.class);
                    //intent.putExtra(EXTRA_MESSAGE, usr);
                    startActivity(intent);
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
}
