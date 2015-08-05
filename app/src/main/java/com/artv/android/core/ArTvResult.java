package com.artv.android.core;

/**
 * General class for delivering result of an operation.
 *
 * Created by ZOG on 8/5/2015.
 */
public class ArTvResult {

    private boolean mSuccess = false;
    private String mMessage;

    protected ArTvResult() {}

    public final boolean getSuccess() {
        return mSuccess;
    }

    public final String getMessage() {
        return mMessage;
    }


    public static class Builder {

        private ArTvResult mArTvResult;

        public Builder() {
            mArTvResult = new ArTvResult();
        }

        public final Builder setSuccess(final boolean _success) {
            mArTvResult.mSuccess = _success;
            return this;
        }

        public final Builder setMessage(final String _message) {
            mArTvResult.mMessage = _message;
            return this;
        }

        public ArTvResult build() {
            return mArTvResult;
        }

    }


}
