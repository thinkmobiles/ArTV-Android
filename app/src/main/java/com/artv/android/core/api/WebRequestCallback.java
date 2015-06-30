package com.artv.android.core.api;

import com.artv.android.core.api.model.BaseResponseObject;
import com.artv.android.core.api.model.ErrorResponseObject;

/**
 * Created by ZOG on 6/30/2015.
 */
public interface WebRequestCallback<T extends BaseResponseObject> {

    void onSuccess(final T _respObj);
    void onFailure(final ErrorResponseObject _errorResp);

}
