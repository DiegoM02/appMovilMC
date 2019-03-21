package com.e.appmc.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;

import com.e.appmc.GeofenceTransitionsIntentService;

public class GeofenceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //System.out.println("Broadcast Recibido");
        GeofenceTransitionsJobIntentService.enqueueWork(context, intent);
    }
}
