package com.csabee.backgroundworkers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ServerCheckService extends Service {
    public int counter=0;
    private static Context context;
    NotificationCreator notificationCreator;

    public ServerCheckService(Context applicationContext) {
        super();
        context = applicationContext;
        notificationCreator = new NotificationCreator(context);
        notificationCreator.createNotificationChannel();
        Log.i("HERE", "here I am!");
    }

    public ServerCheckService() {
        notificationCreator = new NotificationCreator(context);
        notificationCreator.createNotificationChannel();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent(this, ServerCheckServiceRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 15000, 15000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
                //Create notification message here
                Random random = new Random();
                int low = 0;
                int high = 10;
                int gymChance = random.nextInt(high-low) + low;
                if(gymChance < 4) {
                    notificationCreator.createNotification();
                }
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
