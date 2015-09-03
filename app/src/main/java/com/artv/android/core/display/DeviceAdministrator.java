package com.artv.android.core.display;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.system.IMainActivitySleepController;

/**
 * Created by
 * mRogach on 27.08.2015.
 */
public final class DeviceAdministrator {
    private static final int REQUEST_ENABLE = 0;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private IMainActivitySleepController mMainActivitySleepController;
    private StateWorker mStateWorker;
    private Context mContext;
    private TurnOffWorker mTurnOffWorker;

    public DeviceAdministrator (final Context _context) {
        mContext = _context;
        initObjects(mContext);
    }

    public void setMainActivitySleepController(final IMainActivitySleepController _controller) {
        mMainActivitySleepController = _controller;
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
                if (mMainActivitySleepController != null) mMainActivitySleepController.prepareToSleep();
                mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
                mTurnOffWorker.turnOn(_timeTurnOn);
                devicePolicyManager.lockNow();
    }

    private void initObjects(final Context _context) {
        adminComponent = new ComponentName(_context, Darclass.class);
        devicePolicyManager = (DevicePolicyManager) _context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }
}
