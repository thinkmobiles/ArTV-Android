package com.artv.android.core.config_info;

/**
 * Created by ZOG on 7/6/2015.
 */
public final class ConfigInfo {

    private String mDeviceId;
    private String mMasterDeviceIp;
    private String mUser;
    private String mPassword;

    public final String getDeviceId() {
        return mDeviceId;
    }

    public final void setDeviceId(final String _deviceId) {
        mDeviceId = _deviceId;
    }

    public final String getMasterDeviceIp() {
        return mMasterDeviceIp;
    }

    public final void setMasterDeviceIp(final String _masterDeviceIp) {
        mMasterDeviceIp = _masterDeviceIp;
    }

    public final String getUser() {
        return mUser;
    }

    public final void setUser(final String _user) {
        mUser = _user;
    }

    public final String getPassword() {
        return mPassword;
    }

    public final void setPassword(final String _password) {
        mPassword = _password;
    }

    public final boolean hasConfigInfo() {
        return mDeviceId != null && mMasterDeviceIp != null && mUser != null && mPassword != null;
    }

    @Override
    public final boolean equals(final Object _o) {
        if (!(_o instanceof ConfigInfo)) return false;

        final boolean idE = String.valueOf(mDeviceId).equals(String.valueOf(((ConfigInfo) _o).getDeviceId()));
        final boolean ipE = String.valueOf(mMasterDeviceIp).equals(String.valueOf(((ConfigInfo) _o).getMasterDeviceIp()));
        final boolean userE = String.valueOf(mUser).equals(String.valueOf(((ConfigInfo) _o).getUser()));
        final boolean passwordE = String.valueOf(mPassword).equals(String.valueOf(((ConfigInfo) _o).getPassword()));

        return idE && ipE && userE && passwordE;
    }

    @Override
    public final int hashCode() {
        return 32423 * 4324 << 22;
    }
}
