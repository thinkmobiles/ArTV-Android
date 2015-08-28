package com.artv.android.core.display;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.artv.android.app.playback.PlayModeManager;


/**
 * Created by
 * mRogach on 27.08.2015.
 */
public class WakeLockService extends Service {
    private TurnOffWorker mTurnOffWorker;
    private PlayModeManager mPlayModeManager;
    private long mTimeTurnOn;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimeTurnOn = intent.getLongExtra("turn_on", 0);
        mPlayModeManager = new PlayModeManager();
        mTurnOffWorker = new TurnOffWorker(getApplicationContext(), mPlayModeManager);
        if (mTimeTurnOn != 0) {
            mTurnOffWorker.turnOn(mTimeTurnOn);
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}