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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class MainPageActivity extends AppCompatActivity implements MainActivityContract.View {

    private DrawerLayout menuDrawer;

    Intent serviceIntent;
    private ServerCheckService checkService;

    private static String TAG = "MainPageActivity";

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpagecontent);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentMainpageContent, new MainPageFragment());
        mFragmentTransaction.commit();

        setListeners();
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
}

