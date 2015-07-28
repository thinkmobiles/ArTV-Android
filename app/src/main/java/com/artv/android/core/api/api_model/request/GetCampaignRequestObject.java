package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiConst;
import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;
import com.artv.android.core.api.api_model.IQueryCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
public final class GetCampaignRequestObject extends BaseRequestObject implements IQueryCreator {

    protected String mToken;
    protected String mTagID;
    private int mCampaignID;

    private GetCampaignRequestObject() {
        apiType = ApiType.GET_CAMPAIGN;
    }

    @Override
    public final Map<String, String> getQuery() {
        final Map<String, String> query = new HashMap<>(3);
        query.put(ApiConst.KEY_TOKEN, mToken);
        query.put(ApiConst.KEY_TAG_ID, mTagID);
        query.put(ApiConst.KEY_CAMPAIGN_ID, String.valueOf(mCampaignID));
        return query;
    }

    public static final class Builder {

        private GetCampaignRequestObject mGetCampaignRequestObject;

        public Builder() {
            mGetCampaignRequestObject = new GetCampaignRequestObject();
        }

        public final Builder setToken(final String _token) {
            mGetCampaignRequestObject.mToken = _token;
            return this;
        }

        public final Builder setTagID(final String _tagID) {
            mGetCampaignRequestObject.mTagID = _tagID;
            return this;
        }

        public final Builder setCampaignID(final int _campaignID) {
            mGetCampaignRequestObject.mCampaignID = _campaignID;
            return this;
        }

        public final GetCampaignRequestObject build() {
            return mGetCampaignRequestObject;
        }
    }

}