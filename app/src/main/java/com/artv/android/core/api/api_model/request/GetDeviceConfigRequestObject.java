package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
public class GetDeviceConfigRequestObject extends BaseRequestObject {

    protected String mToken;
    protected String mTagID;

    public GetDeviceConfigRequestObject() {
        apiType = ApiType.GET_DEVICE_CONFIG;
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
