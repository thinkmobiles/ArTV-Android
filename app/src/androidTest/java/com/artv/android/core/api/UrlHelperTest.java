package com.artv.android.core.api;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.UrlHelper;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 8/5/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class UrlHelperTest {

    @Test
    public final void ValidatingUrl() {
        Assert.assertTrue(UrlHelper.isValidAddress("http://yo.com"));
        Assert.assertTrue(UrlHelper.isValidAddress("http://yo.com/"));
        Assert.assertTrue(UrlHelper.isValidAddress("http://yo.com/fasdf?fsdfad=4234&fdsf=432"));
        Assert.assertTrue(UrlHelper.isValidAddress("http://to.com:344"));
        Assert.assertTrue(UrlHelper.isValidAddress("http://to.com:344/"));
        Assert.assertTrue(UrlHelper.isValidAddress("http://to.com:344/fasdf?fsdfad=4234&fdsf=432"));
        Assert.assertTrue(UrlHelper.isValidAddress("ftp://to.com"));

        Assert.assertFalse(UrlHelper.isValidAddress(null));
        Assert.assertFalse(UrlHelper.isValidAddress(""));
        Assert.assertFalse(UrlHelper.isValidAddress("4rdsfdsfasdg"));
        Assert.assertFalse(UrlHelper.isValidAddress("to.com"));
        Assert.assertFalse(UrlHelper.isValidAddress("http"));
        Assert.assertFalse(UrlHelper.isValidAddress("http:"));
        Assert.assertFalse(UrlHelper.isValidAddress("http://"));
    }

    @Test
    public final void GetComponentsFromUrl() {
        Assert.assertTrue("http".equals(UrlHelper.getProtocolFrom("http://yo.com/")));
        Assert.assertTrue("ftp".equals(UrlHelper.getProtocolFrom("ftp://yo.com")));
        Assert.assertTrue("ftp".equals(UrlHelper.getProtocolFrom("ftp://yo.com:3254/")));

        Assert.assertTrue("yo.com".equals(UrlHelper.getAuthorityFrom("ftp://yo.com/")));
        Assert.assertTrue("yo.com:4324".equals(UrlHelper.getAuthorityFrom("ftp://yo.com:4324/")));
    }

}
