package com.e.appmc.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.e.appmc.DBMediator;
import com.e.appmc.EvaluationActivity;
import com.e.appmc.MainActivity;
import com.e.appmc.MainMenuActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GeofenceTransitionsJobIntentService extends JobIntentService {


    private static final int JOB_ID = 573;

    private static final String TAG = "GeofenceTransitioIS";

    private static final String CHANNEL_ID="channel01";

    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";

    private static final String HORA_INICIO="hora_inicio";

    private static final String ID_USUARIO = "id_usuario";

    private int id;

    private DBMediator mediator;

    private String requestID;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, GeofenceTransitionsJobIntentService.class, JOB_ID, intent);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        mediator = new DBMediator(getApplicationContext());
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            //String errorMessage = GeofenceErrorMessages.getErrorString(this,
              //      geofencingEvent.getErrorCode());
            Log.e(TAG, "error");
            return;
        }
        //id = intent.getExtras().getInt("id");
        System.out.println("On JobIntent: " + id);
        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Get the geofences that were triggered. A single event can trigger multiple geofences.

            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            requestID = triggeringGeofences.get(0).getRequestId();
            if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
            {
                startVisit();
            }
            else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT)
            {
                stopVisit(requestID);
            }
            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition,
                    triggeringGeofences);
            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails,geofenceTransition);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            //Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
            System.out.println("error");
        }

    }

    private String getGeofenceTransitionDetails(
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {
        String geofenceTransitionString = getTransitionString(geofenceTransition);
        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);
        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }
    private void sendNotification(String notificationDetails, int geofenceTrasition) {
        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "mcapp";
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }
        Intent notificationIntent = null;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        if(geofenceTrasition == Geofence.GEOFENCE_TRANSITION_ENTER)
        {
            notificationIntent = new Intent(getApplicationContext(), EvaluationActivity.class);
            notificationIntent.putExtra("requestID",requestID);
            // Add the main Activity to the task stack as the parent.
            stackBuilder.addParentStack(EvaluationActivity.class);
        }
        else if(geofenceTrasition == Geofence.GEOFENCE_TRANSITION_EXIT)
        {
            notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
            notificationIntent.putExtra("requestID",requestID);
            // Add the main Activity to the task stack as the parent.
            stackBuilder.addParentStack(MainActivity.class);
        }
        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);
        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // Define the notification settings.
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        android.R.drawable.ic_lock_idle_alarm))
                .setColor(Color.RED)//.addAction(android.R.drawable.ic_menu_send,"Inicar Visita",notificationPendingIntent)
                .setContentTitle(notificationDetails)
                .setContentText("transition")
                .setContentIntent(notificationPendingIntent);
        System.out.println("Notificacion lista");
        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }
        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);
        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }


    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Entro";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "salio";
            default:
                return "entro_salio";
        }
    }

    private void startVisit()
    {
        if(obtenerHoraInicioRecordarSession().equals("no hay hora"))
        {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String date = format.format(calendar.getTime());
            guardarHoraInicio(date);
        }
    }

    private void stopVisit(String requestID)
    {
        if(!obtenerHoraInicioRecordarSession().equals("no hay hora"))
        {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String date = format.format(calendar.getTime());
            mediator.registrarVisita(this.obtenerIdUsuarioRecordarSesion(),requestID,this.obtenerHoraInicioRecordarSession(),date);
            //Toast.makeText(this.getApplicationContext(),"pudimos perrito",Toast.LENGTH_LONG);
            this.guardarHoraInicio("no hay hora");

        }
    }

    public void guardarHoraInicio(String hora)
    {
        SharedPreferences horaPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        horaPreferencias.edit().putString(HORA_INICIO,hora).apply();
    }

    public String obtenerHoraInicioRecordarSession()
    {
        SharedPreferences horaPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR,MainActivity.MODE_PRIVATE);
        return horaPreferencias.getString(HORA_INICIO,"no aplica");
    }

    public int obtenerIdUsuarioRecordarSesion()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getInt(ID_USUARIO,0);
    }
}
