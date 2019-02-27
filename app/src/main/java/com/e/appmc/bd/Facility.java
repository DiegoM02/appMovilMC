package com.e.appmc.bd;

import android.content.ContentValues;

public class Facility {
    private int id;
    private int user_id;
    private String created;
    private String code;
    private String name;
    private String address;
    private int service_id;
    private int evaluation_id;
    private String status_sync;

    public Facility(int id, int user_id, String created, String code, String name, String address,int service_id, int evaluation_id,String status_sync) {
        this.id = id;
        this.user_id = user_id;
        this.created = created;
        this.code = code;
        this.name = name;
        this.address = address;
        this.service_id = service_id;
        this.evaluation_id = evaluation_id;
        this.status_sync = status_sync;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getCreated() {
        return created;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getService_id() {
        return service_id;
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public String getStatus_sync()
    {
        return this.status_sync;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(FacilityContract.FacilityEntry.ID, id);
        values.put(FacilityContract.FacilityEntry.NAME, name);
        values.put(FacilityContract.FacilityEntry.USER_ID,user_id);
        values.put(FacilityContract.FacilityEntry.CODE,code);
        values.put(FacilityContract.FacilityEntry.CREATED,created);
        values.put(FacilityContract.FacilityEntry.ADDRESS,address);
        values.put(FacilityContract.FacilityEntry.SERVICE_ID,service_id);
        values.put(FacilityContract.FacilityEntry.EVALUATION_ID,evaluation_id);
        values.put(FacilityContract.FacilityEntry.SYNC_STATUS,status_sync);

        return values;
    }
}
