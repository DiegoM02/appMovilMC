package com.e.appmc.sync;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.e.appmc.DBMediator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class SyncDatabase {


    private final static String URL = "http://192.168.1.100/syncpersonal/php/insert_personal.php";
    private AppCompatActivity activity;
    private DBMediator mediator;

    public SyncDatabase(AppCompatActivity activity) {
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
}
