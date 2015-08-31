package com.artv.android.core.display;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.artv.android.system.ArTvApplication;


/**
 * Created by
 * mRogach on 27.08.2015.
 */
public class WakeLockService extends Service {
    private long mTimeTurnOn;
    private ArTvApplication application;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        application = (ArTvApplication) getApplicationContext();
        mTimeTurnOn = intent.getLongExtra("turn_on", 0);
        Log.v("onWakeService", String.valueOf(mTimeTurnOn));
        if (mTimeTurnOn > 0) {
            application.getApplicationLogic().getTurnOffWorker().turnOn(mTimeTurnOn);
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}