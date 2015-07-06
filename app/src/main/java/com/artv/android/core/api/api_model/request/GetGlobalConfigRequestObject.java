package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;

/**
 * Created by
 * Rogach on 06.07.2015.
 */
public class GetGlobalConfigRequestObject extends BaseRequestObject {

    private String mToken;

    public GetGlobalConfigRequestObject() {
        apiType = ApiType.GET_GLOBAL_GONFIG;
    }

    public String getToken() {
        return mToken;
    }

    public static final class Builder {

        private GetGlobalConfigRequestObject mGetGlobalConfigRequestObject;

        public Builder() {
            mGetGlobalConfigRequestObject = new GetGlobalConfigRequestObject();
        }

        public final Builder setToken(final String _token) {
            mGetGlobalConfigRequestObject.mToken = _token;
            return this;
        }

        public final GetGlobalConfigRequestObject build() {
            return mGetGlobalConfigRequestObject;
        }
    }

}
