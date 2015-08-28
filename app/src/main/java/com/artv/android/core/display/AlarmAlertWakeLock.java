package com.artv.android.core.display;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by mRogach on 27.08.2015.
 */
class AlarmAlertWakeLock {

    private static PowerManager.WakeLock sWakeLock;

    static void acquire(Context context) {
        if (sWakeLock != null) {
            sWakeLock.release();
        }

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        sWakeLock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "AlarmClock");
        sWakeLock.acquire();
    }

    static void release() {
        if (sWakeLock != null) {
            sWakeLock.release();
            sWakeLock = null;
        }
    }
}