package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.annotation.Target;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "workouts_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "lift_done";
    private static final String COL3 = "weight_used";
    private static final String COL4 = "sets_done";
    private static final String COL5 = "reps_done";
    private static final String COL6 = "date";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    //creates the table the data will be stored in
    //sets the data type that each column will hold
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " INTEGER, " + COL4 + " INTEGER, " + COL5 + " INTEGER, " + COL6 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //function that places the data in the DB
    public boolean addData(String liftDone, int weightUsed, int sets_done, int reps_done, String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, liftDone);
        contentValues.put(COL3, weightUsed);
        contentValues.put(COL4, sets_done);
        contentValues.put(COL5, reps_done);
        contentValues.put(COL6, date);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data is inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //creates the cursor to hold the data, runs the query which pulls everything from the table, then returns it
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
