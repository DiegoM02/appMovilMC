package com.e.appmc;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class GeofenceTransitionsIntentService extends IntentService {
    public GeofenceTransitionsIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // do something
            Toast.makeText(this.getApplicationContext(),"adasdada",Toast.LENGTH_LONG);
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // do something else
            Toast.makeText(this.getApplicationContext(),"adasdada",Toast.LENGTH_LONG);
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            // do something else again
            Toast.makeText(this.getApplicationContext(),"adasdada",Toast.LENGTH_LONG);
        }
    }





}
