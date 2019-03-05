package com.e.appmc.bd;

import android.content.ContentValues;

public class Aspect {

    private int id;
    private String created;
    private String name;
    private double aproval_procentage;

    public Aspect(int id, String created, String name, double aproval_procentage) {
        this.id = id;
        this.created = created;
        this.name = name;
        this.aproval_procentage = aproval_procentage;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public double getAproval_procentage() {
        return aproval_procentage;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(AspectContract.AspectEntry.ID,id);
        values.put(AspectContract.AspectEntry.NAME,name);
        values.put(AspectContract.AspectEntry.CREATED,created);
        values.put(AspectContract.AspectEntry.APROVAL_PORCENTAGE,aproval_procentage);
        return  values;

    }
}
