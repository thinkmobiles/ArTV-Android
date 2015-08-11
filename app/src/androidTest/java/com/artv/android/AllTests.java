package com.artv.android;

import com.artv.android.core.AllCoreTests;
import com.artv.android.database.Database;
import com.artv.android.system.AllSystemTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllCoreTests.class,
        Database.class,
        AllSystemTests.class,
        ApplicationLogicTest.class,
        ArTvResultTest.class
})
public final class AllTests {
}
