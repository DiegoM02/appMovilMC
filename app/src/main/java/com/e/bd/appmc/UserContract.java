package com.e.bd.appmc;

import android.provider.BaseColumns;

public class UserContract {

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME ="user";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String CREATED = "created";
        public static final String RUT = "rut";
        public static final String EMAIL = "email";
        public static final String PHONE= "phone";

    }

}
