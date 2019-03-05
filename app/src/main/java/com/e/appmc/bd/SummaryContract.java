package com.e.appmc.bd;

import android.provider.BaseColumns;

public class SummaryContract
{
    public static abstract class SummaryEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "summary";
        public static final String ID = "id";
        public static final String FACILITY_ID = "facility_id";
        public static final String CONTENT = "content";
        public static final String DATE = "created";
        public static final String SYNC_STATE ="sync_status";
    }
}
