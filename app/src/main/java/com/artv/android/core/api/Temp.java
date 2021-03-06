package com.artv.android.core.api;

import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;

/**
 * Created by ZOG on 6/30/2015.
 */
public class Temp {

    public final void example() {
        final GetTokenRequestObject requestObject = new GetTokenRequestObject.Builder()
                .setUserName("test")
                .setPassword("1111111")
                .setTagID("355")
                .build();

        final GetCampaignRequestObject campaignRequestObject = new GetCampaignRequestObject.Builder()
                .setCampaignID(12)
                .setToken("ASDFASWQER@!#$!@#$WEQWER")
                .setTagID("5565")
                .build();

        final GetDeviceConfigRequestObject deviceConfigRequestObject = new GetDeviceConfigRequestObject.Builder()
                .setToken("ASDFASWQER@!#$!@#$WEQWER")
                .setTagID("235")
                .build();



//        ArTvApplication.getApplicationLogic().getApiWorker().doGetToken(requestObject, callback);
//        ArTvApplication.getApplicationLogic().getApiWorker().doGetCampaign(campaignRequestObject, callback1);
//        ArTvApplication.getApplicationLogic().getApiWorker().doGetGlobalConfig("ASDFASWQER@!#$!@#$WEQWER", callback2);
//        ArTvApplication.getApplicationLogic().getApiWorker().doGetDeviceConfig(deviceConfigRequestObject, callback2);
    }


    final WebRequestCallback<GetDeviceConfigResponseObject> callback2 = new WebRequestCallback<GetDeviceConfigResponseObject>() {

        @Override
        public void onSuccess(GetDeviceConfigResponseObject _respObj) {

        }

        @Override
        public void onFailure(ErrorResponseObject _errorResp) {

        }
    };
}
