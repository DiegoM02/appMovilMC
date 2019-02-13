package com.e.bd.appmc;

import android.provider.BaseColumns;

public class EvaluationQuestionContract {

    public static abstract  class evaluationQuestionEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "evaluation_question";
        public static final String QUESTION_ID = "question_id";
        public static final String EVALUATION_ID = "evaluation_id";
    }
}
