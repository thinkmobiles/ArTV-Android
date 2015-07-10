package com.artv.android.system;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.artv.android.R;
import com.artv.android.core.api.Temp;
import com.artv.android.core.state.IArTvStateChangeListener;
import com.artv.android.system.fragments.ConfigInfoFragment;
import com.artv.android.system.fragments.MediaPlayerFragment;
import com.artv.android.system.fragments.SplashScreenFragment;

public class MainActivity extends BaseActivity implements IArTvStateChangeListener {
    private FrameLayout mFragmentContainer;

    @Override
    protected final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        getMyApplication().createApplicationLogic();

        mFragmentContainer = (FrameLayout) findViewById(R.id.flFragmentContainer_AM);

        if (_savedInstanceState == null) handleAppState();
    }

    @Override
    protected final void onStart() {
        super.onStart();

        getMyApplication().getApplicationLogic().getStateWorker().addStateChangeListener(this);
    }

    @Override
    protected final void onStop() {
        super.onStop();

        getMyApplication().getApplicationLogic().getStateWorker().removeStateChangeListener(this);
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
        switch (getMyApplication().getApplicationLogic().getStateWorker().getArTvState()) {
            case STATE_APP_START:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new ConfigInfoFragment()).commit();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new SplashScreenFragment()).commit();
                break;

            case STATE_PLAY_MODE:
//                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new MediaPlayerFragment()).commit();
                break;
        }
    }
}
