package com.e.appmc.bd;

import android.content.ContentValues;

public class Personal {

    private int id;
    private String name;
    private String surname;
    private String  rut;
    private String phone;
    private String email;
    private int facility_id;
    private int state;
    private String created;
    private  String sync_status;


    public Personal(int id, String name, String surname, String rut, String phone, String email, int facility_id,int state,String created,String sync_status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.rut = rut;
        this.phone = phone;
        this.email = email;
        this.facility_id = facility_id;
        this.state = state;
        this.created = created;
        this.sync_status = sync_status;
    }


    public int getFacility_id() {
        return facility_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRut() {
        return rut;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getState() {
        return state;
    }

    public String getCreated() {
        return created;
    }

    public String getSync_status() {
        return sync_status;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        //values.put(PersonalContract.PersonalEntry.ID,id);
        values.put(PersonalContract.PersonalEntry.NAME,name);
        values.put(PersonalContract.PersonalEntry.SURNAME,surname);
        values.put(PersonalContract.PersonalEntry.RUT,rut);
        values.put(PersonalContract.PersonalEntry.PHONE,phone);
        values.put(PersonalContract.PersonalEntry.EMAIL,email);
        values.put(PersonalContract.PersonalEntry.FACILITY_ID,facility_id);
        values.put(PersonalContract.PersonalEntry.STATE,state);
        values.put(PersonalContract.PersonalEntry.CREATED,created);
        values.put(PersonalContract.PersonalEntry.SYNC_STATE,sync_status);
        return values;
    }



}
