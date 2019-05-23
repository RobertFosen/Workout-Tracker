package com.fosenapps.workouttracker;

import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SeePreviousWorkoutsActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private static final String TAG = "editing";

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

        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
                Log.i(TAG, "onItemClick: ");
                String workout = adapterView.getItemAtPosition(i).toString();

                int itemID = i + 1;
                Cursor data = mDatabaseHelper.getWorkoutInfo(itemID);


                String liftDone = "", date = "", weightUsed = "", setsDone = "", repsDone = "";
                while(data.moveToNext()) {
                    Log.d(TAG, "onItemClick: " + data.getString(1));
                    itemID = data.getInt(0);
                    liftDone = data.getString(1);
                    weightUsed = data.getString(2);
                    setsDone = data.getString(3);
                    repsDone = data.getString(4);
                    date = data.getString(5);
                }
                if(itemID > -1) {
                    Intent editScreenIntent = new Intent(SeePreviousWorkoutsActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    //editScreenIntent.putExtra("name", name);
                    editScreenIntent.putExtra("lift", liftDone);
                    editScreenIntent.putExtra("weight", weightUsed);
                    editScreenIntent.putExtra("sets", setsDone);
                    editScreenIntent.putExtra("reps", repsDone);
                    editScreenIntent.putExtra("date", date);
                    startActivity(editScreenIntent);
                }
            }
        });
    }

    public void onDoneButtonClick(View v) {
        finish();
    }
}
