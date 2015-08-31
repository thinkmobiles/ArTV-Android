package com.artv.android.core.display;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.artv.android.app.playback.PlayModeManager;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.DeviceConfig;

import java.util.Calendar;

/**
 * Created by
 * mRogach on 27.08.2015.
 */
public final class TurnOffWorker {

    private AlarmManager mAlarmManager;
    private Context mContext;
    private PlayModeManager mPlayModeManager;
    private DeviceConfig mDeviceConfig;

    public TurnOffWorker(final Context _context, final PlayModeManager _playModeManager) {
        this.mContext = _context;
        this.mPlayModeManager = _playModeManager;
    }

    public final void setDeviceConfig(final DeviceConfig _config) {
        mDeviceConfig = _config;
    }

    public void turnOff() {
        ArTvLogger.printMessage("Turn on/off logic activated");
        ArTvLogger.printMessage(String.format("Off time %s, on time %s",
                mDeviceConfig.turnOffDisp, mDeviceConfig.turnOnDisp));

        long timeOffInMills = getTurnTimeInMills(mDeviceConfig.turnOffDisp);
        long timeOnInMills = getTurnTimeInMills(mDeviceConfig.turnOnDisp);
        startAlarmToTurnOffDevice(timeOffInMills, timeOnInMills);
    }

    public void turnOn(final long _onTime) {
        startAlarmToTurnOnDevice(_onTime);
    }

    public void cancel() {
        if (mAlarmManager == null) {
            mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        }
        Intent intentOff = new Intent(mContext, AlarmTurnOffReceiver.class);
        PendingIntent pendingIntentOff = PendingIntent.getBroadcast(mContext, 2, intentOff, 0);
        mAlarmManager.cancel(pendingIntentOff);
        Intent intentOn = new Intent(mContext, AlarmTurnOnReceiver.class);
        PendingIntent pendingIntentOn = PendingIntent.getBroadcast(mContext, 1, intentOn, 0);
        mAlarmManager.cancel(pendingIntentOn);
    }

    private long getCurrentDayInMills() {
        Calendar calNow = Calendar.getInstance();
        calNow.set(Calendar.HOUR, 0);
        calNow.set(Calendar.MINUTE, 0);
        calNow.set(Calendar.SECOND, 0);
        calNow.set(Calendar.MILLISECOND, 0);
        return calNow.getTimeInMillis();
    }

    private long getTurnTimeInMills(final String _timeToTurnOff) {
        if (mPlayModeManager != null) {
            return getCurrentDayInMills() + mPlayModeManager.getTimeInMills(mPlayModeManager.getTimeFromString(_timeToTurnOff));
        } else
            return 0;
    }

    private void startAlarmToTurnOnDevice(final long _timeTurnOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        long timeOn = _timeTurnOn - calendar.getTimeInMillis();
        if (timeOn < 0) {
            timeOn = _timeTurnOn + (24 * 3600 * 1000) - calendar.getTimeInMillis();
        }
        Log.v("turnOff", "turnOn " + timeOn);

        Intent intent = new Intent(mContext, AlarmTurnOnReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1, intent, 0);
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (timeOn > 0) {
            mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeOn, pendingIntent);
        }
    }

    private void startAlarmToTurnOffDevice(final long _timeTurnOff, final long _timeTurnOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        long timeOff = _timeTurnOff - calendar.getTimeInMillis();
        if (timeOff < 0) {
            timeOff = _timeTurnOff + (24 * 3600 * 1000) - calendar.getTimeInMillis();
        }
        Log.v("turnOff", "turnOff " + timeOff);

        Intent intent = new Intent(mContext, AlarmTurnOffReceiver.class);
        intent.putExtra("on", _timeTurnOn);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 2, intent, 0);
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (timeOff > 0) {
            mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeOff, pendingIntent);
        }
    }

}
