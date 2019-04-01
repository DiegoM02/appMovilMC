package com.e.appmc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.e.appmc.bd.Aspect;
import com.e.appmc.bd.Evaluation;
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
    /*
     * Metodo enccargado de devolver el personal asociado a un determinado centro, haciendo
     * uso de los registros guardados en la base de datos interna del dispositivo.
     * Recibe como parametro un entero que contiene el id del centro actualmente seleccionado.
     * Retorna un ArrayList de objetos Personal con todos los resultados de la consulta.
     */
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

    /*
     * Metodo encargado de devolver todos los registros de preguntas en la base de dato, dependiendo
     * de la dimension, la evluacion y el centro.
     * Recibe como parameto un entero con el id de la dimension, otro con el de la evaluacion y otro
     * con el id del centro.
     * Retorna un ArrayList de Question con todos los datos solicitados.
     */
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


    /*
     * Metodo encargado de obtnener los centros de los registros de la base de datos dependiendo del
     * usuario que este actualmente logeado.
     * Recibe como parametro un entero con el id del usuario en cuestion.
     * Retorna un arreglo de objetos Facility con los datos obtenidos.
     */
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
    /*
     * Metodo originalmente usado para combrobar la presencia de un  usuario en la base de datos
     * interna del dispostivo, se dejo de utilizar debido a que esto se hace actualmente con la base
     * de datos remota.
     * Recibe como parametro un String con el nombre unico del usuario.
     * Retorna un objeto de tipo User con los datos encontrados y null si no se encontro registro.
     */
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
    /*
     * Metodo encargado de identificar en los registros de la base de datos cual es el servicio
     * correspondiente para el usuario actual en la aplicacion.
     * Recibe como parametro un entero con el id del usuario actualemente en la aplicacion.
     * Retorna un entero con el id del servicio y cero si no se encuentra registro.
     */
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
    /*
     * Metodo encargado de modificar la columna estado en un registro de personal determinado, esto
     * se realiza para deshabilitar dicho registro.
     * Recibe como parametro un entero con el id del personal que sera afectado.
     */
    public void actualizarEstadoPesonal(int idPersonal)
    {
        ContentValues cv = new ContentValues();
        cv.put("state",0);
        db.getWritableDatabase().update("personal",cv,"id = " + idPersonal,null);
    }
    /*
     * Metodo encargado de retornar todas las entradas de la tabla point existentes en la base de
     * datos interna del dispositivo.
     * Retorna un ArrayList de objetos de clase point con los datos encotrados en los registros.
     */
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

    /*
     * Metodo encargado de formar el JSON que sera enviado hacia los php de la pagina web, obtiene
     * los registros de personal que aun no han sido sincornizados con dicha base de datos remota.
     * Retorna un String con el formato del JSON en cuestion.
     */
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

    /*
     * Metodo encargado de acttulizar el estado de los registros que ya fueron sincronizados con la
     * base de datos remota pertnciente a la plataforma web.
     * Recibe como parametro un String con el id del registo que se vera afectado, un string con el
     * status que le sera asignado y un entero con el tipo de registro que se vera afectado, 1 para
     * personal y 2 para summary.
     */
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
            case 3:
                db.getWritableDatabase().update("response_evaluation",values,"id= " + id, null);
                db.close();
            case 4:
                db.getWritableDatabase().update("response_question",values,"id= " + id, null);
                db.close();
            case 3:
                db.getWritableDatabase().update("visit",values,"id = " + id,null);
                db.close();
        }

    }
    /*
     * Metodo encargado de formar el JSON que sera enviado hacia los php de la pagina web, obtiene
     * los registros de summary que aun no han sido sincronizados con dicha base de datos remota.
     * Retorna un String con el formato del JSON en cuestion.
     */
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
    /*
     * Metodo encargado de formar el JSON que sera enviado hacia los php de la pagina web, obtiene
     * los registros de personal que posean un status equivalente a 0 que aun no han sido
     * sincornizados con dicha base de datos remota.
     * Retorna un String con el formato del JSON en cuestion.
     */
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
    /*
     * Metodo encargado de formar el JSON que sera enviado hacia los php de la pagina web, obtiene
     * los datos de logeo de usuario que seran comprobados en la base de datos remota.
     * Retorna un String con el formato del JSON en cuestion.
     */
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
    /*
     * Metodo encargado de formar el JSON que sera enviado hacia los php de la pagina web, obtiene
     * los registros de facility que aun no han sido sincronizados con dicha base de datos remota.
     * Retorna un String con el formato del JSON en cuestion.
     */
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


    public String composeJSONfromSQLiteResponseEvaluation(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM response_evaluation where sync_status = '"+"no"+"'";
        Cursor cursor = db.doSelectQuery(selectQuery);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idResponse", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                map.put("IdEvaluation", cursor.getString(cursor.getColumnIndex("id_evaluation")));
                map.put("assessment",cursor.getString(cursor.getColumnIndex("assessment")));
                map.put("IdFacility", cursor.getString(cursor.getColumnIndex("fscility_id")));
                map.put("IdAspect", cursor.getString(cursor.getColumnIndex("aspect_id")));



                wordList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }


    public String composeJSONfromSQLiteResponseQuestion(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM response_question where sync_status = '"+"no"+"'";
        Cursor cursor = db.doSelectQuery(selectQuery);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idResponse", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                map.put("IdEvaluation", cursor.getString(cursor.getColumnIndex("id_evaluation")));
                map.put("IdQuestion", cursor.getString(cursor.getColumnIndex("id_question")));
                map.put("assessment",cursor.getString(cursor.getColumnIndex("assessment")));



                wordList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }

    public String composeJSONfromSQLiteVisit()
    {
        ArrayList<HashMap<String,String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT user_id,facility_id,date,enter,exit,comment FROM visit WHERE state = 0 ";
        Cursor cursor = db.doSelectQuery(selectQuery);
        if(cursor.moveToFirst())
        {
            do
            {
                HashMap<String,String> map = new HashMap<>();
                map.put("idUser",cursor.getString(cursor.getColumnIndex("user_id")));
                map.put("idFacility",String.valueOf(cursor.getString(cursor.getColumnIndex("facility_id"))));
                map.put("dateVisit",String.valueOf(cursor.getString(cursor.getColumnIndex("date"))));
                map.put("enterVisit",String.valueOf(cursor.getString(cursor.getColumnIndex("enter"))));
                map.put("exitVisit",String.valueOf(cursor.getString(cursor.getColumnIndex("exit"))));
                map.put("commentVisit",String.valueOf(cursor.getString(cursor.getColumnIndex("comment"))));

                wordList.add(map);
            }while(cursor.moveToNext());
        }
        db.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }
    /*
     * Metodo encargado de acttulizar el estado de los registros de facility que ya fueron
     * sincronizados con la base de datos remota pertnciente a la plataforma web.
     * Recibe como parametro un String con el id del registo que se vera afectado y un string con el
     * status que le sera asignado y un entero con el tipo de registro que se vera afectado.
     */
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

    public void insertarResponseQuestion( int id_evaluation, int id_question,float valoracion) {

        db.insertTableResponseQuestion(db.getWritableDatabase(), new ResponseQuestion(id_evaluation,id_question,valoracion,"no"));
    }

    public void insertarEvaluation(int id, String done, int facilityId)
    {
        db.insertarEvaluation(db.getWritableDatabase(),new Evaluation(id,done,facilityId));
    }


    /*
     * Metodo encargado de encontrar los registros de las visitas filtrando por el usuario y el centro
     * en cuestion que haya sido visitado.
     * Recibe como parametro un entero con el id del usuario y un entero con el centro seleccionado.
     * Retorna un ArrayList de objetos VisitModel con los datos encontrados en los registros.
     */
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
        db.insertTableVisit(db.getWritableDatabase(),new Visit(-1,idFacility,idUsuario,fecha,horaInicio,horaTermino,"","0"));
    }
    /*
     * Metodo encargado de registrar una visita que se haya realizado en las base de datos interna
     * del dipositivo.
     * Recibe como paramentro un entero con el id del usuario que la realizo, un String con el id
     * del centro donde se realizo, un String con la hora de inicio de la visita, un String con  la
     * hora de termino y un String con la fecha de realizacion.
     */
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
    /*
     * Metodo encargado de encontrar el id de un registro de evaluacion en la base de datos interna
     * del dispositivo, utilizando como filtro el id de la dimension y la pregunta en cuestion.
     * Recibe como parameto un entero con el id de la dimension y otro con el id de la pregunta.
     * Retorna un entero con el id del registro de evaluacion encontrado.
     */
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
    /*
     * Metodo encargado de retornar los registros de los centros con mas y menos visitas.
     * Retorna un HashMap con clave String, que corresponde al nombre del ceentro en cuestion y valor
     * Integer que corresponde a la cantidad de visitas realizadas
     */
    public  HashMap<String,Integer> obtenerCentroVisitas()
    {
        HashMap<String,Integer> visitas = new HashMap<>();
        String nombrePosibleMas = "No hay datos";
        int cantidadPosibleMas = 0;
        String nombrePosibleMenos = "No hay datos";
        int cantidadPosibleMenos = 0;
        int flag =0;
        String query = "SELECT count(facility.id) as number, facility.name as name FROM facility,visit " +
                "WHERE facility.id = visit.facility_id GROUP BY facility.name ORDER BY count(facility.id) desc" ;
        Cursor data = db.doSelectQuery(query);
        if(data.moveToFirst())
        {
            flag=1;
            nombrePosibleMas = data.getString(data.getColumnIndex("name"));
            cantidadPosibleMas = data.getInt(data.getColumnIndex("number"));
            if(data.moveToLast())
            {
                nombrePosibleMenos = data.getString(data.getColumnIndex("name"));
                cantidadPosibleMenos = data.getInt(data.getColumnIndex("number"));
            }
            else
            {
                nombrePosibleMenos = nombrePosibleMas;
                cantidadPosibleMenos = cantidadPosibleMas;
            }
            String cero = this.comprobarCero();
            if(cero.isEmpty())
            {
                visitas.put(nombrePosibleMenos,cantidadPosibleMenos);
            }
            else
            {
                visitas.put(cero,0);
            }
            visitas.put(nombrePosibleMas,cantidadPosibleMas);
        }
        if(flag==0)
        {
            visitas.put(nombrePosibleMas,0);
        }

        return visitas;
    }
    /*
     * Metodo encargado de obtner el registro de algun centro que posea cero visitas si es que existe,
     * es llamado por el metodo obtnerCentroVisitas.
     * Retorna un String con el nombre del centro en cuestion y vacio en caso de no encontrar
     * registros.
     */
    private  String comprobarCero()
    {

        String query = "SELECT id,name FROM facility";
        Cursor data = db.doSelectQuery(query);
        if(data.moveToFirst())
        {
            do {
                int id = data.getInt(data.getColumnIndex("id"));
                Cursor count = db.doSelectQuery("SELECT count(visit.id) as number FROM visit WHERE visit.facility_id = " + id);
                count.moveToFirst();
                if (count.getInt(count.getColumnIndex("number")) == 0) {
                    return data.getString(data.getColumnIndex("name"));
                }
            }while(data.moveToNext());
        }

        return "";
    }
    /*
     * Metodo encargado de retornar el registro de la base de datos correspondiente a la visita con
     * la fecha mas reciente.
     * Retorna un HashMap con clave String correspondiente al nombre del centro y con valor un String
     * con la fecha de dicha visita.
     */
    public HashMap<String,String> visitaReciente() throws ParseException {
        HashMap<String,String> fecha = new HashMap<>();
        String query = "SELECT facility.name as name,visit.date as date FROM facility,visit WHERE facility.id = visit.facility_id ";
        Cursor data = db.doSelectQuery(query);
        String name = "No hay datos";
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
        if(fechaReciente==null)
        {
            fecha.put(name,"No hay datos");
        }
        else
        {
            fecha.put(name,formatDate.format(fechaReciente));
        }
        return  fecha;
    }




    public void insertarResponseEvaluation(int id, int idEvaluation, float valoracion, int aspect, int facility_id)
    {
        db.insertTableResponseEvaluation(db.getWritableDatabase(),new ResponseEvaluation(id,idEvaluation,valoracion, aspect, "no", facility_id));
    }
    /*
     * Metodo encargado de obtener la valoracion asociada a las dimensiones presentes en el centro y
     * mostradas en los cardView dependiendo del fragmento desplegado segun el servicio.
     * Recibe como parametro un entero con el id de la dimension en cuestion y un entero con el id
     * del centro del cual se quiere obtener su valoracion.
     * Retorna un float con la valoracion registrada en la base de datos del dispositivo.
     *
     */
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
