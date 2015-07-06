package com.artv.android.system;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.artv.android.R;
import com.artv.android.core.ArTvState;
import com.artv.android.core.api.Temp;
import com.artv.android.system.fragments.ConfigsFragment;

public class MainActivity extends Activity {
    private FrameLayout mFragmentContainer;

    @Override
    protected final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentContainer = (FrameLayout) findViewById(R.id.flFragmentContainer_MA);

        handleAppState();
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

    private final void handleAppState() {
        if (ArTvState.STATE_APP_START == MyApplication.getApplicationLogic().getArTvState()) {
            getFragmentManager().beginTransaction().add(R.id.flFragmentContainer_MA, new ConfigsFragment()).commit();
        }
    }

}
