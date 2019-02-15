package com.e.bd.appmc;

import android.provider.BaseColumns;

public class PersonalContract {

    public static abstract class PersonalEntry implements BaseColumns {
        public static final String TABLE_NAME = "personal";
        public static final String ID = "id";
        public  static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String RUT = "rut";
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String FACILITY_ID = "facility_id";
        public static final String STATE ="state";
        public static final String CREATED = "created";

    }
}
