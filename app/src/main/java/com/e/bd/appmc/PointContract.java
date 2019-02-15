package com.e.bd.appmc;

import android.provider.BaseColumns;


public class PointContract {
    public static abstract class pointEntry implements BaseColumns{
        public static final String TABLE_NAME = "point";
        public static final String ID = "id";
        public static final String NAME = "name";
    }
}
