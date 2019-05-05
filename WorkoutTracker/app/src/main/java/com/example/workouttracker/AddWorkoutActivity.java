package com.example.workouttracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddWorkoutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button dateSelectorButton;
    private TextView date;
    private EditText liftName;
    private EditText weightUsed;
    private EditText numOfSets;
    private EditText numOfReps;
    private boolean everyEditTextIsPopulated;
    private Calendar c;

    int counter = 0;
    int addToDBCounter = 0;
    String currentDateString;
    String displayWorkoutMessage = "";
    EditText[] liftsDone = new EditText[6];
    EditText[] weightsUsed = new EditText[6];
    EditText[] setsDone = new EditText[6];
    EditText[] repsDone = new EditText[6];

    private static final String TAG = "MyActivity";
    DatabaseHelper mDatabaseHelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workout);

        date = (TextView)findViewById(R.id.dateTextView);
        liftName = (EditText)findViewById(R.id.liftName);
        weightUsed = (EditText)findViewById(R.id.weightUsed);
        numOfSets = (EditText)findViewById(R.id.numberOfSets);
        numOfReps = (EditText)findViewById(R.id.numberofReps);
        mDatabaseHelper = new DatabaseHelper(this);

        //gets the date to set the default date on the addWorkout screen
        c = Calendar.getInstance();
        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.DAY_OF_MONTH);
        currentDateString = DateFormat.getDateInstance().format(c.getTime());
        date.setText(currentDateString);

        //sets a listener to the change dateSelectorButton button which displays the datepickerfragment and
        //will trigger the ondateset function when selected
        dateSelectorButton = findViewById(R.id.dateButton);
        dateSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "dateSelectorButton picker");
            }
        });

        //adds the original edittext values to the arrays which will go into the DB later
        liftsDone[0] = liftName;
        weightsUsed[0] = weightUsed;
        setsDone[0] = numOfSets;
        repsDone[0] = numOfReps;

    }


    //fires after dateSelectorButton is chosen.
    //stores the dateSelectorButton in a string and displays in the datetextview
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //gets the newly selected date if the user changes it
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance().format(c.getTime());
        date.setText(currentDateString);
    }

    //function that adds the data to the DB
    public void AddData(String liftDone, int weightUsed, int numOfSets, int numOfReps, String currentDateString) {
        boolean insertData = mDatabaseHelper.addData(liftDone, weightUsed, numOfSets, numOfReps, currentDateString);

        //checks if the data was inserted or if there was an error
        if (insertData) {
            Toast.makeText(this,"Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    //function called when the user selects the DONE button on the activity
    public void onDoneAddingWorkoutButtonClick(View v) {

        //counter that keeps track of which item is getting added next to iterate through the array
        addToDBCounter = 0;
        //flag that turns false if a field is left blank
        everyEditTextIsPopulated = true;

        //loop that checks if any field was left blank and sets the flag if it was
        for (int i = 0; i <= counter; i++) {
            if (liftsDone[i].length() == 0 || weightsUsed[i].length() == 0 || setsDone[i].length() == 0 || repsDone[i].length() == 0) {
                everyEditTextIsPopulated = false;
            }
        }

        //fires if every edittext is populated
        if (everyEditTextIsPopulated) {

            //populates the alertmessage with the data that was entered, creates a new row for each different lift
            for (int i = 0; i <= counter; i++) {
                displayWorkoutMessage = displayWorkoutMessage + liftsDone[i].getText().toString() + "    (" + setsDone[i].getText().toString() + " x " + repsDone[i].getText().toString() + ") @ " + weightsUsed[i].getText().toString() + "LBS\n";
            }

            //creates a new alert dialog stating that the workout is completed and listing what they entered
            AlertDialog displayWorkout = new AlertDialog.Builder(AddWorkoutActivity.this).create();
            displayWorkout.setTitle("Completed Workout");

            //sets the alertmessage message, shows it, then sets the message to an empty field for next time it gets called
            displayWorkout.setMessage(displayWorkoutMessage);
            displayWorkout.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            displayWorkout.show();
            displayWorkoutMessage = "";

            //adds the data to the DB
            while (addToDBCounter <= counter) {
                AddData(liftsDone[addToDBCounter].getText().toString(),
                        Integer.parseInt(weightsUsed[addToDBCounter].getText().toString()),
                        Integer.parseInt(setsDone[addToDBCounter].getText().toString()),
                        Integer.parseInt(repsDone[addToDBCounter].getText().toString()), currentDateString);

                //sets all fields to empty
                resetFields(addToDBCounter);

                //counter jumps so next time the next items in the array get added
                addToDBCounter++;
            }
        } else {
            Toast.makeText(this, "You left a field empty!", Toast.LENGTH_SHORT).show();
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
            liftsDone[counter + 1] = liftNameEditTextAdded;


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
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    weightsUsed[counter + 1] = editText;
                }
                if (i == 1) {
                    editText.setHint("Sets");
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    setsDone[counter + 1] = editText;
                }
                if (i == 2) {
                    editText.setHint("Reps");
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    repsDone[counter + 1] = editText;
                }

                //sets the parameters, IDs, and adds it to layout
                editText.setLayoutParams(lparams2);
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

    //ends the activity
    public void onDoneButtonClick(View v) {
        finish();
    }

    //resets all of the edittexts
    private void resetFields(int addToDBCounter) {
        liftsDone[addToDBCounter].setText("");
        weightsUsed[addToDBCounter].setText("");
        setsDone[addToDBCounter].setText("");
        repsDone[addToDBCounter].setText("");
    }
}
