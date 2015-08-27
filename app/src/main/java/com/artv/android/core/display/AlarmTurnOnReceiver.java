package com.artv.android.core.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by
 * mRogach on 26.08.2015.
 */
public class AlarmTurnOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        AlarmAlertWakeLock.acquire(arg0);
        Log.v("wake", "wake");
//        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();
    }
}