package com.artv.android.core.api;

import android.content.Context;

import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ApiWorker {

    private Context mContext;

    public ApiWorker(final Context _context) {
        mContext = _context;
    }

    public final void doGetToken(final GetTokenRequestObject _requestObject,
                                 final WebRequestCallback<GetTokenResponseObject> _callback) {
        //todo: implement
//        TestRestClient.getApiService().getToken(mytestCallback);
    }

    public final void doGetGlobalConfig(final String _tokent,
                                 final WebRequestCallback<GetGlobalConfigResponseObject> _callback) {
        //todo: implement
//        TestRestClient.getApiService().getGlobalConfig(callback2);
    }

    public final void doGetDeviceConfig(final GetDeviceConfigRequestObject _requestObject,
                                        final WebRequestCallback<GetDeviceConfigResponseObject> _callback) {
        //todo: implement
//        TestRestClient.getApiService().getDeviceConfig(callback3);
    }

    public final void doGetCampaign(final GetCampaignRequestObject _requestObject,
                                        final WebRequestCallback<GetCampaignResponseObject> _callback) {
        //todo: implement

//        TestRestClient.getApiService().getCampaign(mytestCallback1);
    }

}
