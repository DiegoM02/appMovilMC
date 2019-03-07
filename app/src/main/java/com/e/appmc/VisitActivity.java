package com.e.appmc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.e.appmc.service.FacilityGeofencing;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class VisitActivity extends AppCompatActivity {

    private PendingIntent mGeofencePendingIntent;
    private ArrayList<Geofence> mGeofenceList;
    private GeofencingClient mGeofencingClient;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        mGeofenceList = new ArrayList<>();
        Geofence geofence = new Geofence.Builder().setRequestId("Mi casa").setCircularRegion(-35.07468,-71.25500,10000).setExpirationDuration(600000).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT |
                Geofence.GEOFENCE_TRANSITION_DWELL).setLoiteringDelay(1).build();
        mGeofenceList.add(geofence);
        mGeofencingClient = new GeofencingClient(this.getApplicationContext());
        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VisitActivity.this,"Geofence listo, ready pa todas las nenas",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VisitActivity.this,"Error" + e.getMessage(),Toast.LENGTH_LONG).show();
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
        mGeofencePendingIntent = PendingIntent.getService(this.getApplicationContext(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }




}
