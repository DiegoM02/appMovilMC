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
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.e.appmc.GeofenceTransitionsIntentService;
import com.e.appmc.VisitActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class GPSService extends Service implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG ="BOOMBOOMTESTGPS";
    private PendingIntent mGeofencePendingIntent;
    private ArrayList<Geofence> mGeofenceList;
    private GeofencingClient mGeofencingClient;
    private String userName;
    private int userID;
    private GoogleApiClient googleApiClient;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Star GPS Service");
        googleApiClient.reconnect();
        //startForeground();
        //userID = intent.getExtras().getInt("id");
        return START_STICKY;

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onCreate()
    {

        super.onCreate();
        //startForeground();
        //GoogleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        //GoogleApiClient
        System.out.println("Entre al Servicio");
        mGeofenceList = new ArrayList<>();
        Geofence geofence = new Geofence.Builder().setRequestId("Mi casa").setCircularRegion(-35.07468,-71.25500,50).setExpirationDuration(600000).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT |
                Geofence.GEOFENCE_TRANSITION_DWELL).setLoiteringDelay(1).build();
        mGeofenceList.add(geofence);
        mGeofencingClient = new GeofencingClient(this.getApplicationContext());
        /*mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
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
                });*/

    }



    private GeofencingRequest getGeofencingRequest() {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL)
                .addGeofences(mGeofenceList)
                .build();
    }

    private PendingIntent getGeofencePendingIntent()
    {
        //Intent intent = new Intent(this.getApplicationContext(), GeofenceTransitionsIntentService.class);
        //intent.putExtra("id",this.userID);
        //mGeofencePendingIntent = PendingIntent.getService(this.getApplicationContext(), 0, intent, PendingIntent.
          //      FLAG_UPDATE_CURRENT);
        Intent intent = new Intent(this, GeofenceReceiver.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Google Api Client Connected");
        startLocationMonitor();
        startGeofencing();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Api Client Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection Failed:" + connectionResult.getErrorMessage());
    }


    public class MyBinder extends Binder {
        public GPSService getService() {
            return GPSService.this;
        }
    }

    private void startLocationMonitor() {
        Log.d(TAG, "start location monitor");
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates
                    (googleApiClient, locationRequest, new LocationListener()
                    {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "Location Change Lat Lng " +
                                    location.getLatitude() + " " + location.getLongitude());

                        }
                    });
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        }

    }




    private void startGeofencing() {
        Log.d(TAG, "Start geofencing monitoring call");
        mGeofencePendingIntent = getGeofencePendingIntent();
        if (!googleApiClient.isConnected()) {
            Log.d(TAG, "Google API client not connected");
        } else {
            try {
                LocationServices.GeofencingApi.addGeofences(
                        googleApiClient, getGeofencingRequest(),
                        mGeofencePendingIntent).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.d(TAG, "Successfully Geofencing Connected");
                        } else {
                            Log.d(TAG, "Failed to add Geofencing " + status.getStatus());
                        }
                    }
                });
            } catch (SecurityException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

}
