package com.artv.android.core.beacon;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.BeaconRequestObject;
import com.artv.android.core.api.api_model.response.BeaconResponseObject;
import com.artv.android.core.campaign.CampaignResult;
import com.artv.android.core.campaign.ICampaignCallback;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.date.DateWorker;
import com.artv.android.core.init.InitData;
import com.artv.android.core.model.Beacon;
import com.artv.android.database.DbWorker;

import java.util.ArrayList;

/**
 * Created by ZOG on 7/27/2015.
 */
public final class BeaconWorker {

    private ConfigInfo mConfigInfo;
    private InitData mInitData;
    private ApiWorker mApiWorker;
    private DateWorker mDateWorker;
    private DbWorker mDbWorker;

    private ICampaignCallback mCallback;

    public void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public void setInitData(final InitData _initData) {
        mInitData = _initData;
    }

    public void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public void setDateWorker(final DateWorker _dateWorker) {
        mDateWorker = _dateWorker;
    }

    public void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final void doBeacon(final ICampaignCallback _callback) {
        final BeaconRequestObject requestObject = new BeaconRequestObject();
        requestObject.token = mInitData.getToken();
        requestObject.tagId = mConfigInfo.getDeviceId();
        requestObject.beacon = buildBeacon();

        mApiWorker.doBeacon(requestObject, new WebRequestCallback<BeaconResponseObject>() {
            @Override
            public final void onSuccess(final BeaconResponseObject _respObj) {
                final CampaignResult.Builder builder = new CampaignResult.Builder();

                if (_respObj.errorNumber == 0) {

                } else {

                }
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                _callback.onFinished(new CampaignResult.Builder()
                        .setSuccess(false)
                        .setMessage(_errorResp.apiType.name() + _errorResp.error)
                        .build());
            }
        });
    }

    private final Beacon buildBeacon() {
        final Beacon beacon = new Beacon();
        beacon.tagId = mConfigInfo.getDeviceId();
        beacon.currentDateTime = mDateWorker.getCurrentFormattedDate();
        beacon.currentCampaign = 0; //set current playing campaign id
        beacon.currentAsset = 0; //set current playing asset id
        beacon.campaigns = new ArrayList<>(mDbWorker.getAllCampaigns());
        beacon.msgBoardCampaign = mDbWorker.getMsgBoardCampaign();
        beacon.errorLog = "All ok";
        return beacon;
    }

}
