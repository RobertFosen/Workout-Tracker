package com.example.workouttracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SeePreviousWorkoutsActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        mListView = (ListView)findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        //calls the function that lists all of the data in the database
        populateListView();
    }

    private void populateListView() {

        //cursor which holds the data from the DB and arraylist which gets the string value from each item in the data cursor
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();

        //adds the data from data to listdata while there is still unadded data within data ( :P )
        while (data.moveToNext()){
            listData.add(data.getString(1) +  "    (" + data.getString(3) + " x " + data.getString(4) + ") @ " + data.getString(2) + "LBS" + "     " + data.getString(5));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }

    public void onDoneButtonClick(View v) {
        finish();
    }
}
