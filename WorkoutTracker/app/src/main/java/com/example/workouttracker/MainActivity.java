package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private Button addNewWorkoutButton;
    private Button seePreviousWorkoutsButton;
    private Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewWorkoutButton = findViewById(R.id.addWorkout);
        seePreviousWorkoutsButton = findViewById(R.id.seePreviousWorkouts);
        quitButton = findViewById(R.id.quit);
    }

    //fires when addworkoutbutton is pressed
    public void onAddWorkoutButtonPress(View v) {
        Intent launchResult = new Intent(this, AddWorkoutActivity.class);
        startActivity(launchResult);
    }

    //fires when seepreviousworkoutsbutton is pressed
    public void onSeePreviousWorkoutsButtonPress(View v) {
        Intent launchPreviousWorkouts = new Intent(this, SeePreviousWorkoutsActivity.class);
        startActivity(launchPreviousWorkouts);
    }

    //closes the app
    public void onQuitButtonPress(View v) {finish();}


}
