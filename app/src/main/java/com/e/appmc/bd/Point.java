package com.e.appmc.bd;

import android.content.ContentValues;

public class Point {
    private int id;
    private String name;

    public Point(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }



    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(PointContract.pointEntry.ID,id);
        values.put(PointContract.pointEntry.NAME,name);
        return values;

    }
}
