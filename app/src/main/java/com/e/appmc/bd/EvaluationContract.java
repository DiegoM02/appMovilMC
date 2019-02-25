package com.e.appmc.bd;

import android.provider.BaseColumns;

public class EvaluationContract {

    public static abstract class EvaluationEntry implements BaseColumns {
        public static  final String TABLE_NAME = "evaluation";
        public static  final String ID = "id";
        public  static  final String DONE = "done";
        public static final String  FACILITY_ID = "facility_id";
    }

}
