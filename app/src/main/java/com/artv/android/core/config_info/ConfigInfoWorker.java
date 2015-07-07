package com.artv.android.core.config_info;

import com.artv.android.system.SpHelper;

/**
 * Created by ZOG on 7/6/2015.
 */
public final class ConfigInfoWorker {

    private static final String KEY_DEVICE_ID           = "key_device_id";
    private static final String KEY_MASTER_DEVICE_IP    = "key_master_device_ip";
    private static final String KEY_USER                = "key_user";
    private static final String KEY_PASSWORD            = "key_password";

    private SpHelper mSpHelper;
    private ConfigInfo mConfigInfo;

    public final void setSpHelper(final SpHelper _helper) {
        mSpHelper = _helper;
    }

    public final void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public final ConfigInfo getConfigInfo() {
        return mConfigInfo;
    }

    public final void saveConfigInfo() {
        mSpHelper.putString(KEY_DEVICE_ID, mConfigInfo.getDeviceId());
        mSpHelper.putString(KEY_MASTER_DEVICE_IP, mConfigInfo.getMasterDeviceIp());
        mSpHelper.putString(KEY_USER, mConfigInfo.getUser());
        mSpHelper.putString(KEY_PASSWORD, mConfigInfo.getPassword());
    }

    public final void loadConfigInfo() {
        mConfigInfo = new ConfigInfo.Builder()
                .setDeviceId(mSpHelper.getString(KEY_DEVICE_ID))
                .setMasterDeviceIp(mSpHelper.getString(KEY_MASTER_DEVICE_IP))
                .setUser(mSpHelper.getString(KEY_USER))
                .setPassword(mSpHelper.getString(KEY_PASSWORD))
                .build();
    }

    public final void removeConfigInfo() {
        mConfigInfo = null;
        mSpHelper.removeString(KEY_DEVICE_ID);
        mSpHelper.removeString(KEY_MASTER_DEVICE_IP);
        mSpHelper.removeString(KEY_USER);
        mSpHelper.removeString(KEY_PASSWORD);
    }

}
