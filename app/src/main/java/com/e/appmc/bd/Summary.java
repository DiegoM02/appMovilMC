package com.e.appmc.bd;

import android.content.ContentValues;

public class Summary {
    private String content;
    private String date;
    private int facilityId;
    private int id;
    private String sync_status;

    public Summary(String content, String date, int facilityId,String sync_status) {
        this.content = content;
        this.date = date;
        this.facilityId = facilityId;
        this.sync_status = sync_status;


    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues toContentValue()
    {
        ContentValues contentValues = new ContentValues();
        //contentValues.put(SummaryContract.SummaryEntry.ID,id);
        contentValues.put(SummaryContract.SummaryEntry.FACILITY_ID,facilityId);
        contentValues.put(SummaryContract.SummaryEntry.CONTENT,content);
        contentValues.put(SummaryContract.SummaryEntry.DATE,date);
        contentValues.put(SummaryContract.SummaryEntry.SYNC_STATE,sync_status);

        return contentValues;
    }
}
