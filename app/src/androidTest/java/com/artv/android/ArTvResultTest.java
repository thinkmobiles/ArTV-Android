package com.artv.android;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 8/11/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class ArTvResultTest {

    @Test
    public final void CreateAndGetFields_FieldsEquals() {
        final ArTvResult.Builder builder = new ArTvResult.Builder()
                .setSuccess(false)
                .setMessage(null);

        final ArTvResult result1 = builder.build();
        Assert.assertEquals(false, result1.getSuccess());
        Assert.assertNull(result1.getMessage());

        builder.setSuccess(true);
        builder.setMessage("yo");
        final ArTvResult result2 = builder.build();
        Assert.assertEquals(true, result2.getSuccess());
        Assert.assertTrue("yo".equals(result2.getMessage()));
    }

}
