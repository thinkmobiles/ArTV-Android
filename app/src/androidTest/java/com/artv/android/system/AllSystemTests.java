package com.artv.android.system;

import com.artv.android.system.fragments.playback.PictureTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ZOG on 7/6/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PictureTest.class,
        MainActivityTest.class,
        SpHelperTest.class
})
public final class AllSystemTests {
}
