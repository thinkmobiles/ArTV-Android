package com.artv.android.core.campaign;

import com.artv.android.core.model.Campaign;

import java.util.List;

/**
 * Created by ZOG on 7/29/2015.
 */
public final class CampaignResult {

    private boolean mSuccess = false;
    private String mMessage;
    private List<Campaign> mCampaigns;

    private CampaignResult() {}

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

        private CampaignResult mCampaignResult;

        public Builder() {
            mCampaignResult = new CampaignResult();
        }

        public Builder setSuccess(final boolean _success) {
            mCampaignResult.mSuccess = _success;
            return this;
        }

        public Builder setMessage(final String _message) {
            mCampaignResult.mMessage = _message;
            return this;
        }

        public final Builder setCampaigns(final List<Campaign> _campaigns) {
            mCampaignResult.mCampaigns = _campaigns;
            return this;
        }

        public final CampaignResult build() {
            return mCampaignResult;
        }

    }

}
