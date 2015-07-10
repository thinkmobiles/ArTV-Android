package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
public final class GetCampaignRequestObject extends BaseRequestObject {

    protected String mToken;
    protected String mTagID;
    private long mCampaignID;

    private GetCampaignRequestObject() {
        apiType = ApiType.GET_CAMPAIGN;
    }

    public static final class Builder {

        private GetCampaignRequestObject mGetCampaignRequestObject;

        public Builder() {
            mGetCampaignRequestObject = new GetCampaignRequestObject();
        }

        public final Builder setCampaignID(final long _campaignID) {
            mGetCampaignRequestObject.mCampaignID = _campaignID;
            return this;
        }

        public final Builder setToken(final String _token) {
            mGetCampaignRequestObject.mToken = _token;
            return this;
        }

        public final Builder setTagID(final String _tagID) {
            mGetCampaignRequestObject.mTagID = _tagID;
            return this;
        }

        public final GetCampaignRequestObject build() {
            return mGetCampaignRequestObject;
        }
    }

}