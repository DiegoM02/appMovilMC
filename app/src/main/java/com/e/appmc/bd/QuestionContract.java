package com.e.appmc.bd;

import android.provider.BaseColumns;

public class QuestionContract {
    public static abstract class questionEntry implements BaseColumns {
        public static final String TABLE_NAME ="question";
        public static final String ID = "id";
        public static final String DESCRIPTION = "description";
        public  static final String APROVAL_PORCENTAGE = "aproval_porcentage";
        public static final String TYPE = "type";
        public static final String ASPECT_ID = "aspect_id";
        public static final String POINT_ID = "point_id";
        public static final String EVALUATION_ID = "evaluation_id";

    }

}

