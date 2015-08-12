package com.artv.android;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.campaign.CampaignWorker;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.state.ArTvState;
import com.artv.android.system.SpHelper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ZOG on 6/30/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class ApplicationLogicTest {

    private ApplicationLogic mApplicationLogic;

    @Before
    public final void init() {
        mApplicationLogic = new ApplicationLogic(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public final void Create_FieldsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mSpHelper"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mStateWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mConfigInfoWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mApiWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mInitWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mDisplaySwitcher"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mBeaconWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mCampaignWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mDbWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mDateWorker"));
        Assert.assertNotNull(ReflectionHelper.getField(mApplicationLogic, "mStartWorker"));
    }

    @Test
    public final void Create_StateAppStart() {
        Assert.assertEquals(mApplicationLogic.getStateWorker().getArTvState(), ArTvState.STATE_APP_START);
    }

    @Test
    public final void DetermineState_DependsOnConfigInfoAndCampaign() throws NoSuchFieldException, IllegalAccessException {
        final ConfigInfo configInfo = mock(ConfigInfo.class);
        final ConfigInfoWorker configInfoWorker = mock(ConfigInfoWorker.class);
        when(configInfoWorker.getConfigInfo()).thenReturn(configInfo);
        configInfoWorker.setSpHelper(new SpHelper(InstrumentationRegistry.getTargetContext()));
        final CampaignWorker campaignWorker = mock(CampaignWorker.class);

        ReflectionHelper.setField(mApplicationLogic, "mConfigInfoWorker", configInfoWorker);
        ReflectionHelper.setField(mApplicationLogic, "mCampaignWorker", campaignWorker);

        when(configInfo.hasConfigInfo()).thenReturn(false);
        when(campaignWorker.hasCampaignToPlay()).thenReturn(false);

        mApplicationLogic.determineStateWhenAppStart();
        Assert.assertEquals(ArTvState.STATE_APP_START, mApplicationLogic.getStateWorker().getArTvState());

        when(configInfo.hasConfigInfo()).thenReturn(true);
        when(campaignWorker.hasCampaignToPlay()).thenReturn(false);
        mApplicationLogic.determineStateWhenAppStart();
        Assert.assertEquals(ArTvState.STATE_APP_START, mApplicationLogic.getStateWorker().getArTvState());

        when(configInfo.hasConfigInfo()).thenReturn(false);
        when(campaignWorker.hasCampaignToPlay()).thenReturn(true);
        mApplicationLogic.determineStateWhenAppStart();
        Assert.assertEquals(ArTvState.STATE_APP_START, mApplicationLogic.getStateWorker().getArTvState());

        when(configInfo.hasConfigInfo()).thenReturn(true);
        when(campaignWorker.hasCampaignToPlay()).thenReturn(true);
        mApplicationLogic.determineStateWhenAppStart();
        Assert.assertEquals(ArTvState.STATE_APP_START_WITH_CONFIG_INFO, mApplicationLogic.getStateWorker().getArTvState());
    }
}
