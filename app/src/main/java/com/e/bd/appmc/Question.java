package com.e.bd.appmc;

import android.content.ContentValues;

public class Question {

    private int id;
    private String description;
    private double aprovalPorcentage;
    private int type;
    private int aspect_id;
    private int point_id;
    private int evaluation_id;

    public Question(int id, String description, double aprovalPorcentage, int type,int aspect_id, int point_id, int evaluation_id) {
        this.id = id;
        this.description = description;
        this.aprovalPorcentage = aprovalPorcentage;
        this.type = type;
        this.aspect_id = aspect_id;
        this.point_id = point_id;
        this.evaluation_id = evaluation_id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAprovalPorcentage() {
        return aprovalPorcentage;
    }

    public int getType() {
        return type;
    }

    public int getAspectId()
    {
        return aspect_id;
    }

    public int getAspect_id() {
        return aspect_id;
    }

    public int getPoint_id() {
        return point_id;
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(QuestionContract.questionEntry.ID,id);
        values.put(QuestionContract.questionEntry.DESCRIPTION, description);
        values.put(QuestionContract.questionEntry.APROVAL_PORCENTAGE,aprovalPorcentage);
        values.put(QuestionContract.questionEntry.TYPE,type);
        values.put(QuestionContract.questionEntry.ASPECT_ID,aspect_id);
        values.put(QuestionContract.questionEntry.POINT_ID,point_id);
        values.put(QuestionContract.questionEntry.EVALUATION_ID,evaluation_id);

        return values;
    }
}

