package com.artv.android.core.api;

import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;

import retrofit.Callback;
import retrofit.http.Body;
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
    @GET("/GetToken.xml")
    void getToken(
//            @Body GetTokenRequestObject _request,
            Callback<GetTokenResponseObject> _callback);

    @GET("/GetCampaign.xml")
    void getCampaign(
//            @Body GetTokenRequestObject _request,
            Callback<GetCampaignResponseObject> _callback);

    @GET("/GetGlobalConfig.xml")
    void getGlobalConfig(
//            @Body String _tokent,
            Callback<GetGlobalConfigResponseObject> _callback);

    @GET("/GetDeviceConfig.xml")
    void getDeviceConfig(
//            @Body GetDeviceConfigRequestObject _requestObject,
            Callback<GetDeviceConfigResponseObject> _callback);

}


