package com.csabee.trainer;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.csabee.backgroundworkers.ServerCheckService;
import com.csabee.listadapter.CategoryListAdapter;
import com.csabee.trainer.databinding.ActivityMainpagecontentBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPageActivity extends AppCompatActivity implements MainActivityContract.View, AdapterView.OnItemSelectedListener {

    private DrawerLayout menuDrawer;
    Button menuButton;
    Spinner spinner;

    Intent serviceIntent;
    private ServerCheckService checkService;

    private static String TAG = "MainPageActivity";
    private boolean firstUse = true;
    private String[] options;
    private ArrayList<Category> catList;
    private ArrayList<Category> insertedCatList;

    private com.idunnololz.widgets.AnimatedExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<Exercise>> listHash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainpagecontentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mainpagecontent);
        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext());
        setListeners();
        User userData = new User("Magyar Csaba");
        binding.setUser(userData);
        binding.setPresenter(mainActivityPresenter);
        catList = new ArrayList<Category>();
        addLab();addHat();addTricepsz();addBicepsz();
        binding.setCategoryList(catList);
        createSpinnerOptions();
        insertedCatList = new ArrayList<Category>();
        initData(insertedCatList);
        setupService();
    }

    private void setupService() {
        checkService = new ServerCheckService(this);
        serviceIntent = new Intent(this,ServerCheckService.class);
        if (!isMyServiceRunning(checkService.getClass())) {
            startService(serviceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<? extends ServerCheckService> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }


    private void createSpinnerOptions() {
        spinner = findViewById(R.id.spnrSelectCategoryMainPage);
        options = new String[catList.size()+1];
        options[0] = "Select category";
        int i = 1;
        for (Category category:catList
             ) {
            options[i++] = category.getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPageActivity.this,R.layout.support_simple_spinner_dropdown_item,options);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);
    }

    private void setListeners() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        menuDrawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        menuDrawer.closeDrawers();
                        return true;
                    }
                }
        );
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_baseline_menu);
    }

    @Override
    public void menuClick() {
        if (menuDrawer.isDrawerOpen(GravityCompat.START))
            menuDrawer.closeDrawer(GravityCompat.START);
            //Open the drawer when it's closed
        else menuDrawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            Toast.makeText(this, "Maximum number of categories selected!", Toast.LENGTH_SHORT).show();
        }
        else {
            firstUse = false;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initData(ArrayList<Category> currentList) {
        listView = findViewById(R.id.explvMainPageContent);
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        for (Category category:currentList
        ) {
            listDataHeader.add(category.getName());
            listHash.put(category.getName(),category.getExercises());

        }
        listAdapter = new CategoryListAdapter(this,listDataHeader,listHash);
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
}

