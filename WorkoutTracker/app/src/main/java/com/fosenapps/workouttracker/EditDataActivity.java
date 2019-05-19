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

        Intent recievedIntent = getIntent();
        selectedID = recievedIntent.getIntExtra("id", -1); //-1 is just default
        selectedName = recievedIntent.getStringExtra("name");

        doneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = liftName.getText().toString();
                if(!item.equals("")) {

                } else {

                }
            }

        });
    }
}
