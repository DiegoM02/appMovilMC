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

    private static final String ESTADO_CHECK_BOX ="estado_check_box";

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
            Log.e(TAG, "error");
            return;
        }
        System.out.println("On JobIntent: " + id);

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {


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

            String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition,
                    triggeringGeofences);
            sendNotification(geofenceTransitionDetails,geofenceTransition);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            System.out.println("error");
        }

    }
    /*
     * Metodo encargado de obtener los detalles correspondientes a la transicion realizada en el
     * geofence.
     * Recibe como parametro un entero con el valor correspondiente a la transicion y un List del
     * objeto Geofence correspondiente a los geofences que lanzaron el trigger.
     * Retorna un String con los detalles de la transicion.
     */
    private String getGeofenceTransitionDetails (int geofenceTransition,List<Geofence> triggeringGeofences)
    {
        String geofenceTransitionString = getTransitionString(geofenceTransition);
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);
        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }
    /*
     * Metodo encargado de lanzar la notificacion en el barra de tareas del dispositivo, ademas
     * de seleccionar esta segun el trigger que la haya desatado.
     * Recibe como parametro un String con los detalles de la notifiacion y un entero que represeta
     * el tipo de esta.
     */
    private void sendNotification(String notificationDetails, int geofenceTrasition) {

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "mcapp";
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        Intent notificationIntent = null;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        if(geofenceTrasition == Geofence.GEOFENCE_TRANSITION_ENTER)
        {
            notificationIntent = new Intent(getApplicationContext(), EvaluationActivity.class);
            notificationIntent.putExtra("requestID",requestID);
            stackBuilder.addParentStack(EvaluationActivity.class);
        }
        else if(geofenceTrasition == Geofence.GEOFENCE_TRANSITION_EXIT)
        {
            notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
            notificationIntent.putExtra("requestID",requestID);
            stackBuilder.addParentStack(MainActivity.class);
        }

        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        android.R.drawable.ic_lock_idle_alarm))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText("transition")
                .setContentIntent(notificationPendingIntent);
        System.out.println("Notificacion lista");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        builder.setAutoCancel(true);
        mNotificationManager.notify(0, builder.build());
    }

    /*
     * Metodo encargado de retornar un texto que representara el tipo de transicion segun el trigger
     * que haya sido desencadenado.
     * Recibe como parametro el entero que representa el tipo de transicion.
     * Retorna un String con el tipo de trasicion traducido al español,
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Has iniciado una visita";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "Has terminado una visita";
            default:
                return "Visita en curso";
        }
    }
    /*
     * Metodo encargado de registrar los parametros de inicio de la visita, esto si no existe una
     * visita previa en curso.
     */
    private void startVisit()
    {
        if(obtenerHoraInicioRecordarSession().equals("no hay hora"))
        {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String hour = format.format(calendar.getTime());
            guardarHoraInicio(hour);
        }
    }
    /*
     * Metodo encargado de guardar los parametros de termno de una visita, esto si se encuentra una
     * visita en curso.
     * Recibe como parametro un String con el centro correspondiente.
     */
    private void stopVisit(String requestID)
    {
        if(!obtenerHoraInicioRecordarSession().equals("no hay hora"))
        {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
            String date = formatDate.format(calendar.getTime());
            String hour = formatHour.format(calendar.getTime());
            mediator.registrarVisita(this.obtenerIdUsuarioRecordarSesion(),requestID,this.obtenerHoraInicioRecordarSession(),hour,date);
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

    public boolean obtenerEstadoRecordarSession()
    {
        SharedPreferences sesionPreferencias = getSharedPreferences(SESSION_ESTADO_RECORDAR, MainActivity.MODE_PRIVATE);
        return sesionPreferencias.getBoolean(ESTADO_CHECK_BOX,false);
    }
}
