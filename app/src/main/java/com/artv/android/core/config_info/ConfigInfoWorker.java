package com.artv.android.core.config_info;

import com.artv.android.core.state.StateWorker;
import com.artv.android.system.SpHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZOG on 7/6/2015.
 */
public class ConfigInfoWorker {

    private static final String KEY_DEVICE_ID           = "key_device_id";
    private static final String KEY_MASTER_DEVICE_IP    = "key_master_device_ip";
    private static final String KEY_ADDRESS             = "key_address";
    private static final String KEY_USER                = "key_user";
    private static final String KEY_PASSWORD            = "key_password";
    private static final String KEY_SHOW_DEBUG_INFO     = "key_show_debug_info";

    private ConfigInfo mConfigInfo;
    private SpHelper mSpHelper;
    private StateWorker mStateWorker;

    private Set<IConfigInfoListener> mConfigInfoListeners;

    public ConfigInfoWorker() {
        mConfigInfoListeners = new HashSet<>();
    }

    public final void setSpHelper(final SpHelper _helper) {
        mSpHelper = _helper;
    }

    public final void setStateWorker(final StateWorker _stateWorker) {
        mStateWorker = _stateWorker;
    }

    public final void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public final ConfigInfo getConfigInfo() {
        return mConfigInfo;
    }

    public final boolean addConfigInfoListener(final IConfigInfoListener _listener) {
        return mConfigInfoListeners.add(_listener);
    }

    public final boolean removeConfigInfoListener(final IConfigInfoListener _listener) {
        return mConfigInfoListeners.remove(_listener);
    }

    public final void notifyConfigInfoLoaded() {
        for (final IConfigInfoListener listener : mConfigInfoListeners) listener.onConfigInfoLoaded(mConfigInfo);
    }

    public final void saveConfigInfo() {
        mSpHelper.putString(KEY_DEVICE_ID, mConfigInfo.getDeviceId());
        mSpHelper.putString(KEY_MASTER_DEVICE_IP, mConfigInfo.getMasterDeviceIp());
        mSpHelper.putString(KEY_ADDRESS, mConfigInfo.getAddress());
        mSpHelper.putString(KEY_USER, mConfigInfo.getUser());
        mSpHelper.putString(KEY_PASSWORD, mConfigInfo.getPassword());
        mSpHelper.putBoolean(KEY_SHOW_DEBUG_INFO, mConfigInfo.getShowDebugInfo());
    }

    public final void loadConfigInfo() {
        mConfigInfo = new ConfigInfo.Builder()
                .setDeviceId(mSpHelper.getString(KEY_DEVICE_ID))
                .setMasterDeviceIp(mSpHelper.getString(KEY_MASTER_DEVICE_IP))
                .setAddress(mSpHelper.getString(KEY_ADDRESS))
                .setUser(mSpHelper.getString(KEY_USER))
                .setPassword(mSpHelper.getString(KEY_PASSWORD))
                .setShowDebugInfo(mSpHelper.getBoolean(KEY_SHOW_DEBUG_INFO))
                .build();
    }

    public final void removeConfigInfo() {
        mConfigInfo = null;
        mSpHelper.removeItem(KEY_DEVICE_ID);
        mSpHelper.removeItem(KEY_MASTER_DEVICE_IP);
        mSpHelper.removeItem(KEY_ADDRESS);
        mSpHelper.removeItem(KEY_USER);
        mSpHelper.removeItem(KEY_PASSWORD);
        mSpHelper.removeItem(KEY_SHOW_DEBUG_INFO);
    }

}
