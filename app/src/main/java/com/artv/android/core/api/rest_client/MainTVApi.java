package com.artv.android.core.api.rest_client;

import com.artv.android.core.api.ApiConst;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetGlobalConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;


/**
 * Created by
 * Rogach on 01.07.2015.
 */

public interface MainTVApi {

    @Headers({
            "Accept: application/xml",
            "Content-Type: application/xml"})
    @GET(ApiConst.PATH_GET_TOKEN)
    void getToken(
//            @Body GetTokenRequestObject _request,
            Callback<GetTokenResponseObject> _callback);

    @GET(ApiConst.PATH_GET_CAMPAIGN)
    void getCampaign(
//            @Body GetTokenRequestObject _request,
            Callback<GetCampaignResponseObject> _callback);

    @GET(ApiConst.PATH_GET_GLOBAL_CONFIG)
    void getGlobalConfig(
//            @Body GetGlobalConfigRequestObject _request,
            Callback<GetGlobalConfigResponseObject> _callback);

    @GET(ApiConst.PATH_GET_DEVICE_CONFIG)
    void getDeviceConfig(
//            @Body GetDeviceConfigRequestObject _requestObject,
            Callback<GetDeviceConfigResponseObject> _callback);

}


