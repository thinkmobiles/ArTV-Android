package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiConst;
import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;
import com.artv.android.core.api.api_model.IQueryCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 * Rogach on 06.07.2015.
 */
public class GetGlobalConfigRequestObject extends BaseRequestObject implements IQueryCreator {

    private String mToken;

    private GetGlobalConfigRequestObject() {
        apiType = ApiType.GET_GLOBAL_GONFIG;
    }

    public String getToken() {
        return mToken;
    }

    @Override
    public final Map<String, String> getQuery() {
        final Map<String, String> query = new HashMap<>(1);
        query.put(ApiConst.KEY_TOKEN, mToken);
        return query;
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
