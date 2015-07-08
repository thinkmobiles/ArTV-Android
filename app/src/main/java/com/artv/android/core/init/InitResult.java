package com.artv.android.core.init;

/**
 * Created by ZOG on 7/8/2015.
 */
public final class InitResult {

    private boolean mSuccess = false;
    private String mMessage;

    private InitResult() {}

    public final boolean getSuccess() {
        return mSuccess;
    }

    public final String getMessage() {
        return mMessage;
    }


    public static final class Builder {

        private InitResult mInitResult;

        public Builder() {
            mInitResult = new InitResult();
        }

        public final Builder setSuccess(final boolean _success) {
            mInitResult.mSuccess = _success;
            return this;
        }

        public final Builder setMessage(final String _message) {
            mInitResult.mMessage = _message;
            return this;
        }

        public final InitResult build() {
            return mInitResult;
        }

    }

}
