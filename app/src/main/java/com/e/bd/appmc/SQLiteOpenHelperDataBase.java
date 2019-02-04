package com.e.bd.appmc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteOpenHelperDataBase extends SQLiteOpenHelper {

    public SQLiteOpenHelperDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUser());
        db.execSQL(createTableFacility());
        db.execSQL(createTableVisit());
        db.execSQL(createTableService());
        db.execSQL(createTableSubservice());
        createDataUser(db);
        createDataFacility(db);
        createDataVisit(db);
        createDataService(db);
        createDataSubservice(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ UserContract.UserEntry.TABLE_NAME);
            onCreate(db);

    }


    public long insertTableUser(SQLiteDatabase db, User user)
    {
        return db.insert(UserContract.UserEntry.TABLE_NAME,null,user.toContentValues());
    }

    public long insertTableFacility(SQLiteDatabase db, Facility facility)
    {
        return db.insert(FacilityContract.FacilityEntry.TABLE_NAME,null,facility.toContentValues());
    }

    public long insertTableVisit(SQLiteDatabase db, Visit visit)
    {
        return db.insert(VisitContract.VisitEntry.TABLE_NAME,null,visit.toContentValues());
    }

    public long inserTableService(SQLiteDatabase db, Service service)
    {
        return db.insert(ServiceContract.ServiceEntry.TABLE_NAME,null,service.toContentValues());
    }

    public long insertTableSubservice(SQLiteDatabase db, Subservice subservice)
    {
        return db.insert(SubserviceContract.SubserviceEntry.TABLE_NAME,null,subservice.toContentValues());
    }


    public void createDataUser(SQLiteDatabase db)
    {

        if (db != null) {
            insertTableUser(db,new User(1,"Diego Matus","DMatus","12345","12-14-2019","19.007.996-1","diego@gmail.com",
                    "+56984775979"));
            insertTableUser(db,new User(2,"Ariel Cornejo","ABass","12345","12-14-2019","19.299.833-6","ariel@gmail.com",
                    "+56984775979"));
            insertTableUser(db,new User(3,"Arturo Torres","ATorres","12345","12-14-2019","19.289.833-6","aturo@gmail.com",
                    "+56984775979"));
            insertTableUser(db,new User(4,"Matias Quezada","MQuezada","12345","12-14-2019","19.279.833-6","matias@gmail.com",
                    "+56984775979"));
            insertTableUser(db,new User(5,"Diego Quezada","DQuezada","12345","12-14-2019","19.269.833-6","diegoQ@gmail.com",
                    "+56984775979"));
        }

    }

    public void createDataFacility(SQLiteDatabase db)
    {
        if(db !=null)
        {
            insertTableFacility(db,new Facility(1,2,"04-02-2019","03 02 015","Mall center","Curico"));
        }
    }

    public void createDataVisit(SQLiteDatabase db)
    {
        if(db !=null)
        {
            insertTableVisit(db,new Visit(1,1,2,"04-02-2019 11:00AM","04-02-2019 12:00PM","prueba"));
        }
    }

    public void createDataService(SQLiteDatabase db)
    {
        if(db !=null)
        {
            inserTableService(db,new Service(1,"31321","Seguridad","5464",""));
        }
    }

    public void createDataSubservice(SQLiteDatabase db)
    {
        if(db !=null)
        {
            insertTableSubservice(db,new Subservice(1,"04-02-2019","04-02-2019","rondas","rondas diarias",1));
        }
    }

    public String createTableUser() {
        return "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
                +UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserContract.UserEntry.ID + " INTEGER NOT NULL, "
                + UserContract.UserEntry.NAME + " TEXT NOT NULL, "
                + UserContract.UserEntry.USERNAME + " TEXT NOT NULL, "
                + UserContract.UserEntry.PASSWORD + " TEXT NOT NULL, "
                + UserContract.UserEntry.CREATED + " TEXT NOT NULL, "
                + UserContract.UserEntry.RUT + " TEXT NOT NULL, "
                + UserContract.UserEntry.EMAIL+ " TEXT, "
                + UserContract.UserEntry.PHONE+ " TEXT, "
                + " UNIQUE (" + UserContract.UserEntry.ID + "))";
    }

    public String createTableFacility()
    {
        return "CREATE TABLE " + FacilityContract.FacilityEntry.TABLE_NAME + " ("
                +FacilityContract.FacilityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FacilityContract.FacilityEntry.ID + " INTEGER NOT NULL, "
                + FacilityContract.FacilityEntry.NAME + " INTEGER NOT NULL, "
                + FacilityContract.FacilityEntry.USER_ID + " TEXT NOT NULL, "
                + FacilityContract.FacilityEntry.CODE + " TEXT NOT NULL, "
                + FacilityContract.FacilityEntry.CREATED + " TEXT NOT NULL, "
                + FacilityContract.FacilityEntry.ADDRESS + " TEXT NOT NULL, "
                + " UNIQUE (" + FacilityContract.FacilityEntry.ID + "),"
                + " FOREIGN KEY(" + FacilityContract.FacilityEntry.USER_ID + ") REFERENCES "+ UserContract.UserEntry.TABLE_NAME +"(" + UserContract.UserEntry.ID+"))";
    }

    public String createTableVisit()
    {
        return "CREATE TABLE " + VisitContract.VisitEntry.TABLE_NAME +" ("
                + VisitContract.VisitEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VisitContract.VisitEntry.ID + " INTEGER NOT NULL, "
                + VisitContract.VisitEntry.USER_ID + " INTEGER NOT NULL, "
                + VisitContract.VisitEntry.FACILITY_ID + " INTEGER NOT NULL, "
                + VisitContract.VisitEntry.ENTER + " TEXT NOT NULL, "
                + VisitContract.VisitEntry.EXIT + " TEXT NOT NULL, "
                + VisitContract.VisitEntry.COMMENT + " TEXT NOT NULL, "
                + " UNIQUE (" + VisitContract.VisitEntry.ID + "),"
                + " FOREIGN KEY(" + VisitContract.VisitEntry.USER_ID + ") REFERENCES "+ UserContract.UserEntry.TABLE_NAME +"(" + UserContract.UserEntry.ID+"),"
                + " FOREIGN KEY(" + VisitContract.VisitEntry.FACILITY_ID + ") REFERENCES "+ FacilityContract.FacilityEntry.TABLE_NAME +"(" + FacilityContract.FacilityEntry.ID+"))";
    }

    public String createTableService()
    {
        return "CREATE TABLE " + ServiceContract.ServiceEntry.TABLE_NAME +" ("
                + ServiceContract.ServiceEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ServiceContract.ServiceEntry.ID + " INTEGER NOT NULL, "
                + ServiceContract.ServiceEntry.NAME + " TEXT NOT NULL, "
                + ServiceContract.ServiceEntry.CODE + " TEXT NOT NULL,"
                + ServiceContract.ServiceEntry.IDENTIFIER + " TEXT NOT NULL, "
                + ServiceContract.ServiceEntry.ICON + " TEXT, "
                + " UNIQUE (" + ServiceContract.ServiceEntry.ID +"))";
    }

    public String createTableSubservice()
    {
        return "CREATE TABLE " + SubserviceContract.SubserviceEntry.TABLE_NAME +" ("
                + SubserviceContract.SubserviceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SubserviceContract.SubserviceEntry.ID + " INTEGER NOT NULL, "
                + SubserviceContract.SubserviceEntry.NAME + " TEXT NOT NULL, "
                + SubserviceContract.SubserviceEntry.CREATED + " TEXT NOT NULL, "
                + SubserviceContract.SubserviceEntry.MODIFIED + " TEXT NOT NULL, "
                + SubserviceContract.SubserviceEntry.DESCRIPTION + " TEXT NOT NULL,"
                + SubserviceContract.SubserviceEntry.SERVICE_ID + " INTEGER NOT NULL, "
                + " UNIQUE (" + SubserviceContract.SubserviceEntry.ID + "), "
                + " FOREIGN KEY(" + ServiceContract.ServiceEntry.ID +") REFERENCES " + ServiceContract.ServiceEntry.TABLE_NAME + "(" + ServiceContract.ServiceEntry.ID+"))";

    }

    public Cursor doSelectQuery(String query)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
}
