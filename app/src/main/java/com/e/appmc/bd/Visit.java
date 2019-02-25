package com.e.appmc.bd;

import android.content.ContentValues;

public class Visit {
    private int id;
    private int facility_id;
    private int user_id;
    private String enter;
    private String exit;
    private String comment;

    public Visit(int id, int facility_id, int user_id, String enter, String exit, String comment) {
        this.id = id;
        this.facility_id = facility_id;
        this.user_id = user_id;
        this.enter = enter;
        this.exit = exit;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getFacility_id() {
        return facility_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEnter() {
        return enter;
    }

    public String getExit() {
        return exit;
    }

    public String getComment() {
        return comment;
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(VisitContract.VisitEntry.ID, id);
        values.put(VisitContract.VisitEntry.USER_ID,user_id);
        values.put(VisitContract.VisitEntry.FACILITY_ID,facility_id);
        values.put(VisitContract.VisitEntry.ENTER,enter);
        values.put(VisitContract.VisitEntry.EXIT,exit);
        values.put(VisitContract.VisitEntry.COMMENT,comment);

        return values;
    }
}
