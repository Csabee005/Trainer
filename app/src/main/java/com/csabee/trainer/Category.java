package com.csabee.trainer;

import android.databinding.BaseObservable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Category extends BaseObservable {

    private String name;
    private HashMap<String,Exercise> exerciseMap;

    public Category(String name){
        this.setName(name);
        exerciseMap = new HashMap<String,Exercise>();
    }

    public void addExercise(String exerciseName, int usualSeries, int repetitionAmount, int intensity){
        getExerciseMap().put(exerciseName,new Exercise(exerciseName, usualSeries,repetitionAmount,intensity));
    }

    public void addExercise(String exerciseName, int usualSeries, int repetitionAmount, int intensity, int duration){
        getExerciseMap().put(exerciseName,new Exercise(exerciseName, usualSeries,repetitionAmount,intensity)
        .withDuration(duration));
    }

    public void addExercise(String exerciseName, int usualSeries, int repetitionAmount, int intensity, int duration, double weight){
        getExerciseMap().put(exerciseName,new Exercise(exerciseName, usualSeries,repetitionAmount,intensity)
        .withWeight(weight)
        .withDuration(duration));
    }

    public void addExercise(String exerciseName, int usualSeries, int repetitionAmount, int intensity, double weight){
        getExerciseMap().put(exerciseName,new Exercise(exerciseName, usualSeries,repetitionAmount,intensity)
        .withWeight(weight));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Exercise> getExerciseMap() {
        return exerciseMap;
    }

    public void setExerciseMap(HashMap<String, Exercise> exerciseMap) {
        this.exerciseMap = exerciseMap;
    }

    public ArrayList<Exercise> getExercises(){
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        exerciseList.addAll(exerciseMap.values());
        return exerciseList;
    }
}
