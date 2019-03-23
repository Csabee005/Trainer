package com.csabee.trainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.csabee.sharedpreferences.TrainingDataHandler;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        ArrayList<Category> workoutData = TrainingDataHandler.getCurrentTrainingData(this);




    }
}
