package com.artv.android.core.config_info;

/**
 * Created by ZOG on 7/6/2015.
 */
public class ConfigInfo {

    private String mDeviceId;
    private String mMasterDeviceIp;
    private String mAddress;
    private String mUser;
    private String mPassword;

    /**
     * Construct with Builder.
     */
    private ConfigInfo() {}

    public final String getDeviceId() {
        return mDeviceId;
    }

    public final String getMasterDeviceIp() {
        return mMasterDeviceIp;
    }

    public final String getAddress() {
        return mAddress;
    }

    public final String getUser() {
        return mUser;
    }

    public final String getPassword() {
        return mPassword;
    }

    public boolean hasConfigInfo() {
        return mDeviceId != null && !mDeviceId.isEmpty()
                && mUser != null && !mUser.isEmpty()
                && mPassword != null && !mPassword.isEmpty();
    }

    @Override
    public final boolean equals(final Object _o) {
        if (_o == null) return false;
        if (!(_o instanceof ConfigInfo)) return false;

        final boolean idE = String.valueOf(mDeviceId).equals(String.valueOf(((ConfigInfo) _o).getDeviceId()));
        final boolean ipE = String.valueOf(mMasterDeviceIp).equals(String.valueOf(((ConfigInfo) _o).getMasterDeviceIp()));
        final boolean aE = String.valueOf(mAddress).equals(String.valueOf(((ConfigInfo) _o).getAddress()));
        final boolean uE = String.valueOf(mUser).equals(String.valueOf(((ConfigInfo) _o).getUser()));
        final boolean pE = String.valueOf(mPassword).equals(String.valueOf(((ConfigInfo) _o).getPassword()));

        return idE && ipE && aE && uE && pE;
    }

    @Override
    public final int hashCode() {
        return 32423 * 4324 << 22;
    }


    public static class Builder {

        private ConfigInfo mConfigInfo;

        public Builder() {
            mConfigInfo = new ConfigInfo();
        }

        public final Builder setDeviceId(final String _deviceId) {
            mConfigInfo.mDeviceId = _deviceId;
            return this;
        }

        public final Builder setMasterDeviceIp(final String _masterDeviceIp) {
            mConfigInfo.mMasterDeviceIp = _masterDeviceIp;
            return this;
        }

        public final Builder setAddress(final String _address) {
            mConfigInfo.mAddress = _address;
            return this;
        }

        public final Builder setUser(final String _user) {
            mConfigInfo.mUser = _user;
            return this;
        }

        public final Builder setPassword(final String _password) {
            mConfigInfo.mPassword = _password;
            return this;
        }

        public final ConfigInfo build() {
            return mConfigInfo;
        }

    }

}
