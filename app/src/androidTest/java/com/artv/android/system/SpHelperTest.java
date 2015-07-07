package com.artv.android.system;

import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.ReflectionHelper;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ZOG on 7/6/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class SpHelperTest {

    private SpHelper mSpHelper;

    @Before
    public final void Init() {
        mSpHelper = new SpHelper(InstrumentationRegistry.getTargetContext());
    }

    @After
    public final void DeInit() throws NoSuchFieldException, IllegalAccessException {
        mSpHelper.clearAll();
    }

    @Test
    public final void Created_SpInitialized() throws NoSuchFieldException, IllegalAccessException {
        Assert.assertNotNull(ReflectionHelper.getField(mSpHelper, "mSp"));
    }

    @Test
    public final void PutGetString_ValueEqual() {
        mSpHelper.putString("key", "value");
        Assert.assertTrue(mSpHelper.getString("key").equals("value"));
    }

    @Test
    public final void RemoveString() {
        mSpHelper.putString("key", "value");
        mSpHelper.removeString("key");
        Assert.assertFalse("value".equals(mSpHelper.getString("key")));
    }

    @Test
    public final void GetNotExistingString_ReturnDefValue() throws NoSuchFieldException, IllegalAccessException {
        Assert.assertEquals(mSpHelper.getString("non_existing_key"), ReflectionHelper.getField(mSpHelper, "DEF_STRING_VAL"));
    }

}
