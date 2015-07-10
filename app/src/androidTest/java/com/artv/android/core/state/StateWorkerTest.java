package com.artv.android.core.state;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.ReflectionHelper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZOG on 7/8/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class StateWorkerTest {
    
    private StateWorker mStateWorker;
    
    @Before
    public final void init() {
        mStateWorker = new StateWorker();
    }

    @Test
    public final void Create_FieldsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Assert.assertNotNull(ReflectionHelper.getField(mStateWorker, "mStateChangeListeners"));
    }
    
    @Test
    public final void IArTvStateChangeListeners_AddAndRemoveSuccessfully() {
        final IArTvStateChangeListener l1 = new IArTvStateChangeListener() {
            @Override
            public final void onArTvStateChanged() {

            }
        };
        final IArTvStateChangeListener l2 = new IArTvStateChangeListener() {
            @Override
            public final void onArTvStateChanged() {

            }
        };

        Assert.assertTrue(mStateWorker.addStateChangeListener(l1));
        Assert.assertTrue(mStateWorker.addStateChangeListener(l2));
        Assert.assertFalse(mStateWorker.addStateChangeListener(l2));
        Assert.assertFalse(mStateWorker.addStateChangeListener(l2));
        Assert.assertTrue(mStateWorker.removeStateChangeListener(l1));
        Assert.assertTrue(mStateWorker.removeStateChangeListener(l2));
        Assert.assertFalse(mStateWorker.removeStateChangeListener(l1));
        Assert.assertFalse(mStateWorker.removeStateChangeListener(l2));
    }

    @Test
    public final void IArTvStateChangeListener_Notifying() throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final IArTvStateChangeListener l1 = new IArTvStateChangeListener() {
            @Override
            public final void onArTvStateChanged() {
                countDownLatch.countDown();
            }
        };
        mStateWorker.addStateChangeListener(l1);
        ReflectionHelper.invoke(mStateWorker, "notifyStateChangeListeners");
        final boolean interrupted = !countDownLatch.await(1, TimeUnit.SECONDS);
        Assert.assertFalse(interrupted);
    }

}
