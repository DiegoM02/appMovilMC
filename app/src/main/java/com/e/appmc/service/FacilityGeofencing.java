package com.e.appmc.service;

import android.content.Context;
import android.provider.SyncStateContract;


import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;

public class FacilityGeofencing {


    GeofencingClient geoClient;
    Context context;
    ArrayList<Geofence> geofenceList;





    public FacilityGeofencing(Context context) {
        this.context = context;
        this.geoClient = new GeofencingClient(context);
        geofenceList = new ArrayList<>();
        geofenceList.add(new Geofence.Builder().setRequestId("Casa Ariel").setCircularRegion(-35.07455,	-71.25486,100).setExpirationDuration(600000)
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT).build());
    }

    public GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }


}
