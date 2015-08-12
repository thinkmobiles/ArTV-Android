package com.artv.android.core.api.api_model.request;

import com.artv.android.ArTvResult;
import com.artv.android.core.api.ApiConst;
import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;
import com.artv.android.core.api.api_model.IQueryCreator;
import com.artv.android.core.model.Beacon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZOG on 7/10/2015.
 */
public final class BeaconRequestObject extends BaseRequestObject implements IQueryCreator {

    private String mToken;
    private String mTagID;
    private Beacon mBeacon;

    private BeaconRequestObject() {
        apiType = ApiType.BEACON;
    }

    @Override
    public final Map<String, String> getQuery() {
        final Map<String, String> query = new HashMap<>(2);
        query.put(ApiConst.KEY_TOKEN, mToken);
        query.put(ApiConst.KEY_TAG_ID, mTagID);
        return query;
    }

    public final Beacon getBeacon() {
        return mBeacon;
    }

    public static final class Builder {

        private BeaconRequestObject mRequestObject;

        public Builder() {
            mRequestObject = new BeaconRequestObject();
        }

        public final Builder setToken(final String _token) {
            mRequestObject.mToken = _token;
            return this;
        }

        public final Builder setTagID(final String _tagID) {
            mRequestObject.mTagID = _tagID;
            return this;
        }

        public final Builder setBeacon(final Beacon _beacon) {
            mRequestObject.mBeacon = _beacon;
            return this;
        }

        public final BeaconRequestObject build() {
            return mRequestObject;
        }

    }

}
