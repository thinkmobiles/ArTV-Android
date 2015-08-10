package com.artv.android.system;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.artv.android.R;
import com.artv.android.core.api.Temp;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.config_info.IConfigInfoListener;
import com.artv.android.core.state.IArTvStateChangeListener;
import com.artv.android.core.state.StateWorker;
import com.artv.android.system.fragments.ConfigInfoFragment;
import com.artv.android.system.fragments.MediaPlayerFragment;
import com.artv.android.system.fragments.splash.SplashScreenFragment;

public class MainActivity extends BaseActivity implements IArTvStateChangeListener, IConfigInfoListener {

    private FrameLayout mFragmentContainer;

    private StateWorker mStateWorker;
    private ConfigInfoWorker mConfigInfoWorker;

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
        if (_savedInstanceState == null) handleAppState();
    }

    private final void initLogic() {
        mStateWorker = getApplicationLogic().getStateWorker();
        mConfigInfoWorker = getApplicationLogic().getConfigInfoWorker();
    }

    @Override
    protected final void onStart() {
        super.onStart();
        mStateWorker.addStateChangeListener(this);
        mConfigInfoWorker.addConfigInfoListener(this);
    }

    @Override
    protected final void onStop() {
        super.onStop();
        mStateWorker.removeStateChangeListener(this);
        mConfigInfoWorker.removeConfigInfoListener(this);
    }

    private void getDeviceId() {
        String deviceId = null;
        if (TextUtils.isEmpty(deviceId))
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.v("deviceId", deviceId);
    }

    private void checkTestApi() {
        Temp temp = new Temp();
        temp.example();
    }

    @Override
    public final void onArTvStateChanged() {
        handleAppState();
    }

    private final void handleAppState() {
        switch (mStateWorker.getArTvState()) {
            case STATE_APP_START:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new ConfigInfoFragment()).commit();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new SplashScreenFragment()).commit();
                break;

            case STATE_PLAY_MODE:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new MediaPlayerFragment()).commit();
                break;
        }
    }

    @Override
    public final void onEnteredConfigInfo(final ConfigInfo _configInfo) {
        getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new SplashScreenFragment()).commit();
    }

    @Override
    public final void onNeedRemoveConfigInfo() {}
}
