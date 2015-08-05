package com.artv.android.core.campaign;

import com.artv.android.core.ArTvResult;
import com.artv.android.core.model.Campaign;

import java.util.List;

/**
 * Created by ZOG on 7/29/2015.
 */
public final class GetCampaignsResult extends ArTvResult {

    private List<Campaign> mCampaigns;

    public final List<Campaign> getCampaigns() {
        return mCampaigns;
    }


    public static final class Builder extends ArTvResult.Builder {

        private GetCampaignsResult mGetCampaignsResult;

        public Builder() {
            mGetCampaignsResult = new GetCampaignsResult();
        }

        public final Builder setCampaigns(final List<Campaign> _campaigns) {
            mGetCampaignsResult.mCampaigns = _campaigns;
            return this;
        }

        @Override
        public final GetCampaignsResult build() {
            return mGetCampaignsResult;
        }

    }
}
