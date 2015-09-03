package com.artv.android.core.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.artv.android.system.ArTvApplication;

/**
 * Created by
 * mRogach on 26.08.2015.
 */
public class AlarmTurnOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        ArTvApplication application = (ArTvApplication) arg0.getApplicationContext();
        DeviceAdministrator mDeviceAdministrator = application.getApplicationLogic().getDeviceAdministrator();
        mDeviceAdministrator.lockScreen(arg1.getLongExtra("on", 0));
    }
}