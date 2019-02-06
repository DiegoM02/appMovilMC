package com.e.bd.appmc;

import android.provider.BaseColumns;

public class FacilityContract {
    public static abstract class FacilityEntry implements BaseColumns {
        public static final String TABLE_NAME ="facility";

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String CREATED = "created";
        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String SERVICE_ID ="service_id";

    }
}
