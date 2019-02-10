package com.csabee.trainer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.csabee.listadapter.CategoryListAdapter;
import com.csabee.trainer.databinding.ActivityMainpagecontentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainPageActivity extends AppCompatActivity implements MainActivityContract.View, AdapterView.OnItemSelectedListener {

    private DrawerLayout menuDrawer;
    Button menuButton;
    Spinner spinner;

    private String[] options;
    private ArrayList<Category> catList;

    private ExpandableListView listView;
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
        Category bicepsz = new Category("Bicepsz");
        bicepsz.addExercise("Ülve térdhez szorított emelések egy kézzel",4,10,3,15.00);
        bicepsz.addExercise("Állva két kézzel mellhez húzás",4,10,4,10.00);
        catList.add(bicepsz);
        catList.add(new Category("Tricepsz"));
        catList.add(new Category("Hát"));
        catList.add(new Category("Váll"));
        catList.add(new Category("Láb"));
        binding.setCategories(bicepsz);
        createSpinnerOptions();
        initData(catList);
    }

    private void createSpinnerOptions() {
        spinner = findViewById(R.id.spnrSelectCategoryMainPage);
        options = new String[catList.size()];
        int i = 0;
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
        Toast.makeText(this,"you have clicked on the menu icon",Toast.LENGTH_LONG).show();
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
        switch (position) {
            case 0:
                Toast.makeText(this, "You have selected " + options[position], Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "You have selected " + options[position], Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "You have selected " + options[position], Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "You have selected " + options[position], Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "You have selected " + options[position], Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "You have selected " + options[position], Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initData(ArrayList<Category> catList) {
        listView = findViewById(R.id.explvMainPageContent);
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        for (Category category:catList
        ) {
            listDataHeader.add(category.getName());
            listHash.put(category.getName(),category.getExercises());

        }
        listAdapter = new CategoryListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

}

