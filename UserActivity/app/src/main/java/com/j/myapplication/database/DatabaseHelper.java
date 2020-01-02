package com.j.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "EMPLOYEE";
    public static final String USER_ACTIVITIES = "USER_ACTIVITIES";
    public static final String USER_ACTIVITIES_LIST = "USER_ACTIVITIES_LIST";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "NAME";
    public static final String PASSWORD = "PASSWORD";

    //Activites
    public static final String ID = "id";
    public static final String EMP_NAME = "EMP_NAME";
    public static final String FROM_TIME = "FROM_TIME";
    public static final String TO_TIME = "TO_TIME";
    public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";

    // Database Information
    static final String DB_NAME = "useractivities.db";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + PASSWORD + " TEXT);";

    private static final String ACTIVITIES = "create table "+USER_ACTIVITIES+" (id TEXT, ACTIVITY_NAME TEXT NOT NULL)";
    private static final String INSERT_ACTIVITIES ="INSERT INTO " +
            ""+USER_ACTIVITIES+" (id,ACTIVITY_NAME) values ('1','Yoga'),('2','Gym'),('3','Work Time'),('4','Jogging ')";

    private static final String USER_ACTIVITY_LIST = "create table "+USER_ACTIVITIES_LIST+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, EMP_NAME TEXT NOT NULL, FROM_TIME TEXT,TO_TIME TEXT,ACTIVITY_TYPE TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(ACTIVITIES);
        db.execSQL(INSERT_ACTIVITIES);
        db.execSQL(USER_ACTIVITY_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_ACTIVITIES);
       // db.execSQL("DROP TABLE IF EXISTS " + USER_ACTIVITY_LIST);

        onCreate(db);
    }
}