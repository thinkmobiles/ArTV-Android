package com.artv.android.core.api;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.api.model.request.GetTokenRequestObject;
import com.artv.android.core.api.model.response.GetTokenResponseObject;

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
        final GetTokenRequestObject getTokenRequest = new GetTokenRequestObject();
        final GetTokenResponseObject getTokenResponse = new GetTokenResponseObject();
        Assert.assertEquals(ApiType.GET_TOKEN, getTokenRequest.apiType);
        Assert.assertEquals(getTokenRequest.apiType, getTokenResponse.apiType);
    }

}
