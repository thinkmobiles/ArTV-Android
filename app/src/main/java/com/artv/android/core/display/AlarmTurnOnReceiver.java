package com.artv.android.core.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by
 * mRogach on 26.08.2015.
 */
public class AlarmTurnOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        AlarmAlertWakeLock.acquire(arg0);
    }
}