package com.e.appmc.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSService extends Service {

    private static final String TAG ="BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERFVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    private class LocationListener implements  android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e(TAG,"LocationListener" + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG,"onLocationChanged" + location);
            mLastLocation.set(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
