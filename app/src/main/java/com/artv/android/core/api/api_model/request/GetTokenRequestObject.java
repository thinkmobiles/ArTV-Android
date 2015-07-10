package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiConst;
import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;
import com.artv.android.core.api.api_model.IQueryCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class GetTokenRequestObject extends BaseRequestObject implements IQueryCreator {

    private String mUserName;
    private String mPassword;
    private String mTagID;

    private GetTokenRequestObject() {
        apiType = ApiType.GET_TOKEN;

    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getTagID() {
        return mTagID;
    }

    @Override
    public final Map<String, String> getQuery() {
        final Map<String, String> query = new HashMap<>(3);
        query.put(ApiConst.KEY_USERNAME, mUserName);
        query.put(ApiConst.KEY_PASSWORD, mPassword);
        query.put(ApiConst.KEY_TAG_ID, mTagID);
        return query;
    }

    public static final class Builder {

        private GetTokenRequestObject mGetTokenRequestObject;

        public Builder() {
            mGetTokenRequestObject = new GetTokenRequestObject();
        }

        public final Builder setUserName(final String _userName) {
            mGetTokenRequestObject.mUserName = _userName;
            return this;
        }

        public final Builder setPassword(final String _password) {
            mGetTokenRequestObject.mPassword = _password;
            return this;
        }

        public final Builder setTagID(final String _tagID) {
            mGetTokenRequestObject.mTagID = _tagID;
            return this;
        }

        public final GetTokenRequestObject build() {
            return mGetTokenRequestObject;
        }
    }
}
