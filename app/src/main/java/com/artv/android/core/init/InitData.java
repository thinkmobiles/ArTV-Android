package com.artv.android.core.init;

import com.artv.android.core.model.DeviceConfig;
import com.artv.android.core.model.Entry;
import com.artv.android.core.model.GlobalConfig;

import java.util.ArrayList;

/**
 * Created by ZOG on 7/8/2015.
 */
public final class InitData {

    private String mToken;
    private GlobalConfig mGlobalConfig;
    private DeviceConfig mDeviceConfig;

    public final String getToken() {
        return mToken;
    }

    public final void setToken(final String _token) {
        mToken = _token;
    }

    public final GlobalConfig getGlobalConfig() {
        return mGlobalConfig;
    }

    public final void setGlobalConfig(final GlobalConfig _globalConfig) {
        mGlobalConfig = _globalConfig;
    }

    public final DeviceConfig getDeviceConfig() {
        return mDeviceConfig;
    }

    public final void setDeviceConfig(final DeviceConfig _deviceConfig) {
        mDeviceConfig = _deviceConfig;
    }
}
