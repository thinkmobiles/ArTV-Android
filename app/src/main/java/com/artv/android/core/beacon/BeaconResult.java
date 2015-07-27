package com.artv.android.core.beacon;

/**
 * Created by ZOG on 7/8/2015.
 */
public final class BeaconResult {

    private boolean mSuccess = false;
    private String mMessage;

    private BeaconResult() {}

    public final boolean getSuccess() {
        return mSuccess;
    }

    public final String getMessage() {
        return mMessage;
    }


    public static final class Builder {

        private BeaconResult mBeaconResult;

        public Builder() {
            mBeaconResult = new BeaconResult();
        }

        public final Builder setSuccess(final boolean _success) {
            mBeaconResult.mSuccess = _success;
            return this;
        }

        public final Builder setMessage(final String _message) {
            mBeaconResult.mMessage = _message;
            return this;
        }

        public final BeaconResult build() {
            return mBeaconResult;
        }

    }

}
