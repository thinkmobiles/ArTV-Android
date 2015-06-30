package com.artv.android.core.api;

import com.artv.android.core.api.model.ErrorResponseObject;
import com.artv.android.core.api.model.request.GetTokenRequestObject;
import com.artv.android.core.api.model.response.GetTokenResponseObject;
import com.artv.android.system.MyApplication;

/**
 * Created by ZOG on 6/30/2015.
 */
public class Temp {

    public final void example() {
        final GetTokenRequestObject requestObject = new GetTokenRequestObject();

        final WebRequestCallback<GetTokenResponseObject> callback = new WebRequestCallback<GetTokenResponseObject>() {
            @Override
            public void onSuccess(GetTokenResponseObject _respObj) {

            }

            @Override
            public void onFailure(ErrorResponseObject _errorResp) {

            }
        };

        MyApplication.getApplicationLogic().getApiWorker().doGetToken(requestObject, callback);
    }

}
