package com.artv.android.core;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.api.api_model.request.GetTokenRequestObject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by
 * Rogach on 01.07.2015.
 */

@RunWith(AndroidJUnit4.class)
public final class GetTokenRequestObjectTest {

    @Test
    public final void GetTokenRequestObjectBuilder_FieldsMatch() {
        final GetTokenRequestObject.Builder builder = new GetTokenRequestObject.Builder()
                .setUserName("name")
                .setTagID("test")
                .setPassword("111111");

        final GetTokenRequestObject result = builder.build();

        Assert.assertTrue(result.getUserName().equals("name"));
        Assert.assertTrue(result.getTagID().equals("test"));
        Assert.assertTrue(result.getPassword().equals("111111"));
    }
}
