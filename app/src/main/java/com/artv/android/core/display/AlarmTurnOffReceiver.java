package com.artv.android.core.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by
 * mRogach on 26.08.2015.
 */
public class AlarmTurnOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Log.v("wake", "off");
        Log.v("onAlarmOff", String.valueOf(arg1.getLongExtra("on", 0)));
        if (arg1.getLongExtra("on", 0) > 0) {
            DeviceAdministrator.lockScreen(arg0, arg1.getLongExtra("on", 0));
        }
    }
}