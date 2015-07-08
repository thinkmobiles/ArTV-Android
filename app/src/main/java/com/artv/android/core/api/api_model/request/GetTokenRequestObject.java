package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class GetTokenRequestObject extends BaseRequestObject {

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
