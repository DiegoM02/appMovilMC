package com.e.appmc.bd;

import android.content.ContentValues;

public class EvaluationQuestion {
    private int question_id;
    private int evaluation_id;

    public EvaluationQuestion(int question_id, int evaluation_id) {
        this.question_id = question_id;
        this.evaluation_id = evaluation_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(EvaluationQuestionContract.evaluationQuestionEntry.EVALUATION_ID,evaluation_id);
        values.put(EvaluationQuestionContract.evaluationQuestionEntry.QUESTION_ID,question_id);

        return values;
    }
}
