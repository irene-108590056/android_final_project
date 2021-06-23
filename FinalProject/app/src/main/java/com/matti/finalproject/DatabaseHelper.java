package com.matti.finalproject;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * DATABASE_NAME is basically a "document name".
 * TABLE_NAME is the name of the tables we are referring to.
 * COl# is for storing all the data.
 * refer:  https://github.com/mitchtabian/SQLiteSaveUserData
 * https://www.youtube.com/watch?v=sK15YvRIdqY
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    public static final String TABLE_NAME = "markers";
    public static final String COL1 = "TITLE";
    public static final String COL2 = "SNIPPET";
    public static final String COL3 = "LATITUDE";
    public static final String COL4 = "LONGITUDE";
    public static final String COL5 = "MODE";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = " CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TITLE TEXT, SNIPPET TEXT, LATITUDE TEXT , LONGITUDE TEXT, MODE TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addData(String title, String snippet, Double latitude, Double longitude, String mode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,title);
        contentValues.put(COL2,snippet);
        String string1 = String.valueOf(Double.doubleToRawLongBits(latitude));
        contentValues.put(COL3, string1);
        String string2 = String.valueOf(Double.doubleToRawLongBits(longitude));
        contentValues.put(COL4,string2);
        contentValues.put(COL5,mode);

        long result  = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public int getIDbyTitle(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE TITLE LIKE '%" + title +"%'", null);
        data.moveToFirst();
        int returning = data.getInt(0);
        return returning;
    }

    public int getData (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id+"", null);
        data.moveToFirst();
        int returning = data.getInt(0);
        return returning;
    }

    public boolean checkID (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id + "", null);
        if (data.moveToFirst()) {return true;}
        else{return false;}
    }


    public String getTitle (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id+"", null);
        data.moveToFirst();
        String returning = data.getString(1);
        return returning;
    }

    public String getSnippet (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id+"", null);
        data.moveToFirst();
        String returning = data.getString(2);
        return returning;
    }

    public Double getLat (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id+"", null);
        data.moveToFirst();
        String string = data.getString(3);
        Double returning = Double.longBitsToDouble(Long.parseLong(string));
        return returning;
    }

    public Double getLong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id+"", null);
        data.moveToFirst();
        String string = data.getString(4);
        Double returning = Double.longBitsToDouble(Long.parseLong(string));
        return returning;
    }

    public String getMode (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id+"", null);
        data.moveToFirst();
        String returning = data.getString(5);
        return returning;
    }

    public boolean updateData(int id, String title, String snippet, Double latitude, Double longitude, String mode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,title);
        contentValues.put(COL2,snippet);
        String string1 = String.valueOf(Double.doubleToRawLongBits(latitude));
        contentValues.put(COL3, string1);
        String string2 = String.valueOf(Double.doubleToRawLongBits(longitude));
        contentValues.put(COL4,string2);
        contentValues.put(COL5,mode);
        long result  = db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {String.valueOf(id)});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result  = db.delete(TABLE_NAME, "ID = ?", new String[] {String.valueOf(id)});

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

}

