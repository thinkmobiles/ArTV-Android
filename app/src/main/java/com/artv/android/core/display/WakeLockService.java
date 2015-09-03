package com.artv.android.core.display;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.artv.android.system.ArTvApplication;


/**
 * Created by
 * mRogach on 27.08.2015.
 */
public class WakeLockService extends Service {
    private ArTvApplication application;
    private long mTimeTurnOn;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        application = (ArTvApplication) getApplicationContext();
        mTimeTurnOn = intent.getLongExtra("turn_on", 0);
            application.getApplicationLogic().getTurnOffWorker().turnOn(mTimeTurnOn);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}