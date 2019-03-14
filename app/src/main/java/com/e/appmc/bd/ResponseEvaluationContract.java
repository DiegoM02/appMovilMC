package com.e.appmc.bd;

import android.provider.BaseColumns;

public class ResponseEvaluationContract {

    public static abstract class ResponseEvaluationEntry implements BaseColumns {
        public static final String TABLE_NAME = "response_evaluation";
        public static final String ID = "id";
        public static final String ID_EVALUATION = "id_evaluation";
        public static final String ASSESSMENT = "assessment";
        public static final String SYNC_STATE = "sync_status";
    }
}
