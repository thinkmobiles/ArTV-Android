package com.artv.android.system;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.artv.android.R;
import com.artv.android.core.api.Temp;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.state.IArTvStateChangeListener;
import com.artv.android.database.DBManager;
import com.artv.android.system.fragments.ConfigInfoFragment;
import com.artv.android.system.fragments.SplashScreenFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity implements IArTvStateChangeListener {
    private FrameLayout mFragmentContainer;

    @Override
    protected final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        getMyApplication().createApplicationLogic();

        mFragmentContainer = (FrameLayout) findViewById(R.id.flFragmentContainer_AM);

        if (_savedInstanceState == null) handleAppState();

        testDB();
    }

    private void testDB() {
        DBManager dbManager = DBManager.getInstance(this);
        List<Campaign> testCampaigns = new ArrayList<>(2);

        Campaign campaign1 = new Campaign();
        campaign1.setmCampaignID(23);
        campaign1.setmSequence(1234);
        campaign1.setmPlayDay("playday1");
        campaign1.setmOverrideTime("over1");
        campaign1.setmCRCVersion(12);
        campaign1.setmEndDate("2015-05-07");
        campaign1.setmStartDate("2015-03-07");

        List<Asset> assets1 = new LinkedList<>();

        Asset asset1 = new Asset();
        asset1.setmURL("url1");
        asset1.setmName("name1");
        asset1.setmSequence(12345);
        asset1.setmDuration(1232);

        Asset asset2 = new Asset();
        asset2.setmURL("url2");
        asset2.setmName("name2");
        asset2.setmSequence(123456);
        asset2.setmDuration(12322);

        assets1.add(asset1);
        assets1.add(asset2);

        campaign1.setmAssets(assets1);

        Campaign campaign2 = new Campaign();
        campaign2.setmCampaignID(24);
        campaign2.setmSequence(12345);
        campaign2.setmPlayDay("playday2");
        campaign2.setmOverrideTime("over2");
        campaign2.setmCRCVersion(13);
        campaign2.setmEndDate("2015-20-07");
        campaign2.setmStartDate("2015-01-07");


        List<Asset> assets2 = new LinkedList<>();

        Asset asset3 = new Asset();
        asset3.setmURL("url3");
        asset3.setmName("name3");
        asset3.setmSequence(123453);
        asset3.setmDuration(12323);

        Asset asset4 = new Asset();
        asset4.setmURL("url4");
        asset4.setmName("name4");
        asset4.setmSequence(1234564);
        asset4.setmDuration(123224);

        assets2.add(asset3);
        assets2.add(asset4);

        campaign2.setmAssets(assets2);

        testCampaigns.add(campaign1);
        testCampaigns.add(campaign2);

        dbManager.addNewOrUpdateCampaigns(testCampaigns);

        List<Campaign> resCampaigns = dbManager.getAllCampaigns();

        Campaign resCampaign = dbManager.getCampaignById((long) 24);

        List<Campaign> resCampaigns2 = dbManager.getCampaignsFromDate("2015-01-07");

        dbManager.dropDatabase();


        if(resCampaign != null) return;
    }

    @Override
    protected final void onStart() {
        super.onStart();

        getMyApplication().getApplicationLogic().getStateWorker().addStateChangeListener(this);
    }

    @Override
    protected final void onStop() {
        super.onStop();

        getMyApplication().getApplicationLogic().getStateWorker().removeStateChangeListener(this);
    }

    private void getDeviceId() {
        String deviceId = null;
        if (TextUtils.isEmpty(deviceId))
            deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.v("deviceId", deviceId);
    }

    private void checkTestApi() {
        Temp temp = new Temp();
        temp.example();
    }

    @Override
    public final void onArTvStateChanged() {
        handleAppState();
    }

    private final void handleAppState() {
        switch (getMyApplication().getApplicationLogic().getStateWorker().getArTvState()) {
            case STATE_APP_START:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new ConfigInfoFragment()).commit();
                break;

            case STATE_APP_START_WITH_CONFIG_INFO:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new SplashScreenFragment()).commit();
                break;
        }
    }
}
