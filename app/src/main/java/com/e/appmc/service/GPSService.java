package com.e.appmc.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.e.appmc.GeofenceTransitionsIntentService;
import com.e.appmc.VisitActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class GPSService extends Service {

    private static final String TAG ="BOOMBOOMTESTGPS";
    private PendingIntent mGeofencePendingIntent;
    private ArrayList<Geofence> mGeofenceList;
    private GeofencingClient mGeofencingClient;
    private String userName;
    private int userID;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Star GPS Service");
        //userID = intent.getExtras().getInt("id");
        return START_STICKY;

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate()
    {
        super.onCreate();

        System.out.println("Entre al Servicio");
        mGeofenceList = new ArrayList<>();
        Geofence geofence = new Geofence.Builder().setRequestId("Mi casa").setCircularRegion(-35.07468,-71.25500,50).setExpirationDuration(600000).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT |
                Geofence.GEOFENCE_TRANSITION_DWELL).setLoiteringDelay(1).build();
        mGeofenceList.add(geofence);
        mGeofencingClient = new GeofencingClient(this.getApplicationContext());
        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Service:Succes");
                        //Toast.makeText(VisitActivity.this,"Geofence listo, ready pa todas las nenas",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Service: " + e.getMessage());
                        //Toast.makeText(VisitActivity.this,"Error" + e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private GeofencingRequest getGeofencingRequest() {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL)
                .addGeofences(mGeofenceList)
                .build();
    }

    private PendingIntent getGeofencePendingIntent()
    {
        Intent intent = new Intent(this.getApplicationContext(), GeofenceTransitionsIntentService.class);
        //intent.putExtra("id",this.userID);
        mGeofencePendingIntent = PendingIntent.getService(this.getApplicationContext(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    public class MyBinder extends Binder {
        public GPSService getService() {
            return GPSService.this;
        }
    }
}
