package com.artv.android.core.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.artv.android.core.log.ArTvLogger;
import com.artv.android.system.ArTvApplication;

/**
 * Created by
 * mRogach on 26.08.2015.
 */
public class AlarmTurnOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        AlarmAlertWakeLock.acquire(arg0);

        ((ArTvApplication) arg0.getApplicationContext()).getApplicationLogic().getDeviceAdministrator().notifyScreenTurnedOn();
    }
}