package com.csabee.trainer;

import android.databinding.BaseObservable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "exerciseMap"
})
public class Category extends BaseObservable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("exerciseMap")
    private HashMap<String,Exercise> exerciseMap;

    public Category(String name){
        this.setName(name);
        exerciseMap = new HashMap<String,Exercise>();
    }

    public Category(){}

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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("exerciseMap")
    public HashMap<String, Exercise> getExerciseMap() {
        return exerciseMap;
    }

    @JsonProperty("exerciseMap")
    public void setExerciseMap(HashMap<String, Exercise> exerciseMap) {
        this.exerciseMap = exerciseMap;
    }

    public ArrayList<Exercise> getExercises(){
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        exerciseList.addAll(exerciseMap.values());
        return exerciseList;
    }
}
