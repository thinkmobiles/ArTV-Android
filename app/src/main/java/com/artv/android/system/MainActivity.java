package com.artv.android.system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.artv.android.R;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.display.AlarmAlertWakeLock;
import com.artv.android.core.display.DeviceAdministrator;
import com.artv.android.core.state.IArTvStateChangeListener;
import com.artv.android.core.state.StateWorker;
import com.artv.android.system.fragments.ConfigInfoFragment;
import com.artv.android.system.fragments.playback.PlaybackFragment;
import com.artv.android.system.fragments.splash.SplashScreenFragment;

public class MainActivity extends BaseActivity implements IArTvStateChangeListener,
        IMainActivityProceedListener, IMainActivitySleepController {
    private static final int REQUEST_ENABLE = 0;
    private FrameLayout mFragmentContainer;

    private StateWorker mStateWorker;
    private ConfigInfoWorker mConfigInfoWorker;
    private DeviceAdministrator mDeviceAdministrator;

    @Override
    protected final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        //does not create ApplicationLogic each time activity recreating (i.e. rotate, screen lock etc)
        if (getApplicationLogic() == null) getMyApplication().createApplicationLogic();
        getApplicationLogic().determineStateWhenAppStart();
        initLogic();

        mFragmentContainer = (FrameLayout) findViewById(R.id.flFragmentContainer_AM);

        //don't replace existing fragment when recreating
        if (_savedInstanceState == null || !hasFragment()) getAdminStatusAndHandleAppState();
//        if (_savedInstanceState == null || !hasFragment()) handleAppState();
    }

    private final void initLogic() {
        mStateWorker = getApplicationLogic().getStateWorker();
        mConfigInfoWorker = getApplicationLogic().getConfigInfoWorker();
        mDeviceAdministrator = getApplicationLogic().getDeviceAdministrator();
    }

    @Override
    protected final void onStart() {
        super.onStart();
        mStateWorker.addStateChangeListener(this);
        mDeviceAdministrator.setMainActivitySleepController(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AlarmAlertWakeLock.release();
    }

    @Override
    protected final void onStop() {
        super.onStop();
        mStateWorker.removeStateChangeListener(this);
        mDeviceAdministrator.setMainActivitySleepController(null);
    }

    //todo: use it when recreate after sleep (with no fragment)?
    private final boolean hasFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer_AM) != null;
    }

    @Override
    public final void onArTvStateChanged() {
        handleAppState();
    }

    private final void handleAppState() {
        switch (mStateWorker.getArTvState()) {
            case STATE_APP_START:
                final ConfigInfoFragment fragment = new ConfigInfoFragment();
                fragment.setMainActivityProceedListener(this);
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, fragment).commit();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new SplashScreenFragment()).commit();
                break;

            case STATE_PLAY_MODE:
                getApplicationLogic().getTurnOffWorker().turnOff();
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new PlaybackFragment()).commit();
                break;
        }
    }

    @Override
    public final void proceedToSplashFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new SplashScreenFragment()).commit();
    }

    @Override
    public final void prepareToSleep() {
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer_AM);
        if (fragment == null) return;
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        getSupportFragmentManager().executePendingTransactions();
    }

    private void getAdminStatusAndHandleAppState() {
        if (!mDeviceAdministrator.isAdmin()) {
            mDeviceAdministrator.initAdmin(this);
        } else {
            handleAppState();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                if (requestCode == REQUEST_ENABLE) {
                    handleAppState();
                }
                break;
            case Activity.RESULT_CANCELED:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new MaterialDialog.Builder(this)
                .backgroundColorRes(android.R.color.white)
                .title(R.string.app_name)
                .titleColorRes(android.R.color.black)
                .content(getString(R.string.admin_root))
                .contentColorRes(android.R.color.black)
                .positiveText(R.string.admin_retry)
                .negativeText(R.string.app_exit)
                .cancelable(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        getAdminStatusAndHandleAppState();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        finish();
                        System.exit(0);
                    }
                })
                .show();
    }
}
