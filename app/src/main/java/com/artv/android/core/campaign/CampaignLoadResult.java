package com.artv.android.core.campaign;

/**
 * Created by ZOG on 7/28/2015.
 */
public final class CampaignLoadResult {

    private boolean mSuccess = false;
    private String mMessage;

    private CampaignLoadResult() {}

    public final boolean getSuccess() {
        return mSuccess;
    }

    public final String getMessage() {
        return mMessage;
    }


    public static final class Builder {

        private CampaignLoadResult mCampaignLoadResult;

        public Builder() {
            mCampaignLoadResult = new CampaignLoadResult();
        }

        public final Builder setSuccess(final boolean _success) {
            mCampaignLoadResult.mSuccess = _success;
            return this;
        }

        public final Builder setMessage(final String _message) {
            mCampaignLoadResult.mMessage = _message;
            return this;
        }

        public final CampaignLoadResult build() {
            return mCampaignLoadResult;
        }

    }
}
