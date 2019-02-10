package com.csabee.trainer;

public class Exercise {
    private String name;
    private int series;
    private int repetitions;
    private int duration;
    private Double weight;
    private int intensity;

    public Exercise(String exerciseName, int usualSeries, int repetitionAmount, int intensity){
        this.name = exerciseName;
        this.series = usualSeries;
        this.repetitions = repetitionAmount;
        this.intensity = intensity;
    }

    public Exercise withDuration(int duration){
        this.setDuration(duration);
        return this;
    }

    public Exercise withWeight(Double weight){
        this.setWeight(weight);
        return this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
