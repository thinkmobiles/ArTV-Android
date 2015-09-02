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
    private BaseActivity mActivity;
    private static final int REQUEST_ENABLE = 0;
    private static DevicePolicyManager devicePolicyManager;
    private static ComponentName adminComponent;
    private static volatile DeviceAdministrator instance;
    private static InitWorker mInitWorker;
    private static IMainActivitySleepController mMainActivitySleepController;
    private static StateWorker mStateWorker;

    public DeviceAdministrator(final Activity _activity) {
        mActivity = (BaseActivity) _activity;
        mInitWorker = mActivity.getApplicationLogic().getInitWorker();
    }

    public static DeviceAdministrator getInstance(final Activity _activity) {
        if (instance == null) {
            instance = new DeviceAdministrator(_activity);
    }
        return instance;
    }

    public static void setMainActivitySleepController(final IMainActivitySleepController _controller) {
        mMainActivitySleepController = _controller;
    }

    public static void setStateWorker(final StateWorker _worker) {
        mStateWorker = _worker;
    }

    public void initAdmin() {
        initObjects(mActivity);
        if (!devicePolicyManager.isAdminActive(adminComponent)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
            mActivity.startActivityForResult(intent, REQUEST_ENABLE);
        }
    }

    public static boolean isAdmin() {
        return adminComponent != null && devicePolicyManager != null
                && devicePolicyManager.isAdminActive(adminComponent);
    }

    public static void lockScreen(final Context _context, final long _timeTurnOn) {
//        initObjects(_context);
        if (adminComponent != null && devicePolicyManager != null) {
            if (devicePolicyManager.isAdminActive(adminComponent)) {
                Intent i = new Intent(_context, WakeLockService.class);
                i.putExtra("turn_on", _timeTurnOn);
                Log.v("onnnnnnnn", String.valueOf(_timeTurnOn));
                _context.startService(i);
                devicePolicyManager.lockNow();
            }
        }
    }

    public static void lockScreen(final Context _context) {
//        initObjects(_context);
        if (adminComponent != null && devicePolicyManager != null) {
            if (devicePolicyManager.isAdminActive(adminComponent)) {
                if (mMainActivitySleepController != null) mMainActivitySleepController.prepareToSleep();
                mStateWorker.setState(ArTvState.STATE_APP_START_WITH_CONFIG_INFO);

                devicePolicyManager.lockNow();
            }
        }
    }

    private static void initObjects(final Context _context) {
        adminComponent = new ComponentName(_context, Darclass.class);
        devicePolicyManager = (DevicePolicyManager) _context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }
}
