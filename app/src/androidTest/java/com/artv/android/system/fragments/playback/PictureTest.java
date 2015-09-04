package com.artv.android.system.fragments.playback;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 9/4/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class PictureTest {

    @Test
    public final void ScalingImageValue() {
        //container
        final int cWidth = 100;
        final int cHeight = 100;
        //image
        final int iWidth = 1920;
        final int iHeight = 1080;

        final int scale = PictureHelper.getScale(new Size(cWidth, cHeight), new Size(iWidth, iHeight));

        Assert.assertEquals(8, scale);
    }

}
