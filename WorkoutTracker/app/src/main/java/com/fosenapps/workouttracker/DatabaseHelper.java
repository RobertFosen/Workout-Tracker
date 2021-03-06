package com.fosenapps.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "workouts_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "lift_done";
    private static final String COL3 = "weight_used";
    private static final String COL4 = "sets_done";
    private static final String COL5 = "reps_done";
    private static final String COL6 = "date";

    private static final String TAG = "editing";



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

        //adds the data of each values into the specific columns
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //this line here sets the ID to the next ID depending on how many elements are in the table.  Otherwise after something
        //is deleted, it will continue incrimenting the ID as if that element was still in the table which causes issues with
        //the way that rows are selected in order to edit or delete them.
        contentValues.put(COL1, DatabaseUtils.queryNumEntries(db, TABLE_NAME) + 1);
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


    //call that gets the values of each column in a row
    public Cursor getWorkoutInfo(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + i + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //updates the values in each column in a table
    public void updateWorkout(String id, String newLift, String newWeight, String newSets, String newReps, String newDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " +
                COL2 + " = '" + newLift + "'," +
                " " + COL3 + " = '" + newWeight + "'," +
                " " + COL4 + " = '" + newSets + "'," +
                " " + COL5 + " = '" + newReps + "'," +
                " " + COL6 + " = '" + newDate + "' " +

                "WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);
    }
    
    public void deleteName(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //query that deletes element from the table
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);

        long rows = DatabaseUtils.queryNumEntries(db, TABLE_NAME);

        //for loop that subtracts one from the ID of all the rows that come after the element that got deleted in order
        //to keep a continuous stream of IDs in order.
        for (int i = Integer.parseInt(id) + 1; i <= rows + 1; i++) {
            query = "UPDATE " + TABLE_NAME + " SET " + COL1 + " = '" + (i-1) + "' " + "WHERE " + COL1 + " = '" + i + "'";
            db.execSQL(query);
        }
    }
}
