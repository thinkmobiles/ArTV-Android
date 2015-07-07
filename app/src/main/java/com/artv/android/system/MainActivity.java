package com.artv.android.system;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.artv.android.R;
import com.artv.android.core.IArTvStateChangeListener;
import com.artv.android.core.api.Temp;
import com.artv.android.system.fragments.ConfigInfoFragment;
import com.artv.android.system.fragments.SplashScreenFragment;

public class MainActivity extends Activity implements IArTvStateChangeListener {
    private FrameLayout mFragmentContainer;

    @Override
    protected final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentContainer = (FrameLayout) findViewById(R.id.flFragmentContainer_MA);

        handleAppState();
    }

    @Override
    protected final void onStart() {
        super.onStart();

        MyApplication.getApplicationLogic().addStateChangeListener(this);
    }

    @Override
    protected final void onStop() {
        super.onStop();

        MyApplication.getApplicationLogic().removeStateChangeListener(this);
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
        switch (MyApplication.getApplicationLogic().getArTvState()) {
            case STATE_APP_START:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_MA, new ConfigInfoFragment()).commit();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_MA, new SplashScreenFragment()).commit();
                break;
        }
    }
}
