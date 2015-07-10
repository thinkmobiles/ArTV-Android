package com.artv.android.core.init;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.BeaconRequestObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetGlobalConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.BeaconResponseObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.display.DisplaySwitcher;
import com.artv.android.core.display.DisplaySwitcherAdapterCallback;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Beacon;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;

import java.util.ArrayList;

/**
 * Created by ZOG on 7/8/2015.
 *
 * Class, used for initial authentication (getToken) and load configs (GetGlobalConfig, GetDeviceConfig).
 * todo: rework to queue
 */
public final class InitWorker {

    private static final boolean IGNORE_IF_FAIL = false;

    private DisplaySwitcher mDisplaySwitcher;
    private ConfigInfo mConfigInfo;
    private ApiWorker mApiWorker;
    private StateWorker mStateWorker;

    private InitData mInitData;
    private InitCallback mCallback;

    public InitWorker() {
        mInitData = new InitData();
    }

    public final void setDisplaySwitcher(final DisplaySwitcher _displaySwitcher) {
        mDisplaySwitcher = _displaySwitcher;
    }

    public final void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public final void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public final void setStateWorker(final StateWorker _stateWorker) {
        mStateWorker = _stateWorker;
    }

    public final void startInitializing(final InitCallback _callback) {
        mCallback = _callback;
        turnOnDisplayIfNeed(); //begin
    }

    public final InitData getInitData() {
        return mInitData;
    }

    public final void turnOnDisplayIfNeed() {
        if (mDisplaySwitcher.isDisplayTurnedOn()) {
            mCallback.onProgress(buildInitResult(true, "Display already turned on"));
            getToken();
        } else {
            mDisplaySwitcher.turnOn(new DisplaySwitcherAdapterCallback() {

                @Override
                public final void turnedOn() {
                    mCallback.onProgress(buildInitResult(true, "Turned on display"));
                    getToken();
                }

                @Override
                public final void switchFailed() {
                    mCallback.onInitFail(buildInitResult(false, "Failed to turn on display"));
                }
            });
        }
    }

    private final void getToken() {
        final GetTokenRequestObject requestObject = new GetTokenRequestObject.Builder()
                .setUserName(mConfigInfo.getUser())
                .setPassword(mConfigInfo.getPassword())
                .setTagID(mConfigInfo.getDeviceId())
                .build();

        mApiWorker.doGetToken(requestObject, new WebRequestCallback<GetTokenResponseObject>() {
            @Override
            public final void onSuccess(final GetTokenResponseObject _respObj) {
                mInitData.setToken(_respObj.mToken);
                mCallback.onProgress(buildInitResult(true, _respObj.apiType + " : success"));
                getGlobalConfig();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
                if (IGNORE_IF_FAIL) getGlobalConfig();
            }
        });
    }

    private final void getGlobalConfig() {
        final GetGlobalConfigRequestObject requestObject = new GetGlobalConfigRequestObject.Builder()
                .setToken(mInitData.getToken())
                .build();

        mApiWorker.doGetGlobalConfig(requestObject, new WebRequestCallback<GetGlobalConfigResponseObject>() {
            @Override
            public final void onSuccess(final GetGlobalConfigResponseObject _respObj) {
                mInitData.setGlobalConfig(new ArrayList<>(_respObj.list));
                mCallback.onProgress(buildInitResult(true, _respObj.apiType + " : success"));
                getDeviceConfig();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
                if (IGNORE_IF_FAIL) getDeviceConfig();
            }
        });
    }

    private final void getDeviceConfig() {
        final GetDeviceConfigRequestObject requestObject = new GetDeviceConfigRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .build();

        mApiWorker.doGetDeviceConfig(requestObject, new WebRequestCallback<GetDeviceConfigResponseObject>() {
            @Override
            public final void onSuccess(final GetDeviceConfigResponseObject _respObj) {
                mInitData.setDeviceConfig(_respObj.getDeviceConfig());
                mCallback.onProgress(buildInitResult(true, _respObj.apiType + " : success"));
                doBeacon();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
                if (IGNORE_IF_FAIL) getDeviceConfig();
            }
        });
    }

    public final void doBeacon() {
        final BeaconRequestObject requestObject = new BeaconRequestObject();
        requestObject.token = mInitData.getToken();
        requestObject.tagId = mConfigInfo.getDeviceId();
        requestObject.beacon = buildTestBeacon();

        mApiWorker.doBeacon(requestObject, new WebRequestCallback<BeaconResponseObject>() {
            @Override
            public final void onSuccess(final BeaconResponseObject _respObj) {
                mCallback.onProgress(buildInitResult(true, _respObj.apiType + " : success"));
                getCampaign();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
                if (true) getCampaign();
            }
        });
    }

    public final void getCampaign() {
        final GetCampaignRequestObject requestObject = new GetCampaignRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setCampaignID(0)
                .build();

        mApiWorker.doGetCampaign(requestObject, new WebRequestCallback<GetCampaignResponseObject>() {
            @Override
            public final void onSuccess(final GetCampaignResponseObject _respObj) {
                mCallback.onProgress(buildInitResult(true, _respObj.apiType + " : success"));
                mCallback.onInitSuccess(buildInitResult(true, "Initializing success"));
                mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
            }
        });
    }

    private final Beacon buildTestBeacon() {
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

    private final InitResult buildInitResult(final boolean _success, final String _message) {
        return new InitResult.Builder()
                .setSuccess(_success)
                .setMessage(_message + (_success ? "" : " \tfuuuuuuuuuu"))
                .build();
    }

}
