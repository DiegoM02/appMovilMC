package com.e.bd.appmc;

import android.provider.BaseColumns;

public class ServiceContract
{
    public static abstract class ServiceEntry implements BaseColumns {
        public static final String TABLE_NAME ="service";

        public static final String ID = "id";
        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String IDENTIFIER = "identifier";
        public static final String ICON = "icon";

    }
}
