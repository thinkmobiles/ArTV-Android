package com.artv.android.core.campaign.load;

/**
 * Created by ZOG on 7/29/2015.
 */
public class GetCampaignsResult {

    private boolean mSuccess = false;
    private String mMessage;

    private GetCampaignsResult() {}

    public final boolean getSuccess() {
        return mSuccess;
    }

    public final String getMessage() {
        return mMessage;
    }


    public static final class Builder {

        private GetCampaignsResult mGetCampaignsResult;

        public Builder() {
            mGetCampaignsResult = new GetCampaignsResult();
        }

        public final Builder setSuccess(final boolean _success) {
            mGetCampaignsResult.mSuccess = _success;
            return this;
        }

        public final Builder setMessage(final String _message) {
            mGetCampaignsResult.mMessage = _message;
            return this;
        }

        public final GetCampaignsResult build() {
            return mGetCampaignsResult;
        }

    }
}
