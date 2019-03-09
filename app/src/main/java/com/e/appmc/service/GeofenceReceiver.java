package com.e.appmc.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;

public class GeofenceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Bundle geofenceData = bundle.getBundle("");

        //Retrieve ids of geofences triggered
        String[] geofencesIds = geofenceData.getStringArray("ids");
        //Retrieve transition code (1 == enter, 0 == exit)
        int transitionCode = geofenceData.getInt("transition");

        //Retrieve the geolocation that has triggered these geofences
        //(Note : you will have to parse it as a JSONObject)
        //Keys are : provider, latitude, longitude, altitude, accuracy, bearing, speed, time
        String location = geofenceData.getString("triggeringLocation");

        //Do anything you want, for example : start an activity..
    }
}
