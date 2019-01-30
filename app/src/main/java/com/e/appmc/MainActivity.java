package com.e.appmc;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.e.bd.appmc.SQLiteOpenHelperDataBase;
import com.e.bd.appmc.User;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;
    private SQLiteOpenHelperDataBase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*seteo de instancias de los objetos*/
        usuario = (EditText)findViewById(R.id.EditUsuario);
        contraseña = (EditText)findViewById(R.id.EditContraseña);
        /*Creacion BD*/
        this.bd = new SQLiteOpenHelperDataBase(this, "mcapp", null, 1);
        bd.createDataUser();
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
            User user = this.comprobarUsuario(usr);
            int opcion = this.validar(usr,pass,user);
            switch(opcion){
                case 0:
                    Intent intent = new Intent(this,MainMenuActivity.class);
                    intent.putExtra("id",user.getId());
                    intent.putExtra("name",user.getName());
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


}
