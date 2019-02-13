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
        db.execSQL(createTablePersonal());
        db.execSQL(createTableEvaluation());
        db.execSQL(createTableFacility());
        db.execSQL(createTableVisit());
        db.execSQL(createTableService());
        db.execSQL(createTableSubservice());
        createDataUser(db);
        createDataFacility(db);
        createDataVisit(db);
        createDataService(db);
        createDataSubservice(db);
        createDataPersonal(db);

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

    public long insertTablePersonal(SQLiteDatabase db,Personal personal)
    {
        return db.insert(PersonalContract.PersonalEntry.TABLE_NAME,null,personal.toContentValues());
    }


    public void createDataUser(SQLiteDatabase db)
    {

        if (db != null) {
            insertTableUser(db,new User(1,"Diego Matus","DMatus","12345","12-14-2019","19.007.996-1","diego@gmail.com",
                    "+56984775979",5));
            insertTableUser(db,new User(2,"Ariel Cornejo","ABass","12345","12-14-2019","19.299.833-6","ariel@gmail.com",
                    "+56984775979",5));
            insertTableUser(db,new User(3,"Arturo Torres","ATorres","12345","12-14-2019","19.289.833-6","aturo@gmail.com",
                    "+56984775979",1));
            insertTableUser(db,new User(4,"Matias Quezada","MQuezada","12345","12-14-2019","19.279.833-6","matias@gmail.com",
                    "+56984775979",1));
            insertTableUser(db,new User(5,"Diego Quezada","DQuezada","12345","12-14-2019","19.269.833-6","diegoQ@gmail.com",
                    "+56984775979",1));
        }

    }

    public void createDataFacility(SQLiteDatabase db)
    {
        if(db !=null)
        {
            insertTableFacility(db,new Facility(1,2,"04-02-2019","03 02 015","Mall center","Curico",2));
            insertTableFacility(db,new Facility(2,1,"05-02-2019","46554","Utalca","Los Niches",1));
            insertTableFacility(db,new Facility(3,1,"07-02-2019","4654654","Top Dog","Curico",1));
            insertTableFacility(db,new Facility(4,2,"07-02-2019","464654","Mujica & Docmac Oficina","Curico",2));
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
            inserTableService(db,new Service(1,"78508310","División Aseo - Mujica & Docmac","Aseo",""));
            inserTableService(db,new Service(2,"77327830","División Seguridad Física - Mujica & Docmac","Seguridad 1",""));
            inserTableService(db,new Service(3,"77606450","División Seguridad Física 2 - Mujica & Docmac","Seguridad 2",""));
        }
    }

    public void createDataSubservice(SQLiteDatabase db)
    {
        if(db !=null)
        {
            insertTableSubservice(db,new Subservice(1,"04-02-2019","04-02-2019","rondas","rondas diarias",1));
        }
    }

    public void createDataPersonal(SQLiteDatabase db)
    {
        if(db !=null)
        {
            insertTablePersonal(db,new Personal(1,"Ariel","Cornejo","19.299.833-6","465464","asdas",2,1));
            insertTablePersonal(db, new Personal(2,"Diego","Matus","19.007.996-1","46465","",2,1));
            insertTablePersonal(db,new Personal(3,"Juan","Perez","18.456.203-6","4654654","",3,1));
            insertTablePersonal(db,new Personal(4,"Jose","Acevedo","18.426.203-6","4654654","",3,1));
            insertTablePersonal(db,new Personal(5,"Dylan","Tero","19.299.356-8","a46456","",1,1));
            insertTablePersonal(db,new Personal(6,"Benjamin","Sanhueza","19.626.586-5","64654654","",1,1));
            insertTablePersonal(db,new Personal(7,"Felipe","Ureta","19.741.223-9","64654650","",4,1));
            insertTablePersonal(db,new Personal(8,"Diego","Nuñez","19.987.546-9","5446","",4,1));

        }
    }

    public String createTableUser() {
        return "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
                +UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserContract.UserEntry.ID + " INTEGER NOT NULL, "
                + UserContract.UserEntry.NAME + " TEXT NOT NULL, "
                + UserContract.UserEntry.USERNAME + " TEXT NOT NULL, "
                + UserContract.UserEntry.PASSWORD + " TEXT NOT NULL, "
                + UserContract.UserEntry.CREATED + " TEXT NOT NULL, "
                + UserContract.UserEntry.RUT + " TEXT NOT NULL, "
                + UserContract.UserEntry.EMAIL+ " TEXT, "
                + UserContract.UserEntry.PHONE+ " TEXT, "
                + UserContract.UserEntry.ROLE + " INTEGER NOT NULL, "
                + " UNIQUE (" + UserContract.UserEntry.ID + "))";
    }

    public String createTableFacility()
    {
        return "CREATE TABLE " + FacilityContract.FacilityEntry.TABLE_NAME + " ("
                +FacilityContract.FacilityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FacilityContract.FacilityEntry.ID + " INTEGER NOT NULL, "
                + FacilityContract.FacilityEntry.NAME + " INTEGER NOT NULL, "
                + FacilityContract.FacilityEntry.USER_ID + " INTEGER NOT NULL, "
                + FacilityContract.FacilityEntry.CODE + " TEXT NOT NULL, "
                + FacilityContract.FacilityEntry.CREATED + " TEXT NOT NULL, "
                + FacilityContract.FacilityEntry.ADDRESS + " TEXT NOT NULL, "
                + FacilityContract.FacilityEntry.SERVICE_ID + " INTEGER NOT NULL, "
                + " UNIQUE (" + FacilityContract.FacilityEntry.ID + "),"
                + " FOREIGN KEY(" + FacilityContract.FacilityEntry.USER_ID + ") REFERENCES "+ UserContract.UserEntry.TABLE_NAME +"(" + UserContract.UserEntry.ID+"), "
                + " FOREIGN KEY(" + FacilityContract.FacilityEntry.SERVICE_ID + ") REFERENCES "+ ServiceContract.ServiceEntry.TABLE_NAME +"(" + ServiceContract.ServiceEntry.ID+"))";
    }

    public String createTablePersonal()
    {
        return "CREATE TABLE " + PersonalContract.PersonalEntry.TABLE_NAME + " ("
            //+ PersonalContract.PersonalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalContract.PersonalEntry.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + PersonalContract.PersonalEntry.NAME + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.SURNAME + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.RUT + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.PHONE + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.EMAIL + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.STATE + " INT NOT NULL, "
            + PersonalContract.PersonalEntry.FACILITY_ID + " INTEGER NOT NULL, "
            + " UNIQUE(" + PersonalContract.PersonalEntry.ID + ")," +
                " FOREIGN KEY("+ PersonalContract.PersonalEntry.FACILITY_ID+ ") REFERENCES facility(id))";
    }



    public String createTableEvaluation()
    {
        return "CREATE TABLE " + EvaluationContract.EvaluationEntry.TABLE_NAME+ " ("
                + EvaluationContract.EvaluationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EvaluationContract.EvaluationEntry.ID + " INTEGER NOT NULL, "
                + EvaluationContract.EvaluationEntry.DONE + " TEXT NOT NULL, "
                + EvaluationContract.EvaluationEntry.FACILITY_ID+ " INTEGER NOT NULL, "
                +" UNIQUE("+ EvaluationContract.EvaluationEntry.ID + "), "
                +  " FOREIGN KEY("+ EvaluationContract.EvaluationEntry.FACILITY_ID+ ") REFERENCES facility(id))";
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
