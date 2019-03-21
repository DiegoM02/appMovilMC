package com.e.appmc.bd;

import android.provider.BaseColumns;

public class ResponseQuestionContract {

    public static abstract class ResponseQuestionEntry implements BaseColumns {

        public static final String TABLE_NAME = "reponse_question";
        public static final String ID = "id";
        public static final String ID_EVALUATION = "id_evaluation";
        public static final String ID_QUESTION = "id_question";
        public static final String ASSESSMENT = "assessment";
        public static final String SYNC_STATE = "sync_status";

    }
}
