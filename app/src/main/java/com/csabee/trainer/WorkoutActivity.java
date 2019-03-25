package com.csabee.trainer;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csabee.sharedpreferences.TrainingDataHandler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvWorkoutActivity;
    private ArrayList<Category> workoutData;
    private int noOfExercises,noOfExercisesDone;
    private Button btnExerciseDoneOngoingWorkout,btnSkipExerciseOngoingWorkout,btnExitOngoingWorkout;
    private TextView txtExerciseDurationOngoingWorkout,txtExerciseProgressOngoinWorkout;
    ProgressBar progressBar;
    private TextView txtCurrentExerciseOngoingWorkout;
    private boolean isLastExercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutData = TrainingDataHandler.getCurrentTrainingData(this);
        setupExpandableList();
    }

    private void setupExpandableList() {
        lvWorkoutActivity = findViewById(R.id.lvWorkoutActivity);
        txtCurrentExerciseOngoingWorkout = findViewById(R.id.txtCurrentExerciseOngoingWorkout);
        btnExerciseDoneOngoingWorkout  = findViewById(R.id.btnExerciseDoneOngoingWorkout);
        btnSkipExerciseOngoingWorkout = findViewById(R.id.btnSkipExerciseOngoingWorkout);
        txtExerciseDurationOngoingWorkout = findViewById(R.id.txtExerciseDurationOngoingWorkout);
        txtExerciseProgressOngoinWorkout = findViewById(R.id.txtExerciseProgressOngoinWorkout);
        btnExitOngoingWorkout = findViewById(R.id.btnExitOngoingWorkout);
        progressBar = findViewById(R.id.prgbarWorkoutActivity);
        btnExerciseDoneOngoingWorkout.setOnClickListener(this);
        btnSkipExerciseOngoingWorkout.setOnClickListener(this);
        btnExitOngoingWorkout.setOnClickListener(this);
        noOfExercises = getExerciseNumber()-1;
        noOfExercisesDone = -1;
        String[] workoutArray = new String[noOfExercises];
        progressBar.setMax(noOfExercises+1);
        checkProgressBar();
        setTimer();


        int j = -1;
        for(int i = 0; i < workoutData.size();i++){
            for (Exercise exercise : workoutData.get(i).getExercises()
                 ) {
                if(j > -1){
                    workoutArray[j++] = exercise.getName() + "\n" + exercise.getSeries() + " X "+ exercise.getRepetitions() + " "
                            + exercise.getWeight() + "kg " + exercise.getDuration() + "s";
                }else{
                    txtCurrentExerciseOngoingWorkout.setText(exercise.getName() + "\n" + exercise.getSeries() + " X "+ exercise.getRepetitions() + " "
                            + exercise.getWeight() + "kg " + exercise.getDuration() + "s");
                    j++;
                }
            }
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,R.layout.list_remainingexercises,workoutArray);
        lvWorkoutActivity.setAdapter(mAdapter);
    }

    private void setTimer() {
        new AsyncWorkoutTimer().execute("");
    }


    private void checkProgressBar() {
        if(noOfExercisesDone < getExerciseNumber()/3){
            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            progressBar.setProgressDrawable(progressDrawable);
        }else if(noOfExercisesDone < getExerciseNumber()/1.75){
            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }else{
            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        }
        if(Build.VERSION.SDK_INT < 24){
            progressBar.setProgress(noOfExercisesDone);
        }
        else{
            progressBar.setProgress(noOfExercisesDone+1,true);
        }
        txtExerciseProgressOngoinWorkout.setText( (noOfExercisesDone+1) + "  /  " + getExerciseNumber());
    }

    private int getExerciseNumber() {
        int exerciseAmount = 0;
        for(int i = 0; i < workoutData.size();i++){
            for (Exercise exercise : workoutData.get(i).getExercises()
            ) {
                exerciseAmount++;
            }
        }
        return exerciseAmount;
    }

    private void getNextExercise(){
        noOfExercises -= 1;
        noOfExercisesDone += 1;
        ArrayList<String> workoutList = new ArrayList();
        int j = -1;
        for(int i = 0; i < workoutData.size();i++){
            for (Exercise exercise : workoutData.get(i).getExercises()
            ) {
                if(j > noOfExercisesDone){
                    workoutList.add(exercise.getName() + "\n" + exercise.getSeries() + " X "+ exercise.getRepetitions() + " "
                            + exercise.getWeight() + "kg " + exercise.getDuration() + "s");
                }
                else if(j == noOfExercisesDone){
                    txtCurrentExerciseOngoingWorkout.setText(exercise.getName() + "\n" + exercise.getSeries() + " X "+ exercise.getRepetitions() + " "
                            + exercise.getWeight() + " kg " + exercise.getDuration() + "s");
                }
                j++;
            }
        }
        if(noOfExercises == 0){
            isLastExercise = true;
        }
        checkProgressBar();
        String[] workoutArray = new String[workoutList.size()];
        workoutArray = workoutList.toArray(workoutArray);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,R.layout.list_remainingexercises,workoutArray);
        lvWorkoutActivity.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnExerciseDoneOngoingWorkout:
                if(!isLastExercise) {
                    getNextExercise();
                }
                else{
                    Toast.makeText(this, "Congratulations on finishing the workout!!!", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case R.id.btnSkipExerciseOngoingWorkout:
                if(!isLastExercise){
                    Toast.makeText(this, "Skipping exercise.", Toast.LENGTH_SHORT).show();
                    getNextExercise();
                }
                else{Toast.makeText(this, "Congratulations on finishing the workout!!!", Toast.LENGTH_LONG).show();finish();}
                break;
            case R.id.btnExitOngoingWorkout:
                Toast.makeText(this, "You chose to exit the workout.", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    public class AsyncWorkoutTimer extends AsyncTask<String,Integer,String> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0] > 59){
                int elapsedMin = values[0]/60;
                int elapsedSec = values[0]-(elapsedMin*60);
                txtExerciseDurationOngoingWorkout.setText(elapsedMin + " : " + elapsedSec);
            }else{ txtExerciseDurationOngoingWorkout.setText(values[0] + " s");}
        }

        @Override
        protected String doInBackground(String... strings) {
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                                      int elapsedSeconds = 0;
                                      @Override
                                      public void run() {
                                          elapsedSeconds += 1;
                                          publishProgress(elapsedSeconds);
                                      }

                                  },
                    0,
                    1000);
            return null;
        }
    }
}
