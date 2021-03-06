package com.e.appmc.bd;

import android.provider.BaseColumns;

public class VisitContract {
    public static abstract class VisitEntry implements BaseColumns {
        public static final String TABLE_NAME ="visit";

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String FACILITY_ID = "facility_id";
        public static final String DATE = "date";
        public static final String ENTER = "enter";
        public static final String EXIT = "exit";
        public static final String COMMENT = "comment";
        public static final String SYNC_STATE = "state";

    }
}
