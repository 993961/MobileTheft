package com.newsys.mobiletheft;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Sanjeev on 5/12/2015.
 */
public class BroadBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent=new Intent();
        serviceIntent.setAction("com.newsys.SimchangeService");
        context.startService(serviceIntent);
    }
}
