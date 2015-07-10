package com.artv.android.core.init;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 7/8/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class InitResultTest {

    @Test
    public final void InitResult_Builder_GetSetWorks() {
        final InitResult result = new InitResult.Builder()
                .setSuccess(true)
                .setMessage("success")
                .build();

        Assert.assertEquals(true, result.getSuccess());
        Assert.assertTrue("success".equals(result.getMessage()));
    }

}
