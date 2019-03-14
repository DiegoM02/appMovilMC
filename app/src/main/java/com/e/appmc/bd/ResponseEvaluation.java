package com.e.appmc.bd;

import android.content.ContentValues;

public class ResponseEvaluation {
    private int id;
    private int id_evaluation;
    private float valoracion;
    private String sync_status;

    public ResponseEvaluation(int id, int id_evaluation, float valoracion, String sync_status) {
        this.id = id;
        this.id_evaluation = id_evaluation;
        this.valoracion = valoracion;
        this.sync_status = sync_status;
    }

    public int getId() {
        return id;
    }

    public int getId_evaluation() {
        return id_evaluation;
    }

    public float getValoracion() {
        return valoracion;
    }

    public String getSync_status() {
        return sync_status;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(ResponseEvaluationContract.ResponseEvaluationEntry.ID,id);
        values.put(ResponseEvaluationContract.ResponseEvaluationEntry.ID_EVALUATION,id_evaluation);
        values.put(ResponseEvaluationContract.ResponseEvaluationEntry.ASSESSMENT,valoracion);
        values.put(ResponseEvaluationContract.ResponseEvaluationEntry.SYNC_STATE,sync_status);

        return values;

    }
}
