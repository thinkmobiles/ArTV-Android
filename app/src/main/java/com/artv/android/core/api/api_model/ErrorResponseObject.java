package com.artv.android.core.api.api_model;

import com.artv.android.core.api.ApiType;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ErrorResponseObject {

    public ApiType apiType;
    public String error;

    private ErrorResponseObject() {
    }
    public static final class Builder {

        private ErrorResponseObject mErrorResponseObject;

        public Builder() {
            mErrorResponseObject = new ErrorResponseObject();
        }

        public final Builder setError(final String _error) {
            mErrorResponseObject.error = _error;
            return this;
        }

        public final Builder setApiType(final ApiType _apiType) {
            mErrorResponseObject.apiType = _apiType;
            return this;
        }

        public final ErrorResponseObject build() {
            return mErrorResponseObject;
        }
    }

}
