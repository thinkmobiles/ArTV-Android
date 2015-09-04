package com.artv.android.core.display;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;

/**
 * Created by
 * mRogach on 27.08.2015.
 */
public final class DeviceAdministrator {
    private static final int REQUEST_ENABLE = 0;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private StateWorker mStateWorker;
    private Context mContext;
    private TurnOffWorker mTurnOffWorker;

    private DisplayState mDisplayState = DisplayState.STATE_NORMAL;

    public DeviceAdministrator (final Context _context) {
        mContext = _context;
        initObjects(mContext);
    }

    public void setStateWorker(final StateWorker _worker) {
        mStateWorker = _worker;
    }

    public void setTurnOffWorker(final TurnOffWorker _turnOffWorker) {
        this.mTurnOffWorker = _turnOffWorker;
    }

    public void initAdmin(final Activity _activity) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
        _activity.startActivityForResult(intent, REQUEST_ENABLE);
    }

    public boolean isAdmin() {
        return devicePolicyManager.isAdminActive(adminComponent);
    }

    public void lockScreen(final long _timeTurnOn) {
        ArTvLogger.printMessage("Notifying screen off");
        mDisplayState = DisplayState.STATE_TURNING_OFF;

        mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
        mTurnOffWorker.turnOn(_timeTurnOn);
        devicePolicyManager.lockNow();
    }

    public final void notifyScreenTurnedOn() {
        ArTvLogger.printMessage("Notifying screen on");
        mDisplayState = DisplayState.STATE_TURNING_ON;
    }

    public final void resetDisplayStateToNormal() {
        mDisplayState = DisplayState.STATE_NORMAL;
    }

    public final DisplayState getDisplayState() {
        return mDisplayState;
    }

    private void initObjects(final Context _context) {
        adminComponent = new ComponentName(_context, Darclass.class);
        devicePolicyManager = (DevicePolicyManager) _context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

}
