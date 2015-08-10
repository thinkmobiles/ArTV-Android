package com.artv.android.core.beacon;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.BeaconRequestObject;
import com.artv.android.core.api.api_model.response.BeaconResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Beacon;
import com.artv.android.core.model.MsgBoardCampaign;

import java.util.ArrayList;

/**
 * Created by ZOG on 7/27/2015.
 */
public final class BeaconWorker {

    private ConfigInfo mConfigInfo;
    private InitData mInitData;
    private ApiWorker mApiWorker;
    private IBeaconCallback mCallback;

    public void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public void setInitData(final InitData _initData) {
        mInitData = _initData;
    }

    public void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public final void doBeacon() {
        final BeaconRequestObject requestObject = new BeaconRequestObject();
        requestObject.token = mInitData.getToken();
        requestObject.tagId = mConfigInfo.getDeviceId();
        requestObject.beacon = buildEmptyBeacon();

        mApiWorker.doBeacon(requestObject, new WebRequestCallback<BeaconResponseObject>() {
            @Override
            public final void onSuccess(final BeaconResponseObject _respObj) {
//                mCallback.onProgress(buildInitResult(true, _respObj.apiType + " : success"));
//                getCampaign();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
//                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
            }
        });
    }

    private final Beacon buildEmptyBeacon() {
        final Beacon beacon = new Beacon();
        beacon.tagId = mConfigInfo.getDeviceId();
        beacon.currentDateTime = "";
        beacon.currentCampaign = 0;
        beacon.currentAsset = new Asset();
        beacon.campaigns = new ArrayList<>();
        beacon.msgBoardCampaign = buildMsgBoardCampaign();
        beacon.errorLog = "";
        return beacon;
    }

    private final MsgBoardCampaign buildMsgBoardCampaign() {
        final MsgBoardCampaign msgBoardCampaign = new MsgBoardCampaign();
        return msgBoardCampaign;
    }

//    private final InitResult buildInitResult(final boolean _success, final String _message) {
//        return new InitResult.Builder()
//                .setSuccess(_success)
//                .setMessage(_message + (_success ? "" : " \tfuuuuuuuuuu"))
//                .build();
//    }

}
