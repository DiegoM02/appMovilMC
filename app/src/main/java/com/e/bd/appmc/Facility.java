package com.e.bd.appmc;

import android.content.ContentValues;

public class Facility {
    private int id;
    private int user_id;
    private String created;
    private String code;
    private String name;
    private String address;

    public Facility(int id, int user_id, String created, String code, String name, String address) {
        this.id = id;
        this.user_id = user_id;
        this.created = created;
        this.code = code;
        this.name = name;
        this.address = address;
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

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(FacilityContract.FacilityEntry.ID, id);
        values.put(FacilityContract.FacilityEntry.NAME, name);
        values.put(FacilityContract.FacilityEntry.USER_ID,user_id);
        values.put(FacilityContract.FacilityEntry.CODE,code);
        values.put(FacilityContract.FacilityEntry.CREATED,created);
        values.put(FacilityContract.FacilityEntry.ADDRESS,address);

        return values;
    }
}
