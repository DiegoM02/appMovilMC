package com.e.appmc.sync;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.e.appmc.DBMediator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class SyncDatabase {


    private final static String URL = "http://192.168.1.3/syncpersonal/php/insert_personal.php";
    private final static String URL_CENTRO = "http://192.168.1.3/syncpersonal/php/insert_facility.php";
    private final static String URL_CENTRO_WEBSITE = "http://192.168.1.3/syncwebsite/selectFacility.php";
    private final static String URL_DIMENSION_WEBSITE = "http://192.168.1.3/syncwebsite/selectAspect.php";
    private Context activity;
    private DBMediator mediator;

    public SyncDatabase(Context activity) {
        this.activity = activity;
        this.mediator = new DBMediator(this.activity);
    }

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
                        mediator.updateSyncStatus(obj.get("id").toString(), obj.get("status").toString());
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

    public void syncFacilityWebsite()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("idUser", "9");
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
                        mediator.insertarFacility(obj.getInt("id_facility"),obj.getInt("user_id"),obj.getString("name_facility"),
                                obj.getString("code_facility"),obj.getString("date_facility"),obj.getString("address_facility"),obj.getInt("service_id_facility"));
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
}
