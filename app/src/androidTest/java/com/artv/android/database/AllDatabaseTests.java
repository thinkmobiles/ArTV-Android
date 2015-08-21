package com.artv.android.database;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Need drop existing database before launch tests.
 *
 * Created by ZOG on 8/18/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AssetTest.class,
        CampaignTest.class,
        MsgBoardCampaignTest.class
})
public final class AllDatabaseTests {
    @Before
    public final void Init() {
        DbManager.getInstance(InstrumentationRegistry.getTargetContext()).dropDatabase();
    }
}
