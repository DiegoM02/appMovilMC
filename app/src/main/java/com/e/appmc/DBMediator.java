package com.e.appmc;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;

import com.e.bd.appmc.Facility;
import com.e.bd.appmc.Personal;
import com.e.bd.appmc.SQLiteOpenHelperDataBase;
import com.e.bd.appmc.User;

import java.util.ArrayList;

public final class DBMediator {

    private SQLiteOpenHelperDataBase db;

    public DBMediator(AppCompatActivity activity) {
        this.db = new SQLiteOpenHelperDataBase(activity,"mcapp",null,1);
    }

    public ArrayList<Personal> rellenarPersonal(int idCentroActual)
    {
        ArrayList<Personal> personal = new ArrayList<>();
        String query = "SELECT * FROM personal WHERE facility_id = " + idCentroActual;
        Cursor data=db.doSelectQuery(query);
        if(data.moveToFirst())
        {
            do{
                int id= data.getInt(data.getColumnIndex("id"));
                String name =data.getString(data.getColumnIndex("name"));
                String surname = data.getString(data.getColumnIndex("surname"));
                String rut = data.getString(data.getColumnIndex("rut"));
                personal.add(new Personal(id,name,surname,rut,"","",idCentroActual,1));

            }while(data.moveToNext());

        }

        return personal;

    }

    public Facility[] obtenerCentros(int idUsuario)
    {
        String query = "SELECT * FROM facility WHERE user_id = " + idUsuario;
        Cursor data = db.doSelectQuery(query);
        Facility[] centros = new Facility[data.getCount()];
        if(data.moveToFirst())
        {
            int i =0;
            do{
                int id = data.getInt(data.getColumnIndex("id"));
                //int user_id= data.getInt(data.getColumnIndex("user_id"));
                String created = data.getString(data.getColumnIndex("created"));
                String code = data.getString(data.getColumnIndex("code"));
                String name = data.getString(data.getColumnIndex("name"));
                String address = data.getString(data.getColumnIndex("address"));
                int service_id = data.getInt(data.getColumnIndex("service_id"));
                centros[i] = new Facility(id,idUsuario,created,code,name,address,service_id);
                i=i+1;
            }while(data.moveToNext());

        }

        return centros;


    }

    public User comprobarUsuario(String usr)
    {
        Cursor data = this.db.doSelectQuery("SELECT * FROM user WHERE username LIKE '" +usr+"%'");
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
            int role = data.getInt(data.getColumnIndex("role"));
            usuario = new User(id,name,username,password,created,rut,email,phone,role);
            return usuario;
        }
        return null;
    }

    public int comprobarServicio(int idUsuario)
    {
        String query = "SELECT service_id FROM facility WHERE user_Id = " + idUsuario;
        Cursor servicio =db.doSelectQuery(query);
        if(servicio.getCount()>0)
        {
            servicio.moveToFirst();
            int serv = servicio.getInt(servicio.getColumnIndex("service_id"));
            return serv;

        }
        return 0;
    }

    public void insertarPersonal(Personal personal)
    {
        db.insertTablePersonal(db.getWritableDatabase(),personal);
    }

    public void actualizarEstadoPesonal(int idPersonal)
    {

    }


}
