package com.e.appmc.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.e.appmc.DBMediator;
import com.e.appmc.GeofenceTransitionsIntentService;
import com.e.appmc.MainActivity;
import com.e.appmc.Singleton;
import com.e.appmc.VisitActivity;
import com.e.appmc.bd.Facility;
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
    private DBMediator mediator;

    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";
    private static final String ID_USUARIO = "id_usuario";
    private static final String ESTADO_CHECK_BOX = "estado_check";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Star GPS Service");
        googleApiClient.reconnect();
        System.out.println("Service UserID: " + userID);
        if(this.obtenerEstadoRecordarSession())
        {
            System.out.println("Inicio Sticky");
            return START_STICKY;
        }
        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onCreate()
    {

        super.onCreate();
        mediator = new DBMediator(getApplicationContext());
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        System.out.println("Entre al Servicio");
        mGeofenceList = new ArrayList<>();
        this.fillGeofences();
        mGeofencingClient = new GeofencingClient(this.getApplicationContext());

    }


    /*
     * Metodo encargado de retornar los tipos de trigger que seran afectados en los geoefences que se
     * estan monitoreando.
     * Retorna un objeto de tipo GeofencingRequest con estos.
     */
    private GeofencingRequest getGeofencingRequest() {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL)
                .addGeofences(mGeofenceList)
                .build();
    }
    /*
     * Metodo encargado de generar el pending Intent que sera enviado al JobService utilizado para
     * el monitoreo de los geofences, ademas de poblarlo el Intent contenido en este con los datos
     * necesarios.
     * Retorna un objeto de clase PendingIntent que servira para llamar al servicio.
     */
    private PendingIntent getGeofencePendingIntent()
    {
        Intent intent = new Intent(this, GeofenceReceiver.class);
        intent.putExtra("id",userID);
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
    /*
     * Metodo encargado de iniciar el location monitor, que escuchara todos los cambios de posicion
     * que sufra el dispositivo.
     */
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

    /*
     * Metodo encargado de comenzar el servicio de geofencing que sera realizado por cada uno de los
     * geofences registrados.
     */
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

    /*
     * Metodo encargado de rellenar los geofences segun los datos de los centros que esten en la
     * memoria.
     */
    private void fillGeofences()
    {
        int id = this.obtenerIdUsuarioRecordarSesion();

        Facility[] centros = this.mediator.obtenerCentros(id);
        for(int i=0;i<centros.length;i++)
        {
            Geofence geofence = new Geofence.Builder().setRequestId(centros[i].getName()).setCircularRegion(centros[i].getLatitude(),centros[i].getLongitude(),centros[i].getRadius()).setExpirationDuration(600000).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                    Geofence.GEOFENCE_TRANSITION_EXIT |
                    Geofence.GEOFENCE_TRANSITION_DWELL).setLoiteringDelay(1).build();
            mGeofenceList.add(geofence);
        }
    }

    public int obtenerIdUsuarioRecordarSesion()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getInt(ID_USUARIO,0);
    }

    public boolean obtenerEstadoRecordarSession()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getBoolean(ESTADO_CHECK_BOX,false);
    }

}
