package com.e.appmc.service;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

public class GPSService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
