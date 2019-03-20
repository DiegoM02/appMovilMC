package com.e.appmc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.e.appmc.bd.Aspect;
import com.e.appmc.bd.Facility;
import com.e.appmc.bd.FacilityContract;
import com.e.appmc.bd.Personal;
import com.e.appmc.bd.Point;
import com.e.appmc.bd.Question;
import com.e.appmc.bd.QuestionContract;
import com.e.appmc.bd.ResponseEvaluation;
import com.e.appmc.bd.ResponseQuestion;
import com.e.appmc.bd.SQLiteOpenHelperDataBase;
import com.e.appmc.bd.Summary;
import com.e.appmc.bd.User;
import com.e.appmc.bd.Visit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DBMediator {

    private SQLiteOpenHelperDataBase db;

    public DBMediator(AppCompatActivity activity) {
        this.db = new SQLiteOpenHelperDataBase(activity,"mcapp",null,1);
    }

    public DBMediator(Context activity) {
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
                if(data.getInt(data.getColumnIndex("state"))==1) {
                    int id = data.getInt(data.getColumnIndex("id"));
                    String name = data.getString(data.getColumnIndex("name"));
                    String surname = data.getString(data.getColumnIndex("surname"));
                    String rut = data.getString(data.getColumnIndex("rut"));
                    String created = data.getString(data.getColumnIndex("created"));
                    String status = data.getString(data.getColumnIndex("sync_status"));
                    personal.add(new Personal(id, name, surname, rut, "", "", idCentroActual, 1,created,status));
                }
            }while(data.moveToNext());

        }

        return personal;

    }


    public ArrayList<Question> llenarPreguntas(int idAspect, int idEvaluation, int idCentro)
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        String query = "SELECT question.id, question.description, question.aproval_porcentage, question.type, question.aspect_id, question.point_id, question.evaluation_id FROM "+ FacilityContract.FacilityEntry.TABLE_NAME
                +" , " + QuestionContract.questionEntry.TABLE_NAME+ " WHERE facility.id = "+ idCentro + " AND facility.evaluation_id = " + 1
                + " AND question.evaluation_id = "+idEvaluation+" AND question.aspect_id = "+idAspect+";";
        Cursor cursor = db.doSelectQuery(query);
        if (cursor.moveToFirst()) {
        do {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String descripcion = cursor.getString(cursor.getColumnIndex("description"));
                double aproval_porcentage = cursor.getDouble(cursor.getColumnIndex("aproval_porcentage"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                int aspect_id = cursor.getInt(cursor.getColumnIndex("aspect_id"));
                int evaluation_id = cursor.getInt(cursor.getColumnIndex("evaluation_id"));
                int point_id = cursor.getInt(cursor.getColumnIndex("point_id"));
                questions.add(new Question(id, descripcion, aproval_porcentage, type, aspect_id, point_id, evaluation_id));



        } while (cursor.moveToNext());
        }

        cursor.close();

        return questions;


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
                double latitude = data.getDouble(data.getColumnIndex("latitude"));
                double longitude = data.getDouble(data.getColumnIndex("longitude"));
                float radius = data.getFloat(data.getColumnIndex("radius"));
                int service_id = data.getInt(data.getColumnIndex("service_id"));
                int evaluation_id = data.getInt(data.getColumnIndex("evaluation_id"));
                centros[i] = new Facility(id,idUsuario,created,code,name,address,service_id,evaluation_id,"no",latitude,longitude,radius);
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

    public void insertarResumen(Summary summary)
    {
        db.inserTableSummary(db.getWritableDatabase(),summary);
    }

    public void actualizarEstadoPesonal(int idPersonal)
    {
        ContentValues cv = new ContentValues();
        cv.put("state",0);
        db.getWritableDatabase().update("personal",cv,"id = " + idPersonal,null);
    }

    public ArrayList<Point> obtenerPuntos()
    {
        ArrayList<Point> points = new ArrayList<>();
        Cursor data = db.doSelectQuery("SELECT * FROM point");
        if(data.moveToFirst())
        {
            do{
                String name = data.getString(data.getColumnIndex("name"));
                int id = data.getInt(data.getColumnIndex("id"));
                points.add(new Point(id,name));
            }while(data.moveToNext());
        }

        return points;

    }


    public String composeJSONfromSQLitePersonal(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM personal where sync_status = '"+"no"+"'";
        Cursor cursor = db.doSelectQuery(selectQuery);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idPersonal", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                map.put("namePersonal", cursor.getString(cursor.getColumnIndex("name")));
                map.put("surnamePersonal",cursor.getString(cursor.getColumnIndex("surname")));
                map.put("rutPersonal",cursor.getString(cursor.getColumnIndex("rut")));
                map.put("phonePersonal",cursor.getString(cursor.getColumnIndex("phone")));
                map.put("emailPersonal",cursor.getString(cursor.getColumnIndex("email")));
                map.put("statePersonal",cursor.getString(cursor.getColumnIndex("state")));
                map.put("dateCreated",cursor.getString(cursor.getColumnIndex("created")));
                map.put("facilityId",cursor.getString(cursor.getColumnIndex("facility_id")));


                wordList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }


    public void updateSyncStatus(String id, String status,int type){
         this.db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sync_status",status);
        switch (type)
        {
            case 1:
                db.getWritableDatabase().update("personal",values,"id = " + id,null);
                db.close();
            case 2:
                db.getWritableDatabase().update("summary",values,"id= " + id, null);
                db.close();
        }

    }

    public String composeJSONFromSQLiteSummary()
    {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM summary where sync_status = '"+"no"+"'";
        Cursor cursor = db.doSelectQuery(selectQuery);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idSummary", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                map.put("content", cursor.getString(cursor.getColumnIndex("content")));
                map.put("created",cursor.getString(cursor.getColumnIndex("created")));
                map.put("facilityId",cursor.getString(cursor.getColumnIndex("facility_id")));


                wordList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }

    public String composeJSONfromSQLitePersonalStatus()
    {
        ArrayList<HashMap<String,String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT rut,state FROM personal WHERE state = 0 ";
        Cursor cursor = db.doSelectQuery(selectQuery);
        if(cursor.moveToFirst())
        {
            do
            {
                HashMap<String,String> map = new HashMap<>();
                map.put("rutPersonal",cursor.getString(cursor.getColumnIndex("rut")));
                map.put("statePersonal",String.valueOf(cursor.getString(cursor.getColumnIndex("state"))));

                wordList.add(map);
            }while(cursor.moveToNext());
        }
        db.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }

    public String composeJSONfromSQLiteUserLogin(String user,String password)
    {
        ArrayList<HashMap<String,String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = new HashMap<>();
        map.put("user",user);
        map.put("password",password);

        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }

    public String composeJSONfromSQLiteFacility(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM facility where sync_status = '"+"no"+"'";
        Cursor cursor = db.doSelectQuery(selectQuery);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idCentro", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                map.put("userId", cursor.getString(cursor.getColumnIndex("user_id")));
                map.put("created",cursor.getString(cursor.getColumnIndex("created")));
                map.put("centroCode",cursor.getString(cursor.getColumnIndex("code")));
                map.put("centroName",cursor.getString(cursor.getColumnIndex("name")));
                map.put("centroAddress",cursor.getString(cursor.getColumnIndex("address")));



                wordList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }

    public void updateSyncStatusFacility(String id, String status){
        this.db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sync_status",status);
        db.getWritableDatabase().update("facility",values,"id = " + id,null);
        db.close();
    }


    public void insertarFacility(int id, int userId, String name, String code,
                                 String created, String address,double latitude,double longitude,float radius,int serviceId) {

        db.insertTableFacility(db.getWritableDatabase(),new Facility(id,userId,created,code,name,address,serviceId,1,"no",latitude,longitude,radius));
    }

    public void insertarAspect(int id, String name, String created, double approval_percentage) {

        db.insertTableAspect(db.getWritableDatabase(),new Aspect(id,created,name,approval_percentage));
    }

    public void insertarResponseQuestion(int id, int id_evaluation, int id_question,float valoracion) {

        db.insertTableResponseQuestion(db.getWritableDatabase(), new ResponseQuestion(id,id_evaluation,id_question,valoracion,"no"));
    }



    public ArrayList<VisitModel> obtenerVisitasPorUsuarioYCentro(int idUser , int idFacility)
    {
        ArrayList<VisitModel> visit = new ArrayList<VisitModel>();
        String selectQuery = "SELECT visit.date as visit_date, visit.enter as visit_enter, visit.exit visit_exit  FROM visit, facility where facility.id = " + idFacility + " and visit.user_id = " + idUser +
                " and visit.facility_id = " + idFacility ;
        Cursor cursor = db.doSelectQuery(selectQuery);
        if (cursor.moveToFirst()) {
            do {

                String date = cursor.getString(cursor.getColumnIndex("visit_date"));
                String enter = cursor.getString(cursor.getColumnIndex("visit_enter"));
                String exit = cursor.getString(cursor.getColumnIndex("visit_exit"));
                System.out.println("date: " + date);
                System.out.println("enter: " + enter);
                System.out.println("exit: " + exit);
                visit.add(new VisitModel(date,enter,exit));

            } while (cursor.moveToNext());


        }

        return visit;
}

    public void insertarVisit(int idUsuario,int idFacility,String horaInicio,String horaTermino,String fecha)
    {
        db.insertTableVisit(db.getWritableDatabase(),new Visit(-1,idFacility,idUsuario,fecha,horaInicio,horaTermino,""));
    }

    public void registrarVisita(int idUsuario,String IDRequest,String horaInicio,String horaTermino,String fecha)
    {
        Cursor data = db.doSelectQuery("SELECT id FROM facility WHERE name = '"+IDRequest+"' AND user_id = " + idUsuario);
        if(data.moveToFirst())
        {
            System.out.println("Encontre Faciity");
            int idFacility = data.getInt(data.getColumnIndex("id"));
            insertarVisit(idUsuario,idFacility,horaInicio,horaTermino,fecha);
        }


    }

    public int obtenerIdEvaluation(int aspectID, int quesionID)
    {
        Cursor data = db.doSelectQuery("SELECT question.evaluation_id as id_evaluation FROM aspect, question WHERE aspect.id = " + aspectID + " AND question.id = " + quesionID +
                " AND question.aspect_id = " + aspectID );
        if(data.moveToFirst())
        {
            return  data.getInt(data.getColumnIndex("id_evaluation"));
        }
        return -1;
    }
        HashMap<String,Integer> visitas = new HashMap<>();
        String nombrePosibleMas = "";
        int cantidadPosibleMas = 0;
        String nombrePosibleMenos = "";
        int cantidadPosibleMenos = 0;
        String query = "SELECT id,name FROM facility " ;
        Cursor data = db.doSelectQuery(query);
        if(data.moveToFirst())
        {
            do {
                int id = data.getInt(data.getColumnIndex("id"));
                Cursor counts = db.doSelectQuery("SELECT count(id) as number FROM visit WHERE facility_id = " + id );
                counts.moveToFirst();
                int nCounts = counts.getInt(counts.getColumnIndex("number"));
                if(nCounts>=cantidadPosibleMas)
                {
                    nombrePosibleMas = data.getString(data.getColumnIndex("name"));
                    cantidadPosibleMas = nCounts;
                }
                if(nCounts<=cantidadPosibleMas)
                {
                    nombrePosibleMenos = data.getString(data.getColumnIndex("name"));
                    cantidadPosibleMenos = nCounts;
    

                }
            }while(data.moveToNext());
        }

        visitas.put(nombrePosibleMas,cantidadPosibleMas);
        visitas.put(nombrePosibleMenos,cantidadPosibleMenos);
        return visitas;
    }

    public HashMap<String,String> visitaReciente() throws ParseException {
        HashMap<String,String> fecha = new HashMap<>();
        String query = "SELECT facility.name as name,visit.date as date FROM facility,visit WHERE facility.id = visit.facility_id ";
        Cursor data = db.doSelectQuery(query);
        String name = "no hay datos";
        Date fechaReciente = null;
        if(data.moveToFirst())
        {
            do
            {
                Date date = new SimpleDateFormat("dd-MMM-yyyy").parse(data.getString(data.getColumnIndex("date")));
                if(fechaReciente == null || date.compareTo(fechaReciente)>=0)
                {
                    fechaReciente = date;
                    name = data.getString(data.getColumnIndex("name"));
                }
            }while(data.moveToNext());
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy");
        fecha.put(name,formatDate.format(fechaReciente));
        return  fecha;
    }




    public void insertarResponseEvaluation(int id, int idEvaluation, float valoracion, int aspect, int facility_id)
    {
        db.insertTableResponseEvaluation(db.getWritableDatabase(),new ResponseEvaluation(id,idEvaluation,valoracion, aspect, "no", facility_id));
    }

    public float obtenerValoracionPromedioDimension(int idAspect, int idCentro)
    {
        String query = "SELECT avg(assessment) as valoracion_promedio FROM response_evaluation where response_evaluation.aspect_id = " + idAspect
                + " and facility_id = " + idCentro;



        Cursor cursor = db.doSelectQuery(query);

        if (cursor.moveToFirst())

        {

            float valoracion = cursor.getFloat(cursor.getColumnIndex("valoracion_promedio"));

            return valoracion;
        }
        return -1;
    }





}
