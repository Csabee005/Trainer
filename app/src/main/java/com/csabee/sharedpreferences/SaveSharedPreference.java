package com.csabee.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class SaveSharedPreference {

    private static HashMap<String, byte[]> MAP = new HashMap<>();

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setTrainingData(Context context, String trainingData){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("trainingData",trainingData);
        editor.apply();
    }

    public static String getTrainingData(Context context){
        return getPreferences(context).getString("trainingData","");
    }
}

