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
        createDataUser(db);

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
                + " UNIQUE (" + UserContract.UserEntry.ID + "))";
    }


    public String createTablePersonal()
    {
        return "CREATE TABLE " + PersonalContract.PersonalEntry.TABLE_NAME + " ("
            + PersonalContract.PersonalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalContract.PersonalEntry.ID + " INTEGER NOT NULL, "
            + PersonalContract.PersonalEntry.NAME + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.SURNAME + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.RUT + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.PHONE + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.EMAIL + " TEXT NOT NULL, "
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





    public Cursor doSelectQuery(String query)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
}
