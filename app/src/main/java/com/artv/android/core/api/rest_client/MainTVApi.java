package com.artv.android.core.api.rest_client;

import com.artv.android.core.api.ApiConst;
import com.artv.android.core.api.api_model.response.BeaconResponseObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;
import com.artv.android.core.model.Beacon;
import com.artv.android.core.model.DeviceConfig;
import com.artv.android.core.model.GlobalConfig;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;


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
            @QueryMap() Map<String, String> _query,
            Callback<GetTokenResponseObject> _callback);

    @GET(ApiConst.PATH_GET_GLOBAL_CONFIG)
    void getGlobalConfig(
            @QueryMap() Map<String, String> _query,
            Callback<GlobalConfig> _callback);

    @GET(ApiConst.PATH_GET_DEVICE_CONFIG)
    void getDeviceConfig(
            @QueryMap() Map<String, String> _query,
            Callback<DeviceConfig> _callback);

    @POST(ApiConst.PATH_BEACON)
    void beacon(
            @QueryMap() Map<String, String> _query,
            @Body Beacon _beacon,
            Callback<BeaconResponseObject> _callback);

    @GET(ApiConst.PATH_GET_CAMPAIGN)
    void getCampaign(
            @QueryMap() Map<String, String> _query,
            Callback<GetCampaignResponseObject> _callback);

}


