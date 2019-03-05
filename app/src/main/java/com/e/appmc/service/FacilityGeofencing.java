package com.e.appmc.service;

import android.content.Context;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;

import java.util.ArrayList;

public class FacilityGeofencing {


    GeofencingClient geoClient;
    Context context;





    public FacilityGeofencing(Context context) {
        this.context = context;
        this.geoClient = new GeofencingClient(context);
    }











}
