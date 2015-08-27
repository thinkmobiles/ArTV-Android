package com.artv.android.core.display;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.artv.android.app.playback.PlayModeManager;

import java.util.Calendar;

/**
 * Created by
 * mRogach on 27.08.2015.
 */
public final class TurnOffWorker {

    private AlarmManager mAlarmManager;
    private Context mContext;
    private PlayModeManager mPlayModeManager;

    public TurnOffWorker(final Context _context, final PlayModeManager _playModeManager) {
        this.mContext = _context;
        this.mPlayModeManager = _playModeManager;
    }

    public void setOffTime(final String _offTime) {
        long timeOffInMills = getTurnOffTimeInMills(_offTime);
        startAlarmToTurnOffDevice(timeOffInMills);
    }

    public void cancel() {
        if (mAlarmManager != null) {
            Intent intent = new Intent(mContext, AlarmTurnOffReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1, intent, 0);
            mAlarmManager.cancel(pendingIntent);
        }
    }

    private long getCurrentDayInMills() {
        Calendar calNow = Calendar.getInstance();
        calNow.set(Calendar.HOUR, 0);
        calNow.set(Calendar.MINUTE, 0);
        calNow.set(Calendar.SECOND, 0);
        calNow.set(Calendar.MILLISECOND, 0);
        return calNow.getTimeInMillis();
    }

    private long getTurnOffTimeInMills(final String _timeToTurnOff) {
        if(mPlayModeManager != null) {
            return getCurrentDayInMills() + mPlayModeManager.getTimeInMills(mPlayModeManager.getTimeFromString(_timeToTurnOff));
        } else
            return 0;
    }

    private void startAlarmToTurnOffDevice(final long _timeTurnOff) {
        if (_timeTurnOff != 0) {
            Intent intent = new Intent(mContext, AlarmTurnOffReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1, intent, 0);
            mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, _timeTurnOff, pendingIntent);
        }
    }

}
