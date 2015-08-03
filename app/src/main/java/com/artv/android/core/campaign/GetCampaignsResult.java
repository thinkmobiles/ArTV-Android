package com.artv.android.core.campaign;

import com.artv.android.core.model.Campaign;

import java.util.List;

/**
 * Created by ZOG on 7/29/2015.
 */
public final class GetCampaignsResult {

    private boolean mSuccess = false;
    private String mMessage;
    private List<Campaign> mCampaigns;

    private GetCampaignsResult() {}

    public final boolean getSuccess() {
        return mSuccess;
    }

    public final String getMessage() {
        return mMessage;
    }

    public final List<Campaign> getCampaigns() {
        return mCampaigns;
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

        public final Builder setCampaigns(final List<Campaign> _campaigns) {
            mGetCampaignsResult.mCampaigns = _campaigns;
            return this;
        }

        public final GetCampaignsResult build() {
            return mGetCampaignsResult;
        }

    }
}
