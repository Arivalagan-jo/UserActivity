package com.j.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.util.Log.e;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public void insertEmp() {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, "emp01");
        contentValue.put(DatabaseHelper.PASSWORD, "123456");
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public void insertActiviesPeriod(String name, String from, String to, String activityType){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.EMP_NAME, name);
        contentValue.put(DatabaseHelper.FROM_TIME, from);
        contentValue.put(DatabaseHelper.TO_TIME, to);
        contentValue.put(DatabaseHelper.ACTIVITY_TYPE, activityType);
        database.insert(DatabaseHelper.USER_ACTIVITIES_LIST, null, contentValue);
    }


    public Cursor getLoginEmp() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME,DatabaseHelper.PASSWORD };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor getUserActivities1() {
        String[] columns = new String[] { "id", "ACTIVITY_NAME" };
        Cursor cursor = database.query(DatabaseHelper.USER_ACTIVITIES, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public List<String> getUserActivities() {
        List<String> notes = new ArrayList<>();
        String[] columns = new String[] { "id", "ACTIVITY_NAME" };
        Cursor cursor = database.query(DatabaseHelper.USER_ACTIVITIES, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                notes.add(cursor.getString(cursor.getColumnIndex("ACTIVITY_NAME")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }


    public List<HashMap<String,String>> getListOfActivities() {

            List<HashMap<String, String>> ativity = new ArrayList<>();
            HashMap<String, String> map = new HashMap<>();
            String[] columns = new String[]{DatabaseHelper.EMP_NAME, DatabaseHelper.FROM_TIME, DatabaseHelper.TO_TIME, DatabaseHelper.ACTIVITY_TYPE};
           try {
               Cursor cursor = database.query(DatabaseHelper.USER_ACTIVITIES_LIST, columns, null, null, null, null, null);
               if (cursor != null) {
                   cursor.moveToFirst();
               }

               // looping through all rows and adding to list
               if (cursor.moveToFirst()) {

                   do {  map = new HashMap<>();
                       map.put(DatabaseHelper.EMP_NAME, cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMP_NAME)));
                       map.put(DatabaseHelper.FROM_TIME, cursor.getString(cursor.getColumnIndex(DatabaseHelper.FROM_TIME)));
                       map.put(DatabaseHelper.TO_TIME, cursor.getString(cursor.getColumnIndex(DatabaseHelper.TO_TIME)));
                       map.put(DatabaseHelper.ACTIVITY_TYPE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACTIVITY_TYPE)));
                       ativity.add(map);
                   } while (cursor.moveToNext());


               }
               cursor.close();
           }catch (Exception e){}


        return ativity;
    }




    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }


}