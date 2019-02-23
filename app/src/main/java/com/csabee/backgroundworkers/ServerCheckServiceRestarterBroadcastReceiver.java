package com.csabee.backgroundworkers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServerCheckServiceRestarterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("SERVER_CHECK_SERVICE","Service stopped!");
        context.startService(new Intent(context, ServerCheckService.class));
    }
}
