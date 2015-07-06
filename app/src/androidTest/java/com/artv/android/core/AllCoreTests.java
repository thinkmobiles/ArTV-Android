package com.artv.android.core;

import com.artv.android.core.api.AllApiTests;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.config_info.ConfigInfoTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllApiTests.class,
        ConfigInfoTests.class,
        ApplicationLogicTest.class,
        GetTokenRequestObjectTest.class
})
public final class AllCoreTests {
}
