package com.e.appmc.bd;

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
        db.execSQL(createTableAspect());
        db.execSQL(createTableQuestion());
        db.execSQL(createTablePoint());
        db.execSQL(createTableSummary());
        db.execSQL(createTableResponseQuestion());
        db.execSQL(createTableResponseEvaluation());
        createDataUser(db);
        //createDataFacility(db);
        createDataVisit(db);
        createDataService(db);
        createDataSubservice(db);
        createDataPersonal(db);
        createDataEvaluation(db);
        createDataQuestion(db);
        createDataPoint(db);



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

    public long insertTableResponseQuestion(SQLiteDatabase db, ResponseQuestion response)
    {
        return db.insert(ResponseQuestionContract.ResponseQuestionEntry.TABLE_NAME,null,response.toContentValues());
    }

    public long insertTableResponseEvaluation(SQLiteDatabase db, ResponseEvaluation response)
    {
        return db.insert(ResponseEvaluationContract.ResponseEvaluationEntry.TABLE_NAME,null,response.toContentValues());
    }

    public long insertTableFacility(SQLiteDatabase db, Facility facility)
    {
        return db.insert(FacilityContract.FacilityEntry.TABLE_NAME,null,facility.toContentValues());
    }

    public long insertTableAspect(SQLiteDatabase db, Aspect aspect)
    {
        return db.insert(AspectContract.AspectEntry.TABLE_NAME,null,aspect.toContentValues());
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

    public long inserTableSummary(SQLiteDatabase db, Summary summary)
    {
        return db.insert(SummaryContract.SummaryEntry.TABLE_NAME,null,summary.toContentValue());
    }

    public long insertTableQuestion(SQLiteDatabase db, Question question)
    {
        return db.insert(QuestionContract.questionEntry.TABLE_NAME,null,question.toContentValues());
    }

    public long insertEvaluationQuestion(SQLiteDatabase db, EvaluationQuestion evaluationQuestion)
    {
        return db.insert(EvaluationQuestionContract.evaluationQuestionEntry.TABLE_NAME,null,evaluationQuestion.toContentValues());
    }

    public long insertarEvaluation(SQLiteDatabase db,Evaluation evaluation)
    {
        return db.insert(EvaluationContract.EvaluationEntry.TABLE_NAME,null,evaluation.toContentValues());
    }

    public long insertarPoint(SQLiteDatabase db, Point point)
    {
        return db.insert(PointContract.pointEntry.TABLE_NAME,null,point.toContentValues());
    }


    public void createDataPoint(SQLiteDatabase db)
    {
        insertarPoint(db, new Point(1,"Operativo"));
        insertarPoint(db, new Point(2,"Recursos Humanos"));
        insertarPoint(db, new Point(3,"Prevención de Riesgo"));
        insertarPoint(db,new Point(4, "Vestimenta y Presentación Física"));
        insertarPoint(db, new Point(5,"Apoyo"));
        insertarPoint(db, new Point(6,"Porteria"));
        insertarPoint(db, new Point(7,"Ronda de Guardias"));
        insertarPoint(db, new Point(8,"Portalón"));
        insertarPoint(db, new Point(9,"Ronda de guardias-Mall"));
    }

    public void createDataEvaluation(SQLiteDatabase db)
    {
        insertarEvaluation(db,new Evaluation(1,"12-02-2019",1));
        insertarEvaluation(db,new Evaluation(2,"13-02-2019",1));
        insertarEvaluation(db,new Evaluation(3,"13-02-2019",1));

    }


    public void createDataUser(SQLiteDatabase db)
    {

        if (db != null) {
            insertTableUser(db,new User(1,"Diego Matus","DMatus","12345","12-14-2019","19.007.996-1","diego@gmail.com",
                    "+56984775979",5));
            insertTableUser(db,new User(9,"Ariel Cornejo","ABass","12345","12-14-2019","19.299.833-6","ariel@gmail.com",
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
            insertTableFacility(db,new Facility(1,2,"04-02-2019","03 02 015","Mall center","Curico",2,1,"no"));
            insertTableFacility(db,new Facility(2,1,"05-02-2019","46554","Utalca","Los Niches",1,1,"no"));
            insertTableFacility(db,new Facility(3,1,"07-02-2019","4654654","Top Dog","Curico",1,1,"no"));
            insertTableFacility(db,new Facility(4,2,"07-02-2019","464654","Mujica & Docmac Oficina","Curico",2,1,"no"));
        }
    }

    public void createDataQuestion(SQLiteDatabase db)
    {

        if(db !=null) {
            insertTableQuestion(db, new Question(1, "¿Cuentan todos con ficha de ingreso?", 100, 1, 1,1,1));
            insertTableQuestion(db, new Question(2, "¿Cuentan todos con curriculum vitae?", 100, 1, 1,1,1));
            insertTableQuestion(db, new Question(3, "¿Cuentan todos con certificado de antecedentes?", 100, 1, 1,1,1));
            insertTableQuestion(db, new Question(4, "¿Cuentan todos con certificado de afiliación AFP?", 100, 1, 1,1,1));
            insertTableQuestion(db, new Question(5,"¿Cuentan todos con certificado de afiliación FONASA O ISAPRE?",100,1,1,1,1));
            insertTableQuestion(db, new Question(6,"¿Cuentan todos con curso OS-10 vigente?",100,1,1,1,1));
            insertTableQuestion(db, new Question(7,"¿Cuentan todos con copia entrega de credencial o solicitud de acreditación?",100,1,1,1,1));
            insertTableQuestion(db, new Question(8,"¿Cuentan  con la resolución aprobación de directiva?",100,2,1,1,1));
            insertTableQuestion(db, new Question(9,"¿Cuentan con la nómina actualizada?",100,2,1,1,1));
            insertTableQuestion(db, new Question(10,"¿Cuentan todos los guardias con seguro de vida?",100,1,1,1,1));
            insertTableQuestion(db, new Question(11,"¿Cuentan con el certificado de empresa prestadora de RRHH actualizada?",100,2,1,1,1));
            insertTableQuestion(db, new Question(12,"¿Cuentan con la resolución aprobación Acuerdos Marcos?",100,2,1,1,1));

            insertTableQuestion(db, new Question(13,"¿Cuentan todos con la copia del contrato?",100,1,1,2,1));
            insertTableQuestion(db, new Question(14,"¿Cuentan todos con la copia de los comprobantes de remuneración?",100,1,1,2,1));
            insertTableQuestion(db, new Question(15,"¿Cuentan todos con la copia de comprobantes de pago y cotizaciones?",100,1,1,2,1));
            insertTableQuestion(db, new Question(16,"¿Cuentan con el certificado de centralización de documentos?",100,2,1,2,1));
            insertTableQuestion(db, new Question(17,"¿Cuentan todos con los anexos de contrato?",100,1,1,2,1));

            insertTableQuestion(db, new Question(18,"¿Cuentan todos con OIR Cargo?",100,1,1,3,1));
            insertTableQuestion(db, new Question(19,"¿Cuentan todos con OIR Protocolo Prexor e implementación MINSAL?",100,1,1,3,1));
            insertTableQuestion(db, new Question(20,"¿Cuentan todos con OIR Protocolo Sílice e implementación MINSAL?",100,1,1,3,1));
            insertTableQuestion(db, new Question(21,"¿Cuentan todos con OIR Protocolo Rad UV e implementación MINSAL?",100,1,1,3,1));
            insertTableQuestion(db, new Question(22,"¿Cuentan todos con OIR Protocolo TMERT e implementación MINSAL?",100,1,1,3,1));
            insertTableQuestion(db, new Question(23,"¿Cuentan todos con OIR Protocolo Psicosocial e implementación MINSAL?",100,1,1,3,1));
            insertTableQuestion(db, new Question(24,"¿Cuentan todos con OIR Protocolo MMC e implementación MINSAL?",100,1,1,3,1));
            insertTableQuestion(db, new Question(25,"¿Cuentan todos con el registro de inducción DS 40 - art. 21?",100,1,1,3,1));
            insertTableQuestion(db, new Question(26,"¿Cuentan todos con la hoja de cargo y entrega EPP?",100,1,1,3,1));
            insertTableQuestion(db, new Question(27,"¿Cuentan todos con el registro de entrega de procedimientos de trabajo seguro?",100,1,1,3,1));
            insertTableQuestion(db, new Question(28,"¿Cuentan todos el Acta entrega de RIOHS?",100,1,1,3,1));
            insertTableQuestion(db, new Question(29,"¿Cuentan todos el registro de capacitaci\"Inducción Hombre Nuevo\"?",100,1,1,3,1));



            insertTableQuestion(db, new Question(30,"Mantiene postura corporal y presentación acorde al servicio",80,0,2,4,2));
            insertTableQuestion(db, new Question(31,"Porta credencial de OS-10 o(existe registro de solicitud)",80,0,2,4,2));
            insertTableQuestion(db, new Question(32,"Utilización de uniforme completo",80,0,2,4,2));
            insertTableQuestion(db, new Question(33,"Uniforme empresa limpio y en buen estado",80,0,2,4,2));
            insertTableQuestion(db, new Question(34,"Utilización y estado de elementos de protección personal (EPP)",80,0,2,4,2));
            insertTableQuestion(db, new Question(35,"Zapatos de seguridad, en buenas condiciones y limpios",80,0,2,4,2));

            insertTableQuestion(db, new Question(36,"Captor",80,0,3,1,3));
            insertTableQuestion(db, new Question(37,"Radio",80,0,3,1,3));
            insertTableQuestion(db, new Question(38,"Linterna",80,0,3,1,3));
            insertTableQuestion(db, new Question(39,"Equipo Telefonico MD",80,0,3,1,3));
            insertTableQuestion(db, new Question(40,"Hervidor",80,0,3,5,3));
            insertTableQuestion(db, new Question(41,"Calefactor",80,0,3,5,3));
            insertTableQuestion(db, new Question(42,"Articulos de oficina",80,0,3,5,3));
            insertTableQuestion(db, new Question(43,"Otros",80,0,3,5,3));


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
            insertTablePersonal(db,new Personal(1,"Ariel","Cornejo","19.299.833-6","465464","asdas",2,1,"14-02-2019","no"));
            insertTablePersonal(db, new Personal(2,"Diego","Matus","19.007.996-1","46465","",2,1,"14-02-2019","no"));
            insertTablePersonal(db,new Personal(3,"Juan","Perez","18.456.203-6","4654654","",3,1,"14-02-2019","no"));
            insertTablePersonal(db,new Personal(4,"Jose","Acevedo","18.426.203-6","4654654","",3,1,"14-02-2019","no"));
            insertTablePersonal(db,new Personal(5,"Dylan","Tero","19.299.356-8","a46456","",1,1,"14-02-2019","no"));
            insertTablePersonal(db,new Personal(6,"Benjamin","Sanhueza","19.626.586-5","64654654","",1,1,"14-02-2019","no"));
            insertTablePersonal(db,new Personal(7,"Felipe","Ureta","19.741.223-9","64654650","",4,1,"14-02-2019","no"));
            insertTablePersonal(db,new Personal(8,"Diego","Nuñez","19.987.546-9","5446","",4,1,"14-02-2019","no"));

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
                + FacilityContract.FacilityEntry.EVALUATION_ID + " INTEGER NOT NULL, "
                + FacilityContract.FacilityEntry.SYNC_STATUS + " TEXT NOT NULL, "
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
            + PersonalContract.PersonalEntry.STATE + " INTEGER NOT NULL, "
            + PersonalContract.PersonalEntry.CREATED + " TEXT NOT NULL, "
            + PersonalContract.PersonalEntry.FACILITY_ID + " INTEGER NOT NULL, "
            + PersonalContract.PersonalEntry.SYNC_STATE + " TEXT NOT NULL, "
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

    public String createTableQuestion()
    {
        return "CREATE TABLE " + QuestionContract.questionEntry.TABLE_NAME+ " ("
                + QuestionContract.questionEntry._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + QuestionContract.questionEntry.ID+ " INTEGER NOT NULL, "
                + QuestionContract.questionEntry.DESCRIPTION + " TEXT NOT NULL, "
                + QuestionContract.questionEntry.APROVAL_PORCENTAGE + " TEXT NOT NULL, "
                + QuestionContract.questionEntry.TYPE+ " TEXT NOT NULL,"
                + QuestionContract.questionEntry.ASPECT_ID + " TEXT NOT NULL, "
                + QuestionContract.questionEntry.POINT_ID + " INTEGER NOT NULL, "
                + QuestionContract.questionEntry.EVALUATION_ID +" INTEGER NOT NULL, "
                + " FOREIGN KEY(" + QuestionContract.questionEntry.POINT_ID +") REFERENCES " + PointContract.pointEntry.TABLE_NAME + "(" + PointContract.pointEntry.ID+")"
                + "UNIQUE("+ QuestionContract.questionEntry.ID+"))";

    }

    public String createTablePoint()
    {
        return "CREATE TABLE "+ PointContract.pointEntry.TABLE_NAME+" ("
                + PointContract.pointEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PointContract.pointEntry.ID + " INTEGER NOT NULL, "
                + PointContract.pointEntry.NAME+ " TEXT NOT NULL, "
                + "UNIQUE ("+PointContract.pointEntry.ID+"))";

    }

    public String createTableAspect()
    {
       return "CREATE TABLE "+ AspectContract.AspectEntry.TABLE_NAME+" ("
                + AspectContract.AspectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AspectContract.AspectEntry.ID + " INTEGER NOT NULL, "
                + AspectContract.AspectEntry.NAME+ " TEXT NOT NULL, "
               + AspectContract.AspectEntry.CREATED+ " TEXT NOT NULL, "
               + AspectContract.AspectEntry.APROVAL_PORCENTAGE+ " REAL NOT NULL, "
                + "UNIQUE ("+PointContract.pointEntry.ID+"))";
    }
    public String createTableSummary()
    {
        return "CREATE TABLE " + SummaryContract.SummaryEntry.TABLE_NAME +" ("
                + SummaryContract.SummaryEntry.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SummaryContract.SummaryEntry.FACILITY_ID +" INTEGER NOT NULL, "
                + SummaryContract.SummaryEntry.CONTENT + " TEXT NOT NULL, "
                + SummaryContract.SummaryEntry.DATE + " TEXT NOT NULL, "
                + SummaryContract.SummaryEntry.SYNC_STATE + " TEXT NOT NULL, "
                + " FOREIGN KEY(" + SummaryContract.SummaryEntry.FACILITY_ID +") REFERENCES " + FacilityContract.FacilityEntry.TABLE_NAME + "(" + FacilityContract.FacilityEntry.ID+")"
                + "UNIQUE ("+SummaryContract.SummaryEntry.ID+"))";
    }

    public String createTableResponseQuestion()
    {
        return "CREATE TABLE " + ResponseQuestionContract.ResponseQuestionEntry.TABLE_NAME +" ("
                + ResponseQuestionContract.ResponseQuestionEntry.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ResponseQuestionContract.ResponseQuestionEntry.ID_EVALUATION +" INTEGER NOT NULL, "
                + ResponseQuestionContract.ResponseQuestionEntry.ID_QUESTION + " TEXT NOT NULL, "
                + ResponseQuestionContract.ResponseQuestionEntry.ASSESSMENT + " TEXT NOT NULL, "
                + ResponseQuestionContract.ResponseQuestionEntry.SYNC_STATE + " TEXT NOT NULL, "
                + " FOREIGN KEY(" + ResponseQuestionContract.ResponseQuestionEntry.ID_EVALUATION +") REFERENCES " + EvaluationContract.EvaluationEntry.TABLE_NAME + "(" + EvaluationContract.EvaluationEntry.ID+")"
                +   " FOREIGN KEY(" + ResponseQuestionContract.ResponseQuestionEntry.ID_QUESTION +") REFERENCES " + QuestionContract.questionEntry.TABLE_NAME + "(" + QuestionContract.questionEntry.ID+")"
                + "UNIQUE ("+ ResponseQuestionContract.ResponseQuestionEntry.ID+"))";
    }


    public String createTableResponseEvaluation()
    {
        return "CREATE TABLE " + ResponseEvaluationContract.ResponseEvaluationEntry.TABLE_NAME +" ("
                + ResponseEvaluationContract.ResponseEvaluationEntry.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ResponseEvaluationContract.ResponseEvaluationEntry.ID_EVALUATION +" INTEGER NOT NULL, "
                + ResponseEvaluationContract.ResponseEvaluationEntry.ASSESSMENT + " TEXT NOT NULL, "
                + ResponseEvaluationContract.ResponseEvaluationEntry.SYNC_STATE + " TEXT NOT NULL, "
                + " FOREIGN KEY(" + ResponseEvaluationContract.ResponseEvaluationEntry.ID_EVALUATION +") REFERENCES " + EvaluationContract.EvaluationEntry.TABLE_NAME + "(" + EvaluationContract.EvaluationEntry.ID+")"
                + "UNIQUE ("+ ResponseEvaluationContract.ResponseEvaluationEntry.ID+"))";
    }


    public Cursor doSelectQuery(String query)

    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,
                null);
        return cursor;
    }



}
