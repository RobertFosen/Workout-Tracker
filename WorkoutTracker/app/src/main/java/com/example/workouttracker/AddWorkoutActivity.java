package com.example.workouttracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddWorkoutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button date;
    private EditText liftName;
    private EditText weightUsed;
    private EditText numOfSets;
    private EditText numOfReps;
    private static final String TAG = "MyActivity";
    private int counter = 0;
    DatabaseHelper mDatabaseHelper;
    String currentDateString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workout);

        liftName = (EditText)findViewById(R.id.liftName);
        weightUsed = (EditText)findViewById(R.id.weightUsed);
        numOfSets = (EditText)findViewById(R.id.numberOfSets);
        numOfReps = (EditText)findViewById(R.id.numberofReps);
        mDatabaseHelper = new DatabaseHelper(this);

        //sets a listener to the change date button which displays the datepickerfragment and
        //will trigger the ondateset function when selected
        date = findViewById(R.id.dateButton);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

    }


    //fires after date is chosen.
    //stores the date in a string and displays in the datetextview
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance().format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.dateTextView);
        textView.setText(currentDateString);
    }

    public void AddData(String liftDone, int weightUsed, int numOfSets, int numOfReps, String currentDateString) {
        boolean insertData = mDatabaseHelper.addData(liftDone, weightUsed, numOfSets, numOfReps, currentDateString);

        if (insertData) {
            Toast.makeText(this,"Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    //function called when the user selects the DONE button on the activity
    public void onDoneAddingWorkoutButtonClick(View v) {

        //TODO need to change names of the edittexts so a loop can be run to add each line to database and clear lines
        //make counter for addnewworkout global and make a private counter in this function and compare them
        //and make this whole function a while loop that runs until the private counter catches up?

        //gets the entered Lift name, weight, sets and reps from the edittexts
        String tempStringFromLiftEditText = liftName.getText().toString();
        int tempIntFromWeightUsedEditText = Integer.parseInt(weightUsed.getText().toString());
        int tempIntFromNumOfSetsEditText = Integer.parseInt(numOfSets.getText().toString());
        int tempIntFromNumOfRepsEditText = Integer.parseInt(numOfReps.getText().toString());

        //creates a new alert dialog stating that the workout is completed and listing what they entered
        AlertDialog displayWorkout = new AlertDialog.Builder(AddWorkoutActivity.this).create();
        displayWorkout.setTitle("Completed Workout");
        displayWorkout.setMessage(tempStringFromLiftEditText + "    (" + tempIntFromNumOfSetsEditText + " x " + tempIntFromNumOfRepsEditText + ") @ " + tempIntFromWeightUsedEditText + "LBS");
        displayWorkout.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        displayWorkout.show();

        if (tempStringFromLiftEditText.length() != 0) {
            AddData(tempStringFromLiftEditText, tempIntFromWeightUsedEditText, tempIntFromNumOfSetsEditText, tempIntFromNumOfRepsEditText, currentDateString);
            liftName.setText("");
        } else {
            Toast.makeText(this, "You must put something in the text field!", Toast.LENGTH_SHORT);
        }

    }

    public void onAddNewWorkoutButtonClick(View v) {

        //uses a counter each time the button is pressed to ensure they cannot overfill the screen
        if (counter < 5) {

            //matches the layout LinearLayout with the workoutLayout housing the rows of edittexts in the adding workout activity
            LinearLayout layout = (LinearLayout) findViewById(R.id.workoutLayout);
            layout.setOrientation(LinearLayout.VERTICAL);

            //calls the size of edittexts from other activity
            //only calls one of the smaller three since they are all the same size
            EditText liftNameEditText = (EditText) findViewById(R.id.liftName);
            EditText widthForRepsSetsAndWeight = (EditText) findViewById(R.id.numberOfSets);

            //creates a new linearLayout to nest within the larger layout "layout"
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            //this is the lift Name edit text that doesnt get triggered within the loop since there is only one of this size
            EditText liftNameEditTextAdded = new EditText(this);
            //uses the getWidth and getHeight function that we called above to get width and height of original edit text
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(liftNameEditText.getWidth(), liftNameEditText.getHeight());
            //use the resources class to set margins with dp instead of pixels so it is usable on different sized screen
            lparams.setMargins(getResources().getDimensionPixelSize(R.dimen.margin_left_for_lift_done), 0, 0, 0);

            //sets the parameters, hint and adds it to the layout
            liftNameEditTextAdded.setLayoutParams(lparams);
            liftNameEditTextAdded.setHint("Lift Performed");
            row.addView(liftNameEditTextAdded);

            //adds four edittexts to the layout underneath the previous ones
            //creates new layout parameters to match the ones above it
            //uses the for loop counter to differentiate each since certain ones have different parameters and hints and margins
            for (int i = 0; i < 3; i++) {

                EditText editText = new EditText(this);
                LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(widthForRepsSetsAndWeight.getWidth(), widthForRepsSetsAndWeight.getHeight());

                //uses 3 if statements to add the hints to each edittext using the counter
                //the first smaller edittext also has the left margin added to it to space it further from the liftname edit text
                if (i == 0) {
                    lparams2.setMargins(getResources().getDimensionPixelSize(R.dimen.margin_left_for_weight_lifted), 0, 0, 0);
                    editText.setHint("LBS");
                }
                if (i == 1) {
                    editText.setHint("Sets");
                }
                if (i == 2) {
                    editText.setHint("Reps");
                }

                //sets the parameters, IDs, and adds it to layout
                editText.setLayoutParams(lparams2);
                editText.setId(i + 1);
                row.addView(editText);
            }

            //adds the overall layout row to the layout and increments counter
            layout.addView(row);
            counter++;
        } else {
            //displays a message if the user cannot enter anymore exercises
            Toast.makeText(this, "You cannot add any more exercises", Toast.LENGTH_LONG).show();
        }
    }
}
