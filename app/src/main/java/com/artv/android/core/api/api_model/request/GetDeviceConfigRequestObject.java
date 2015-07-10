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
public final class GetDeviceConfigRequestObject extends BaseRequestObject implements IQueryCreator {

    protected String mToken;
    protected String mTagID;

    protected GetDeviceConfigRequestObject() {
        apiType = ApiType.GET_DEVICE_CONFIG;
    }

    @Override
    public final Map<String, String> getQuery() {
        final Map<String, String> query = new HashMap<>(2);
        query.put(ApiConst.KEY_TOKEN, mToken);
        query.put(ApiConst.KEY_TAG_ID, mTagID);
        return query;
    }

    public static class Builder {

        protected GetDeviceConfigRequestObject mGetDeviceConfigRequestObject;

        public Builder() {
            mGetDeviceConfigRequestObject = new GetDeviceConfigRequestObject();
        }

        public Builder setToken(final String _token) {
            mGetDeviceConfigRequestObject.mToken = _token;
            return this;
        }

        public Builder setTagID(final String _tagID) {
            mGetDeviceConfigRequestObject.mTagID = _tagID;
            return this;
        }

        public GetDeviceConfigRequestObject build() {
            return mGetDeviceConfigRequestObject;
        }
    }
}
