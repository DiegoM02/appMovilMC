package com.e.appmc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bd.appmc.SQLiteOpenHelperDataBase;
import com.e.bd.appmc.User;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;
    private SQLiteOpenHelperDataBase bd;
    private CheckBox session;
    private boolean estaActivadoCheckBox;
    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";
    private static final String ESTADO_CHECK_BOX = "estado_check";
    private static final String NOMBRE_USUARIO = "nombre_usuario";
    private static final String ID_USUARIO = "id_usuario";
    private static final String BD_CREADA ="bd_creada";
    private User user;

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

        /*Creacion BD*/

            this.bd = new SQLiteOpenHelperDataBase(this, "mcapp", null, 1);
            SQLiteDatabase dataBase = this.bd.getWritableDatabase();


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

    public void guardarBdCreada()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        sesionPreferencias.edit().putBoolean(BD_CREADA,true);
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
            user = this.comprobarUsuario(usr);
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
            }

        }
    }

    private int validar(String usr,String pass,User user) {


        //Agregar despues con conexion a BD
        if(user == null)//correccion para funcionar con BD
        {
            return 1;
        }
        if(!user.getPassword().equals(pass))//similar
        {
            return 2;
        }
        return 0;
    }

    private User comprobarUsuario(String usr)
    {
        Cursor data = this.bd.doSelectQuery("SELECT * FROM user WHERE username LIKE '" +usr+"%'");
        if(data.getCount()!=0)
        {
            data.moveToFirst();
            User usuario;
            String name = data.getString(data.getColumnIndex("name"));
            String username = data.getString(data.getColumnIndex("username"));
            String password = data.getString(data.getColumnIndex("password"));
            String created = data.getString(data.getColumnIndex("created"));
            String rut = data.getString(data.getColumnIndex("rut"));
            String email = data.getString(data.getColumnIndex("email"));
            String phone   = data.getString(data.getColumnIndex("phone"));
            int id = data.getInt(data.getColumnIndex("id"));
            usuario = new User(id,name,username,password,created,rut,email,phone);
            return usuario;
        }
        return null;
    }



    private  void enterSession() {

            Intent intent = new Intent(this,MainMenuActivity.class);
            intent.putExtra("id",this.obtenerIdUsuarioRecordarSesion());
            intent.putExtra("name",this.obtenerNombreUsuarioRecordarSesion());
            startActivity(intent);
            //finish();

    }
}
