package com.e.bd.appmc;

import android.content.ContentValues;

public class Service
{
    private int id;
    private String code;
    private String name;
    private String identifier;
    private String icon;

    public Service(int id, String code, String name, String identifier, String icon) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.identifier = identifier;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getIcon() {
        return icon;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ServiceContract.ServiceEntry.ID, id);
        values.put(ServiceContract.ServiceEntry.NAME, name);
        values.put(ServiceContract.ServiceEntry.CODE,code);
        values.put(ServiceContract.ServiceEntry.IDENTIFIER,identifier);
        values.put(ServiceContract.ServiceEntry.ICON,icon);

        return values;
    }
}
