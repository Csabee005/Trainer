package com.csabee.trainer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "series",
        "repetitions",
        "duration",
        "weight",
        "intensity"
})
public class Exercise {
    @JsonProperty("name")
    private String name;
    @JsonProperty("series")
    private int series;
    @JsonProperty("repetitions")
    private int repetitions;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("intensity")
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

    public Exercise(){}

    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("series")
    public int getSeries() {
        return series;
    }
    @JsonProperty("series")
    public void setSeries(int series) {
        this.series = series;
    }
    @JsonProperty("repetitions")
    public int getRepetitions() {
        return repetitions;
    }
    @JsonProperty("repetitions")
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
    @JsonProperty("duration")
    public int getDuration() {
        return duration;
    }
    @JsonProperty("duration")
    public void setDuration(int duration) {
        this.duration = duration;
    }
    @JsonProperty("weight")
    public Double getWeight() {
        return weight;
    }
    @JsonProperty("weight")
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    @JsonProperty("intensity")
    public int getIntensity() {
        return intensity;
    }
    @JsonProperty("intensity")
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
