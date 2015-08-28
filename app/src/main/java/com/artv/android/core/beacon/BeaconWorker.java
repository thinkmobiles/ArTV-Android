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
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Beacon;
import com.artv.android.database.DbWorker;

import java.util.ArrayList;

/**
 * Created by ZOG on 7/27/2015.
 */
public class BeaconWorker {

    private ConfigInfo mConfigInfo;
    private InitData mInitData;
    private ApiWorker mApiWorker;
    private DateWorker mDateWorker;
    private DbWorker mDbWorker;

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
        final BeaconRequestObject requestObject = new BeaconRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setBeacon(buildBeacon())
                .build();

        mApiWorker.doBeacon(requestObject, new WebRequestCallback<BeaconResponseObject>() {
            @Override
            public final void onSuccess(final BeaconResponseObject _respObj) {
                final CampaignResult.Builder builder = new CampaignResult.Builder();

                if (_respObj.errorNumber == 0) {
                    ArTvLogger.printMessage(_respObj.apiType + " : success");
                    builder.setSuccess(true).setCampaigns(_respObj.getCampaigns()).setMsgBoardCampaign(_respObj.getMsgBoardCampaign());
                } else {
                    ArTvLogger.printMessage(_respObj.apiType + "#" + _respObj.errorNumber + ", " +_respObj.errorDescription);
                    builder.setSuccess(false).setMessage(_respObj.apiType + "#" + _respObj.errorNumber + ", " +_respObj.errorDescription);
                }

                _callback.onFinished(builder.build());
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                ArTvLogger.printMessage(_errorResp.apiType + " : " + _errorResp.error);

                _callback.onFinished(new CampaignResult.Builder()
                        .setSuccess(false)
                        .setMessage(_errorResp.apiType.name() + " " + _errorResp.error)
                        .build());
            }
        });
    }

    private final Beacon buildBeacon() {
        final Beacon beacon = new Beacon();
        beacon.tagId = mConfigInfo.getDeviceId();
        beacon.currentDateTime = mDateWorker.getCurrentFormattedDate();
        beacon.currentCampaign = 0; //set current playing campaign id
        beacon.currentAsset = 0; //set current playing asset sequence
        beacon.campaigns = new ArrayList<>(mDbWorker.getAllCampaigns());
        beacon.mMessageBoardCampaigns = new ArrayList<>();
        beacon.mMessageBoardCampaigns.add(mDbWorker.getMsgBoardCampaign());
        beacon.errorLog = "All ok";
        return beacon;
    }

}
