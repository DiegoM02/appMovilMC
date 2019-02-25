package com.e.appmc.bd;

import android.content.ContentValues;

public class Subservice
{
    private int id;
    private String created;
    private String modified;
    private String name;
    private String description;
    private int service_id;

    public Subservice(int id, String created, String modified, String name, String description, int service_id) {
        this.id = id;
        this.created = created;
        this.modified = modified;
        this.name = name;
        this.description = description;
        this.service_id = service_id;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getService_id() {
        return service_id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SubserviceContract.SubserviceEntry.ID, id);
        values.put(SubserviceContract.SubserviceEntry.NAME, name);
        values.put(SubserviceContract.SubserviceEntry.CREATED,created);
        values.put(SubserviceContract.SubserviceEntry.MODIFIED,modified);
        values.put(SubserviceContract.SubserviceEntry.DESCRIPTION,description);
        values.put(SubserviceContract.SubserviceEntry.SERVICE_ID,service_id);

        return values;
    }
}
