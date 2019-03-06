package com.e.appmc.sync;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.e.appmc.EvaluationActivity;

public class MonitorConnectionInternet  extends BroadcastReceiver {
    private Context context;
    private SyncDatabase sync;

    public boolean isConnected(int[] type, Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);

        for (int tipo : type) {

            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        }

        return false;
    }





    @Override
    public void onReceive(Context context, Intent intent) {

       /* this.sync = new SyncDatabase(context);
        int [] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};

        if (isConnected(type, context))
        {
            this.sync.syncFacilitySQLite();
            this.sync.syncPersonaSQLite();
        }

        context.sendBroadcast(new Intent("com.e.appmc.EvaluationActivity"));

        */
    }
}
