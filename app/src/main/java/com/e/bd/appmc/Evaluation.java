package com.e.bd.appmc;

import android.content.ContentValues;

public class Evaluation {

    private int id;
    private String done;
    private int facility_id;

    public Evaluation(int id, String done, int facility_id) {
        this.id = id;
        this.done = done;
        this.facility_id = facility_id;
    }

    public int getId() {
        return id;
    }

    public String getDone() {
        return done;
    }

    public int getFacility_id() {
        return facility_id;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(EvaluationContract.EvaluationEntry.ID,id);
        values.put(EvaluationContract.EvaluationEntry.DONE,done);
        values.put(EvaluationContract.EvaluationEntry.FACILITY_ID,facility_id);

        return values;
    }


}
