package com.e.appmc;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.e.appmc.bd.User;
import com.e.appmc.service.GPSService;
import com.e.appmc.sync.SyncDatabase;

import java.util.HashMap;

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
    private SyncDatabase sincronizador;
    private ServiceConnection m_serviceConnection;
    private GPSService m_service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText)findViewById(R.id.EditUsuario);
        usuario.setText("ABass");
        contraseña = (EditText)findViewById(R.id.EditContraseña);
        contraseña.setText("matanui2009");
        session = (CheckBox) findViewById(R.id.checkbox_session);
        this.mediador = new DBMediator(this);
        sincronizador = new SyncDatabase(this);
        estaActivadoCheckBox = session.isChecked();
        if (obtenerEstadoRecordarSession()) enterSession();
        m_serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                m_service = ((GPSService.MyBinder)iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                m_service = null;
            }
        };

       // bindService(intent, m_serviceConnection, BIND_AUTO_CREATE);



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

    public void guardarDatosUsuario(String name,int id)
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        sesionPreferencias.edit().putString(NOMBRE_USUARIO,name).apply();
        sesionPreferencias.edit().putInt(ID_USUARIO,id).apply();
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
            sincronizador.loginSQLite(usr,pass);

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


    public void betweenSession(String name, int id,int role)
    {
        switch(name)
        {
            case "NoUser ":
                usuario.setError("Usuario Incorrecto");
                break;
            case "NoPass ":
                contraseña.setError("Contraseña Incorrecta");
                break;
            default:
                roleChecker(name,id,role);
                break;

        }

    }

    @SuppressLint("NewApi")
    private void roleChecker(String name, int id, int role)
    {
        if(role==5)
        {
            this.guardarDatosUsuario(name,id);
            this.guadarEstadoRecordarSesion();
            Intent intent = new Intent(this, GPSService.class);
            intent.putExtra("name",this.obtenerNombreUsuarioRecordarSesion());
            intent.putExtra("id",this.obtenerIdUsuarioRecordarSesion());
            startService(intent);
            //bindService(intent, m_serviceConnection, BIND_AUTO_CREATE);

            enterSession();
        }
        else
        {
            usuario.setError("Usuario No valido");
        }
    }

    private  void enterSession() {


        this.sincronizador.syncAspectWebsite();
        this.sincronizador.syncFacilityWebsite();
        Intent intent = new Intent(this,MainMenuActivity.class);
        intent.putExtra("id",this.obtenerIdUsuarioRecordarSesion());
        intent.putExtra("name",this.obtenerNombreUsuarioRecordarSesion());
        startActivity(intent);
        finish();

    }
}
