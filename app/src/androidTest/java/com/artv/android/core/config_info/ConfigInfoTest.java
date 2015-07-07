package com.artv.android.core.config_info;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 7/7/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class ConfigInfoTest {

    @Test
    public final void ConfigInfoBuilder_BuildsCorrectly() {
        final ConfigInfo ci = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        Assert.assertEquals(ci.getDeviceId(), "id");
        Assert.assertEquals(ci.getMasterDeviceIp(), "ip");
        Assert.assertEquals(ci.getUser(), "user");
        Assert.assertEquals(ci.getPassword(), "password");
    }

    @Test
    public final void ConfigInfoObject_HasConfigInfo_TrueOnlyIfAllDataFilled() {
        final ConfigInfo.Builder builder = new ConfigInfo.Builder();
        Assert.assertFalse(builder.build().hasConfigInfo());
        builder.setDeviceId("id");
        Assert.assertFalse(builder.build().hasConfigInfo());
        builder.setMasterDeviceIp("ip");
        Assert.assertFalse(builder.build().hasConfigInfo());
        builder.setUser("user");
        Assert.assertFalse(builder.build().hasConfigInfo());
        builder.setPassword("password");
        Assert.assertTrue(builder.build().hasConfigInfo());
    }

    @Test
    public final void ConfigInfoObjects_Comparison() {
        final ConfigInfo ci1 = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        final ConfigInfo ci2 = new ConfigInfo.Builder()
                .setDeviceId("id")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        final ConfigInfo ci3 = new ConfigInfo.Builder()
                .setDeviceId("id1")
                .setMasterDeviceIp("ip")
                .setUser("user")
                .setPassword("password")
                .build();

        Assert.assertEquals(ci1, ci2);
        Assert.assertFalse(ci1.equals(ci3));
    }

}
