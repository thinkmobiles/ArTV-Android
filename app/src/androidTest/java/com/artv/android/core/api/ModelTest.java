package com.artv.android.core.api;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;

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
        final GetTokenRequestObject getTokenRequest                 = new GetTokenRequestObject();
        final GetDeviceConfigRequestObject getDeviceConfigRequest   = new GetDeviceConfigRequestObject();
        final GetCampaignRequestObject getCampaignRequest           = new GetCampaignRequestObject();
        final GetTokenResponseObject getTokenResponse               = new GetTokenResponseObject();
        final GetDeviceConfigResponseObject getDeviceConfigResponse = new GetDeviceConfigResponseObject();
        final GetCampaignResponseObject getCampaignResponse         = new GetCampaignResponseObject();
        final GetGlobalConfigResponseObject getGlobalConfigResponse = new GetGlobalConfigResponseObject();

        Assert.assertEquals(ApiType.GET_TOKEN, getTokenRequest.apiType);
        Assert.assertEquals(ApiType.GET_DEVICE_CONFIG, getDeviceConfigRequest.apiType);
        Assert.assertEquals(ApiType.GET_CAMPAIGN, getCampaignRequest.apiType);
        Assert.assertEquals(ApiType.GET_GLOBAL_GONFIG, getGlobalConfigResponse.apiType);

        Assert.assertEquals(getTokenRequest.apiType, getTokenResponse.apiType);
        Assert.assertEquals(getDeviceConfigRequest.apiType, getDeviceConfigResponse.apiType);
        Assert.assertEquals(getCampaignRequest.apiType, getCampaignResponse.apiType);
    }

}
