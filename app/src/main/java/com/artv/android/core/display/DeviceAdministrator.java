package com.artv.android.core.display;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.artv.android.core.init.InitWorker;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;
import com.artv.android.system.BaseActivity;
import com.artv.android.system.IMainActivitySleepController;

/**
 * Created by
 * mRogach on 27.08.2015.
 */
public final class DeviceAdministrator {
    private static final int REQUEST_ENABLE = 0;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private InitWorker mInitWorker;
    private IMainActivitySleepController mMainActivitySleepController;
    private StateWorker mStateWorker;

    public void setMainActivitySleepController(final IMainActivitySleepController _controller) {
        mMainActivitySleepController = _controller;
    }

    public void setStateWorker(final StateWorker _worker) {
        mStateWorker = _worker;
    }

    public void setInitWorker(final InitWorker _initWorker) {
        mInitWorker = _initWorker;
    }

    public void initAdmin(final Activity _activity) {
        initObjects(_activity);
        if (!devicePolicyManager.isAdminActive(adminComponent)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
            _activity.startActivityForResult(intent, REQUEST_ENABLE);
        }
    }

    public boolean isAdmin() {
        return adminComponent != null && devicePolicyManager != null
                && devicePolicyManager.isAdminActive(adminComponent);
    }

    public void lockScreen(final Context _context, final long _timeTurnOn) {
        if (adminComponent != null && devicePolicyManager != null) {
            if (devicePolicyManager.isAdminActive(adminComponent)) {
                if (mMainActivitySleepController != null) mMainActivitySleepController.prepareToSleep();
                mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);
                Intent i = new Intent(_context, WakeLockService.class);
                i.putExtra("turn_on", _timeTurnOn);
                Log.v("onnnnnnnn", String.valueOf(_timeTurnOn));
                _context.startService(i);
                devicePolicyManager.lockNow();
            }
        }
    }

    public void lockScreen() {
        if (adminComponent != null && devicePolicyManager != null) {
            if (devicePolicyManager.isAdminActive(adminComponent)) {
                if (mMainActivitySleepController != null) mMainActivitySleepController.prepareToSleep();
                mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);

                devicePolicyManager.lockNow();
            }
        }
    }

    private void initObjects(final Context _context) {
        adminComponent = new ComponentName(_context, Darclass.class);
        devicePolicyManager = (DevicePolicyManager) _context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }
}
