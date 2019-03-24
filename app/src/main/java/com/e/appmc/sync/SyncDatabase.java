package com.e.appmc.sync;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.e.appmc.DBMediator;
import com.e.appmc.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Clase encargada de administrar las peticiones de actualizaciones entre la base de datos interna
 * con la base de daros MySQL externa.
 *
 * */
public class SyncDatabase {



    /**     direcciones url de los web services           */
    private final static String IP = "192.168.1.7";
    private final static String URL_CENTRO = "http://"+IP+"/syncpersonal/modelos/insert_facility.php";
    private final static String URL = "http://"+IP+"/syncpersonal/modelos/insert_personal.php";
    private final static String URL_CENTRO_WEBSITE = "http://"+IP+"/syncpersonal/modelos/selectFacility.php";
    private final static String URL_DIMENSION_WEBSITE = "http://"+IP+"/syncpersonal/modelos/selectAspect.php";
    private final static String URL_SUMMARY= "http://"+IP+"/syncpersonal/php/insert_summary.php";
    private final static String URL_UPDATE_PERSONAl = "http://"+IP+"/syncpersonal/php/update_personal.php";
    private final static String URL_LOGIN_USER = "http://"+IP+"/syncpersonal/modelos/login_mcs.php";
    private AppCompatActivity activity;
    private Context activityC;
    private DBMediator mediator;
    private HashMap<String,String> resultLogin;

    public SyncDatabase(AppCompatActivity activity) {
        this.activity = activity;
        this.mediator = new DBMediator(this.activity);

    }




    /**
     * Metodo encargado de enviar una peticion al servidor para sincronizar el personal
     * insertando este en la plataforma web, este envia los datos de registro del personal
     * mediante un peticion post y recibe un respuesta segun el estado en que termino la peticion
     * si el status es no entonces la peticion no se realizo con exito, en caso contrario la peticion devuelve yes.
     * */
    public void syncPersonaSQLite() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("personalJSON", mediator.composeJSONfromSQLitePersonal());
        client.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        System.out.println(obj.get("id"));
                        System.out.println(obj.get("status"));
                        mediator.updateSyncStatus(obj.get("id").toString(), obj.get("status").toString(),1);
                    }
                    Toast.makeText(activity.getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(activity.getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    /**
     * Metodo encargado de sincronizar los datos de los centros de costos en la base de datos interna hacia la base externa MYSQL
     * de la plataforma web, este envia todos los centros del usuario logeado.
     *
     * */
    public void syncFacilitySQLite()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("centroJSON", mediator.composeJSONfromSQLiteFacility());
        client.post(URL_CENTRO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                try {
                     JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        System.out.println(obj.get("id"));
                        System.out.println(obj.get("status"));
                        mediator.updateSyncStatusFacility(obj.get("id").toString(), obj.get("status").toString());
                    }
                    Toast.makeText(activity.getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(activity.getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Metodo encargado de sincronizar desde la base de datos de la plataforma web los centros de costos
     * hacia la base de datos interna, enviando el id del usuario logeado actualmente.
     * */
    public void syncFacilityWebsite()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("idUser", "30000");
        client.setTimeout(10000);
        client.post(URL_CENTRO_WEBSITE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                       // mediator.insertarFacility(obj.getInt("id_facility"),obj.getInt("user_id"),obj.getString("name_facility"),
                          //      obj.getString("code_facility"),obj.getString("date_facility"),obj.getString("address_facility"),obj.getInt("service_id_facility"));
                    }
                    Toast.makeText(activity.getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(activity.getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Metodo encargado de sincronizar los datos de los aspectos de las dimensiones a evaluar
     * desde la plataforma web hacia la base de datos interna de la aplicacion.
     * Este recibe el id , nombre , fecha de creacion del aspecto  y porcentaje de aprobacion de este.
     * */
    public void syncAspectWebsite()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        client.post(URL_DIMENSION_WEBSITE,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        mediator.insertarAspect(obj.getInt("id"), obj.getString("name"), obj.getString("created"),obj.getDouble("approval_percentage"));
                    }
                    Toast.makeText(activity.getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(activity.getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Metodo encargado de sincronizar los resumenes de las evaluaciones generados en la base de datos
     * internas llevando los datos a la plataforma web.
     * Este envia los datos correspondientes al resumen , ya se el personal involurado , las preguntas y puntos criticos.
     * **/
    public void syncSummarySQLite()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("summaryJSON", mediator.composeJSONFromSQLiteSummary());
        client.post(URL_SUMMARY,params,new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    for(int i=0;i<arr.length();i++)
                    {
                        JSONObject object = (JSONObject) arr.get(i);
                        System.out.println(object.get("id"));
                        System.out.println(object.get("status"));
                        mediator.updateSyncStatus(object.get("id").toString(),object.get("status").toString(),2);
                    }
                    Toast.makeText(activity.getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                }catch (JSONException e)
                {
                    // TODO Auto-generated catch block
                    Toast.makeText(activity.getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Metodo encargado de actualizar el estado del personal ha habilitado o deshabilitado segun
     * se estime conveniente, enviando los datos de actualizacion a la plataforma web desde
     * la base de datos interna.
     * */
    public void updatePersonalSQLite()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("updatePersonalJSON", mediator.composeJSONfromSQLitePersonalStatus());
        client.post(URL_UPDATE_PERSONAl,params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(String content) {
                System.out.println(content);
                try {
                    JSONArray arr = new JSONArray(content);
                    for(int i=0;i<arr.length();i++)
                    {
                        JSONObject object = (JSONObject) arr.get(i);
                        System.out.println(object.getString("status"));

                        Toast.makeText(activity.getApplicationContext(),"Updated !!",Toast.LENGTH_LONG);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }

        });
    }


    public void syncFacilityWebsite2() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("syncFacilityJSON", "9");
        client.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        System.out.println(obj.get("id"));
                        System.out.println(obj.get("status"));
                        //mediator.updateSyncStatus(obj.get("id").toString(), obj.get("status").toString());
                    }
                    Toast.makeText(activity.getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(activity.getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    /**
     * Metodo encargado de verificar si el usuario ingresado existe en la plataforma web y validarlo
     * en la aplicacion movil para que el usuario ingrese a esta.
     * Se envian los datos de usario username y password para realizar la verificacion.
     * Si la petecion es exitosa se devuelve el id y el nombre del usuario
     * */
    public void loginSQLite(String user, String password)
    {
        SyncHttpClient clientSync = new SyncHttpClient();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("loginJSON",mediator.composeJSONfromSQLiteUserLogin(user,password));
        //clientSync.post(URL_LOGIN_USER,params,);
        client.post(URL_LOGIN_USER,params, new AsyncHttpResponseHandler()
        {


            @Override
            public void onSuccess(String content) {
                try {
                    System.out.println(content);
                    JSONArray arr = new JSONArray(content);
                    for(int i =0;i<arr.length();i++)
                    {
                        JSONObject object = (JSONObject) arr.get(i);
                        System.out.println("ID" + object.getString("id"));
                        System.out.println("name" + object.getString("name"));
                        String name = object.getString("name") + " " + object.getString("surname");
                        ((MainActivity)activity).betweenSession(name,object.getInt("id"),object.getInt("role"));



                    }
                    Toast.makeText(activity.getApplicationContext(),"Login Sucesfully",Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(activity.getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });


    }




}
