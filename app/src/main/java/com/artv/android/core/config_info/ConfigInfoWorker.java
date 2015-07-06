package com.artv.android.core.config_info;

import com.artv.android.system.SpHelper;

import java.util.HashSet;
import java.util.Set;

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
    private Set<IConfigInfoProvider> mConfigInfoProviders;

    public ConfigInfoWorker() {
        mConfigInfoProviders = new HashSet<>();
    }

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
        mConfigInfo = new ConfigInfo();
        mConfigInfo.setDeviceId(mSpHelper.getString(KEY_DEVICE_ID));
        mConfigInfo.setMasterDeviceIp(mSpHelper.getString(KEY_MASTER_DEVICE_IP));
        mConfigInfo.setUser(mSpHelper.getString(KEY_USER));
        mConfigInfo.setPassword(mSpHelper.getString(KEY_PASSWORD));
    }

    public final boolean addConfigInfoProvider(final IConfigInfoProvider _provider) {
        return mConfigInfoProviders.add(_provider);
    }

    public final boolean removeConfigInfoProvider(final IConfigInfoProvider _provider) {
        return mConfigInfoProviders.remove(_provider);
    }

}
