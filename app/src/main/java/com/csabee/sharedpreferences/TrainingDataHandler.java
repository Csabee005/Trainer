package com.csabee.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.csabee.trainer.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class TrainingDataHandler {
    public static void saveCurrentTrainingData(Context context, ArrayList<Category> trainingData){
        ObjectMapper mapper = new ObjectMapper();
        String jsonTrainingData = null;
        try {
            jsonTrainingData = mapper.writeValueAsString(trainingData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        SaveSharedPreference.setTrainingData(context,jsonTrainingData);
    }

    public static ArrayList<Category> getCurrentTrainingData(Context context){
        ObjectMapper mapper = new ObjectMapper();

        try {
            ArrayList<Category> trainingData = mapper.readValue(SaveSharedPreference.getTrainingData(context),new TypeReference<ArrayList<Category>>(){});
            return trainingData;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
