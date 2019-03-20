package com.e.appmc.bd;

import android.provider.BaseColumns;

public class AspectContract {
    public static abstract class AspectEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "aspect";
        public static final String ID = "id";
        public  static  final String NAME = "name";
        public static final String CREATED = "created";
        public static final String APROVAL_PORCENTAGE = "aproval_porcentage";
        public static final String QUESTION_ID = "question_id";

    }
}
