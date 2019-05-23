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
    private TextView date;
    private EditText liftName;
    private EditText weightUsed;
    private EditText numOfSets;
    private EditText numOfReps;
    private boolean everyEditTextIsPopulated;
    private Calendar c;
    private String selectedName;
    private int selectedID;
    private Button doneEditButton;
    DatabaseHelper mDatabaseHelper;
    private Button btnDelete;
    private String selectedLift;
    private String selectedWeight;
    private String selectedSets;
    private String selectedReps;
    private String selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        dateSelectorButton = (Button)findViewById(R.id.dateButtonEdit);
        date = (TextView)findViewById(R.id.dateTextViewEdit);
        liftName = (EditText)findViewById(R.id.liftNameEdit);
        weightUsed = (EditText)findViewById(R.id.weightUsedEdit);
        numOfSets = (EditText)findViewById(R.id.numberOfSetsEdit);
        numOfReps = (EditText)findViewById(R.id.numberofRepsEdit);
        doneEditButton = (Button)findViewById(R.id.workoutCompletedEdit);
        mDatabaseHelper = new DatabaseHelper(this);
        btnDelete = (Button)findViewById(R.id.deleteButton);

        Intent recievedIntent = getIntent();
        selectedID = recievedIntent.getIntExtra("id", -1); //-1 is just default
        selectedLift = recievedIntent.getStringExtra("lift");
        selectedWeight = recievedIntent.getStringExtra("weight");
        selectedSets = recievedIntent.getStringExtra("sets");
        selectedReps = recievedIntent.getStringExtra("reps");
        selectedDate = recievedIntent.getStringExtra("date");

        date.setText(selectedDate);
        liftName.setText(selectedLift);
        weightUsed.setText(selectedWeight);
        numOfSets.setText(selectedSets);
        numOfReps.setText(selectedReps);

        //selectedName = recievedIntent.getStringExtra("name");

        doneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String workout = liftName.getText().toString();

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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteName(selectedID, selectedName);
            }
        });
    }
}
