package com.fosenapps.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onQuitButtonPress(View v) {
        finish();
    }
}
