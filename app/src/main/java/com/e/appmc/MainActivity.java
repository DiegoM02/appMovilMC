package com.e.appmc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.e.appmc.bd.User;
import com.e.appmc.sync.SyncDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;
    private CheckBox session;
    private boolean estaActivadoCheckBox;
    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";
    private static final String ESTADO_CHECK_BOX = "estado_check";
    private static final String NOMBRE_USUARIO = "nombre_usuario";
    private static final String ID_USUARIO = "id_usuario";
    private static final String BD_CREADA ="bd_creada";
    private User user;
    private DBMediator mediador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText)findViewById(R.id.EditUsuario);
        contraseña = (EditText)findViewById(R.id.EditContraseña);
        session = (CheckBox) findViewById(R.id.checkbox_session);
        this.mediador = new DBMediator(this);
        estaActivadoCheckBox = session.isChecked();
        if (obtenerEstadoRecordarSession()) enterSession();



    }


    public static void changeEstadoSession(Context c , boolean estadoSession)
    {
        SharedPreferences sessionPreferences = c.getSharedPreferences(SESSION_ESTADO_RECORDAR,MODE_PRIVATE);
        sessionPreferences.edit().putBoolean(ESTADO_CHECK_BOX,estadoSession).apply();
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

    public void guardarDatosUsuario()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        sesionPreferencias.edit().putString(NOMBRE_USUARIO,user.getName()).apply();
        sesionPreferencias.edit().putInt(ID_USUARIO,user.getId()).apply();
    }



    public boolean obtenerEstadoRecordarSession()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getBoolean(ESTADO_CHECK_BOX,false);
    }

    public String obtenerNombreUsuarioRecordarSesion()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getString(NOMBRE_USUARIO,"no aplica");
    }

    public int obtenerIdUsuarioRecordarSesion()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getInt(ID_USUARIO,0);
    }

    public boolean obtenerBdCreada()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getBoolean(BD_CREADA,false);
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
            user = mediador.comprobarUsuario(usr);
            int opcion = this.validar(usr,pass,user);
            switch(opcion){
                case 0:
                    this.guardarDatosUsuario();
                    this.guadarEstadoRecordarSesion();
                    enterSession();
                    break;
                case 1:
                    usuario.setError("Usuario Incorrecto");
                    break;
                case 2:
                    contraseña.setError("Contraseña Incorrecta");
                    break;
                case 3:
                    usuario.setError("Usuario no valido");
                    break;
            }

        }
    }

    private int validar(String usr,String pass,User user) {


        if(user == null)
        {
            return 1;
        }
        if(!user.getPassword().equals(pass))//similar
        {
            return 2;
        }
        if(user.getRole()!=5)
        {
            return 3;
        }
        return 0;
    }


    private  void enterSession() {


            Intent intent = new Intent(this,MainMenuActivity.class);
            intent.putExtra("id",this.obtenerIdUsuarioRecordarSesion());
            intent.putExtra("name",this.obtenerNombreUsuarioRecordarSesion());
            startActivity(intent);
            finish();

    }
}
