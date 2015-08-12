package com.artv.android.core.api;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.BeaconRequestObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetGlobalConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.BeaconResponseObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;
import com.artv.android.core.model.Beacon;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class ModelTest {

    @Test
    public final void CreateModels_ApiTypeSetRight() {
        final BeaconRequestObject beaconRequest                     = new BeaconRequestObject.Builder().build();
        final GetCampaignRequestObject getCampaignRequest           = new GetCampaignRequestObject.Builder().build();
        final GetDeviceConfigRequestObject getDeviceConfigRequest   = new GetDeviceConfigRequestObject.Builder().build();
        final GetGlobalConfigRequestObject getGlobalConfigRequest   = new GetGlobalConfigRequestObject.Builder().build();
        final GetTokenRequestObject getTokenRequest                 = new GetTokenRequestObject.Builder().build();

        final BeaconResponseObject beaconResponse                   = new BeaconResponseObject();
        final GetCampaignResponseObject getCampaignResponse         = new GetCampaignResponseObject();
        final GetDeviceConfigResponseObject getDeviceConfigResponse = new GetDeviceConfigResponseObject();
        final GetGlobalConfigResponseObject getGlobalConfigResponse = new GetGlobalConfigResponseObject();
        final GetTokenResponseObject getTokenResponse               = new GetTokenResponseObject();

        Assert.assertEquals(ApiType.BEACON,                 beaconRequest.apiType);
        Assert.assertEquals(ApiType.GET_CAMPAIGN,           getCampaignRequest.apiType);
        Assert.assertEquals(ApiType.GET_DEVICE_CONFIG,      getDeviceConfigRequest.apiType);
        Assert.assertEquals(ApiType.GET_GLOBAL_CONFIG,      getGlobalConfigResponse.apiType);
        Assert.assertEquals(ApiType.GET_TOKEN,              getTokenRequest.apiType);

        Assert.assertEquals(beaconRequest.apiType, beaconResponse.apiType);
        Assert.assertEquals(getCampaignRequest.apiType, getCampaignResponse.apiType);
        Assert.assertEquals(getDeviceConfigRequest.apiType, getDeviceConfigResponse.apiType);
        Assert.assertEquals(getGlobalConfigRequest.apiType, getGlobalConfigResponse.apiType);
        Assert.assertEquals(getTokenRequest.apiType, getTokenResponse.apiType);
    }



}
