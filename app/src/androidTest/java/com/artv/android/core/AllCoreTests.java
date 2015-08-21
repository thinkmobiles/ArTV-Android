package com.artv.android.core;

import com.artv.android.core.api.AllApiTests;
import com.artv.android.core.config_info.AllConfigInfoTests;
import com.artv.android.core.date.AllDateTests;
import com.artv.android.core.init.AllInitTests;
import com.artv.android.core.model.AllModelTests;
import com.artv.android.core.state.AllStateTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllApiTests.class,
        AllConfigInfoTests.class,
        AllDateTests.class,
        AllInitTests.class,
        AllStateTests.class,
        AllModelTests.class
})
public final class AllCoreTests {
}
