package com.artv.android.core;

import com.artv.android.core.api.AllApiTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllApiTests.class,
        ApplicationLogicTest.class,
        GetTokenRequestObjectTest.class
})
public final class AllCoreTests {
}
