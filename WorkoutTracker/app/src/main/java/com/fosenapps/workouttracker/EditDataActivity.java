package com.fosenapps.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class EditDataActivity extends AppCompatActivity {

    private Button dateSelectorButton;
    private Button btnDelete;
    private Button btnBack;
    private Button doneEditButton;

    private TextView date;
    private EditText liftName;
    private EditText weightUsed;
    private EditText numOfSets;
    private EditText numOfReps;

    private int selectedID;
    private boolean everyEditTextIsPopulated;
    private String selectedLift;
    private String selectedWeight;
    private String selectedSets;
    private String selectedReps;
    private String selectedDate;
    //private String selectedName;

    private Calendar c;
    DatabaseHelper mDatabaseHelper;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        btnDelete = (Button)findViewById(R.id.deleteButton);
        btnBack = (Button)findViewById(R.id.backButtonEdit);
        doneEditButton = (Button)findViewById(R.id.workoutCompletedEdit);
        dateSelectorButton = (Button)findViewById(R.id.dateButtonEdit);
        date = (TextView)findViewById(R.id.dateTextViewEdit);
        liftName = (EditText)findViewById(R.id.liftNameEdit);
        weightUsed = (EditText)findViewById(R.id.weightUsedEdit);
        numOfSets = (EditText)findViewById(R.id.numberOfSetsEdit);
        numOfReps = (EditText)findViewById(R.id.numberofRepsEdit);
        mDatabaseHelper = new DatabaseHelper(this);

        //sets values for variables from intents passed from previous activity
        Intent recievedIntent = getIntent();
        selectedID = recievedIntent.getIntExtra("id", -1); //-1 is just default
        selectedLift = recievedIntent.getStringExtra("lift");
        selectedWeight = recievedIntent.getStringExtra("weight");
        selectedSets = recievedIntent.getStringExtra("sets");
        selectedReps = recievedIntent.getStringExtra("reps");
        selectedDate = recievedIntent.getStringExtra("date");

        //sets the edittext values according to intents
        date.setText(selectedDate);
        liftName.setText(selectedLift);
        weightUsed.setText(selectedWeight);
        numOfSets.setText(selectedSets);
        numOfReps.setText(selectedReps);

        //selectedName = recievedIntent.getStringExtra("name");

        //sets done button that checks each field is full, runs query to update the table, then finishes activity
        doneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                everyEditTextIsPopulated = true;

                if (liftName.getText().toString() == "" || weightUsed.getText().toString() == "" || numOfSets.getText().toString() == "" || numOfReps.getText().toString() == "" || date.getText().toString() == "") {
                    everyEditTextIsPopulated = false;
                }

                if(everyEditTextIsPopulated) {
                    mDatabaseHelper.updateWorkout(Integer.toString(selectedID), liftName.getText().toString(), weightUsed.getText().toString(), numOfSets.getText().toString(), numOfReps.getText().toString(), date.getText().toString());
                }
                finish();
            }

        });

        //sets delete button clicker that runs query to delete row in table then finishes activity
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteName(Integer.toString(selectedID));
                finish();
            }
        });

        //sets back button that finishes activity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
