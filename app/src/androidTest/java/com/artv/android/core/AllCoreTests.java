package com.artv.android.core;

import com.artv.android.core.api.AllApiTests;
import com.artv.android.core.config_info.AllConfigInfoTests;
import com.artv.android.core.config_info.ConfigInfoWorkerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllApiTests.class,
        AllConfigInfoTests.class,
        ApplicationLogicTest.class,
        GetTokenRequestObjectTest.class
})
public final class AllCoreTests {
}
