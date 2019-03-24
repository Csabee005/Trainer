package com.csabee.trainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.csabee.listadapter.CategoryListAdapter;
import com.csabee.sharedpreferences.SaveSharedPreference;
import com.csabee.sharedpreferences.TrainingDataHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPageFragment extends Fragment implements AdapterView.OnItemSelectedListener, CategoryListAdapter.EventListener, View.OnClickListener
{
    Spinner spinner;
    Button btnStartWorkout;

    private com.idunnololz.widgets.AnimatedExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<Exercise>> listHash;

    private static String TAG = "MainPageActivity";
    private boolean firstUse = true;
    private String[] options;
    private ArrayList<Category> catList;
    private ArrayList<Category> insertedCatList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_mainpage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        User userData = new User("Magyar Csaba");
        catList = new ArrayList<>();
        addLab();addHat();addTricepsz();addBicepsz();
        createSpinnerOptions();
        insertedCatList = new ArrayList<>();
        initData(insertedCatList);
        userSetup(userData);
    }

    private void userSetup(@NonNull User userData) {
        TextView userName = getView().findViewById(R.id.txtProfileNameMainPageFragment);
        userName.setText(userData.getName());
    }

    private void createSpinnerOptions() {
        spinner = getView().findViewById(R.id.spnrSelectCategoryMainPage);
        options = new String[catList.size()+1];
        options[0] = "Select category";
        int i = 1;
        for (Category category:catList
        ) {
            options[i++] = category.getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,options);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!firstUse && insertedCatList.size() < 3){
            for (int i = 0; i < catList.size();i++){
                if(catList.get(i).getName().equals(options[position])){
                    insertedCatList.add(catList.get(i));
                    initData(insertedCatList);
                    catList.remove(i);
                    createSpinnerOptions();
                }
            }
        }
        else if(insertedCatList.size() == 3){
            Toast.makeText(getContext(), "Maximum number of categories selected!", Toast.LENGTH_SHORT).show();
            parent.setSelection(0);
        }
        else {
            firstUse = false;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initData(@NonNull ArrayList<Category> currentList) {
        btnStartWorkout = getView().findViewById(R.id.btnStartWorkoutMainPageFragment);
        btnStartWorkout.setOnClickListener(this);
        listView = getView().findViewById(R.id.explvMainPageContent);
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        for (Category category:currentList
        ) {
            listDataHeader.add(category.getName());
            listHash.put(category.getName(),category.getExercises());

        }
        listAdapter = new CategoryListAdapter(getContext(),listDataHeader,listHash, this);
        listView.setAdapter(listAdapter);
    }

    public void addBicepsz(){
        Category bicepsz = new Category("Bicepsz");
        bicepsz.addExercise("Scott padon kétkezes emelések francia rúddal",5,10,5,25.0);
        bicepsz.addExercise("Ülve térdhez szorított emelések egy kézzel",4,10,3,15.00);
        bicepsz.addExercise("Állva két kézzel mellhez húzás",4,10,4,10.00);
        catList.add(bicepsz);
    }

    public void addTricepsz(){
        Category tricepsz = new Category("Tricepsz");
        tricepsz.addExercise("Hát mögé engedés egykezes súlyzóval",4,10,3,7.5);
        tricepsz.addExercise("Lenyomás csigán",3,8,4,10.0);
        tricepsz.addExercise("Karnyújtás ülve kézisúlyzóval",4,15,5,10.0);
        catList.add(tricepsz);
    }

    public void addHat(){
        Category hat = new Category("Hát");
        hat.addExercise("Evezés egy kézzel",4,12,3,15.0);
        hat.addExercise("Mellhez húzás gépnél",4,8,4,25.0);
        catList.add(hat);
    }

    public void addLab(){
        Category lab = new Category("Láb");
        lab.addExercise("Guggolás",3,20,3);
        lab.addExercise("Egyensúlyozás egy lábon",3,4,3,30,10);
        catList.add(lab);
    }

    public void removeCategory(String headerTitle){
        for (int i = 0; i < insertedCatList.size();i++){
            if(insertedCatList.get(i).getName().equals(headerTitle)){
                catList.add(insertedCatList.get(i));
                insertedCatList.remove(i);
                initData(insertedCatList);
                createSpinnerOptions();
                break;
            }
        }
    }

    @Override
    public void onRemoveEvent(String headerTitle) {
        removeCategory(headerTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartWorkoutMainPageFragment:
                saveAsJson(insertedCatList);
                getContext().startActivity(new Intent(getContext(), WorkoutActivity.class));
                break;
        }
    }

    private void saveAsJson(ArrayList<Category> insertedCatList) {
        TrainingDataHandler.saveCurrentTrainingData(getContext(),insertedCatList);
    }

    @Override
    public void onResume() {
        super.onResume();
        catList = new ArrayList<>();
        insertedCatList = new ArrayList<>();
        addLab();addHat();addTricepsz();addBicepsz();
        createSpinnerOptions();
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        listAdapter = new CategoryListAdapter(getContext(),listDataHeader,listHash, this);
        listView.setAdapter(listAdapter);
    }
}
