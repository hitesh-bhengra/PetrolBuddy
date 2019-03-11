package com.gl.practice.petrolbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

public class DatabaseHelper extends SQLiteOpenHelper {


    private SQLiteDatabase myDatabase;
    private static final String DATABASE_NAME = "rider.db";
    private static final String TABLE_NAME = "rider_table";
    private Cursor cursor;

    /*
    *   Columns
    */
    public static final String column_id = "ID";
    public static final String column_date = "Date";
    public static final String column_petrol = "Petrol";
    public static final String column_price = "Price";
    public static final String column_km_reading = "km";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        myDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists "+ TABLE_NAME +
                "("+column_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                column_date+" VARCHAR NOT NULL," +
                column_petrol+" REAL NOT NULL," +
                column_price+" REAL NOT NULL," +
                column_km_reading+" REAL NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date,Float petrol, Float price, Float km) {
        myDatabase = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(column_date, date);
        content.put(column_petrol, petrol);
        content.put(column_price, price);
        content.put(column_km_reading, km);

        long result = myDatabase.insert(TABLE_NAME,null,content);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewData() {
        myDatabase = this.getReadableDatabase();
        String query = "Select * from "+TABLE_NAME;
        cursor = myDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getKM() {

        myDatabase = this.getReadableDatabase();
        String query = "Select "+column_km_reading+" from "+TABLE_NAME+
                " order by "+column_km_reading +" DESC";
        cursor = myDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public int getCount() {

        myDatabase = this.getReadableDatabase();
        String query = "Select "+column_id+" from "+TABLE_NAME;
        cursor = myDatabase.rawQuery(query,null);
        return cursor.getCount();
    }
}
