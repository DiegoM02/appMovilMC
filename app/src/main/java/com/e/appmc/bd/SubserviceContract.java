package com.e.appmc.bd;

import android.provider.BaseColumns;

public class SubserviceContract
{
    public static abstract class SubserviceEntry implements BaseColumns {
        public static final String TABLE_NAME ="subservice";

        public static final String ID = "id";
        public static final String CREATED = "created";
        public static final String MODIFIED = "modified";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String SERVICE_ID = "service_id";

    }
}
