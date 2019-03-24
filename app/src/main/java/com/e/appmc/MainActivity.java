package com.e.appmc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.e.appmc.bd.User;
import com.e.appmc.service.GPSService;
import com.e.appmc.sync.SyncDatabase;

import java.util.HashMap;




/******************************************
 * Actividad encargada de administrar la interfaz de logeo
 * y sus funcionalidades.
 * */

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
    private static final String HORA_INICIO="hora_inicio";
    private User user;
    private DBMediator mediador;
    private SyncDatabase sincronizador;
    private ServiceConnection m_serviceConnection;
    private GPSService m_service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},111);
        usuario = (EditText)findViewById(R.id.EditUsuario);
        contraseña = (EditText)findViewById(R.id.EditContraseña);
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



    /*
    * Metodo que permite cambiar el estado de mantencion de sesion
    * seteando en true o false el estado del check de recordar sesion.
    * Recibe como parametro el contexto de la aplicacion y el valor booleano del
    * estado del recordar sesion.
    * */
    public static void changeEstadoSession(Context c , boolean estadoSession)
    {
        SharedPreferences sessionPreferences = c.getSharedPreferences(SESSION_ESTADO_RECORDAR,MODE_PRIVATE);
        sessionPreferences.edit().putBoolean(ESTADO_CHECK_BOX,estadoSession).apply();
    }


    /*
    * Metodo encargado de gatillar evento cuando se selecciona
    * el checkbox de recordar sesion, activando o desactivando el check
    * segun corresponda.
    *
    * Recibe como parametro la vista en donde se genera el evento.
    * */
    public void activarGuardarSesion(View view)
    {
        if (estaActivadoCheckBox)
        {
            session.setChecked(false);
        }

        estaActivadoCheckBox = session.isChecked();
    }


    /*
    * Metodo encargado de guardar el estado de la sesion dentro
    * del cache SheredPreferences, guardando el
    * estado del checkbox de recordar sesion.
    *
    * */
    public  void guadarEstadoRecordarSesion()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        sesionPreferencias.edit().putBoolean(ESTADO_CHECK_BOX,session.isChecked()).apply();

    }


    /*
     * Metodo encargado de guardar los datos relevantes del usuario dentro
     * del cache SheredPreferences, guardando el id del usuario y su nomabre.
     *
     * */
    public void guardarDatosUsuario(String name,int id)
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        sesionPreferencias.edit().putString(NOMBRE_USUARIO,name).apply();
        sesionPreferencias.edit().putInt(ID_USUARIO,id).apply();
    }

    public void guardarHoraInicio(String hora)
    {
        SharedPreferences horaPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        horaPreferencias.edit().putString(HORA_INICIO,hora).apply();
    }

    public String obtenerHoraInicioRecordarSession()
    {
        SharedPreferences horaPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        return horaPreferencias.getString(HORA_INICIO,"no aplica");
    }



    /*
    * Metodo encargado de obtener el estado actual de la sesion desde el cache
    * SharedPreferences.
    *
    * */

    public boolean obtenerEstadoRecordarSession()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getBoolean(ESTADO_CHECK_BOX,false);
    }


    /*
     * Metodo encargado de obtener el nombre del usuario logeado actualmente desde el cache
     * SharedPreferences.
     *
     * */
    public String obtenerNombreUsuarioRecordarSesion()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getString(NOMBRE_USUARIO,"no aplica");
    }


    /*
     * Metodo encargado de obtener el id del usuario logeado actualmente desde el cache
     * SharedPreferences.
     *
     * */
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


    /*Metodo que es gatillado cuendo se inicia el evento onclick del boton de logeo
    * validando los campos necesarios
    *
    * Recibe como parametro la vista en donde se gatilla el evento onclic*/
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




    /*Metodo encargado de validar que los datos ingresados existan y sean correctos.
    Ademas que tengan autorizacion para el ingreso a la aplicacion.
    *
    * Recibe como paramentro el nombre del usuario , su ida y su rol.
    *
    * */
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


    /*
    * Metodo encargado de verificar el rol permitido dentro de la aplicacion,
    * en esta caso aceptando solo el rol de supervisor identificado con el id 5.
    * Recibe como parametro el nombre del usuario , id y su rol.
    * */
    @SuppressLint("NewApi")
    private void roleChecker(String name, int id, int role)
    {
        if(role==5)
        {
            this.guardarDatosUsuario(name,id);
            this.guadarEstadoRecordarSesion();
            Singleton.getInstance().setID(this.obtenerIdUsuarioRecordarSesion());
            //Intent intent = new Intent(this, GPSService.class);
            //intent.putExtra("name",this.obtenerNombreUsuarioRecordarSesion());
            //intent.putExtra("id",this.obtenerIdUsuarioRecordarSesion());
            //startService(intent);
            //bindService(intent, m_serviceConnection, BIND_AUTO_CREATE);

            enterSession();
        }
        else
        {
            usuario.setError("Usuario No valido");
        }
    }



    /*
    * Metodo encargado de hacer la transicion de la actividad de logeo
    * a la actividad de menu principal, una vez validado y verificado que los
    * datos ingresados son correctos.
    * */
    private  void enterSession() {

        this.sincronizador.syncAspectWebsite();
        this.sincronizador.syncFacilityWebsite();
        this.guardarHoraInicio("no hay hora");
        Intent intent = new Intent(this,MainMenuActivity.class);
        intent.putExtra("id",this.obtenerIdUsuarioRecordarSesion());
        intent.putExtra("name",this.obtenerNombreUsuarioRecordarSesion());
        startActivity(intent);
        finish();

    }
}
