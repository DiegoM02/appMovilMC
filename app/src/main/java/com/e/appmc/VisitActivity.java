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
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        mGeofenceList = new ArrayList<>();
        Geofence geofence = new Geofence.Builder().setRequestId("Utalca").setCircularRegion(-35.00205f,-71.22992f,1000).setExpirationDuration(600000).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT |
                Geofence.GEOFENCE_TRANSITION_DWELL).setLoiteringDelay(1).build();
        mGeofenceList.add(geofence);
        GeofencingClient mGeofencingClient = new GeofencingClient(this.getApplicationContext());
        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // do something
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // do something
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
