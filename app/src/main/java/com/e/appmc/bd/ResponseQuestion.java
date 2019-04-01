package com.e.appmc.bd;

import android.content.ContentValues;

public class ResponseQuestion {


    private int id_evaluation;
    private int id_question;
    private float valoracion;
    private String sync_status;


    public ResponseQuestion( int id_evaluation, int id_question, float valoracion, String sync_status) {

        this.id_evaluation = id_evaluation;
        this.id_question = id_question;
        this.valoracion = valoracion;
        this.sync_status = sync_status;
    }

    public String getSync_status()
    {
        return sync_status;
    }

    public int getId_evaluation() {
        return id_evaluation;
    }

    public int getId_question() {
        return id_question;
    }

    public float getValoracion() {
        return valoracion;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(ResponseQuestionContract.ResponseQuestionEntry.ID_EVALUATION,id_evaluation);
        values.put(ResponseQuestionContract.ResponseQuestionEntry.ID_QUESTION,id_question);
        values.put(ResponseQuestionContract.ResponseQuestionEntry.ASSESSMENT,valoracion);
        values.put(ResponseQuestionContract.ResponseQuestionEntry.SYNC_STATE,sync_status);

        return values;

    }
}
