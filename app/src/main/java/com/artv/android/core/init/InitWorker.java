package com.artv.android.core.init;

import android.content.res.Resources;

import com.artv.android.ArTvResult;
import com.artv.android.app.beacon.BeaconScheduler;
import com.artv.android.app.message.MessageWorker;
import com.artv.android.app.playback.PlaybackWorker;
import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetGlobalConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.config_info.IConfigInfoListener;
import com.artv.android.core.log.ArTvLogger;

/**
 * Created by ZOG on 7/8/2015.
 *
 * Class, used for initial authentication (getToken) and load configs (GetGlobalConfig, GetDeviceConfig).
 * todo: rework to queue
 */
public class InitWorker implements IConfigInfoListener {

    private ConfigInfo mConfigInfo;
    private ApiWorker mApiWorker;
    private PlaybackWorker mPlaybackWorker;
    private MessageWorker mMessageWorker;
    private BeaconScheduler mBeaconScheduler;

    private InitData mInitData;
    private IInitCallback mCallback;

    public InitWorker() {
        mInitData = new InitData();
    }

    public final void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public final void setPlaybackWorker(final PlaybackWorker _worker) {
        mPlaybackWorker = _worker;
    }

    public final void setMessageWorker(final MessageWorker _worker) {
        mMessageWorker = _worker;
    }

    public final void setBeaconScheduler(final BeaconScheduler _scheduler) {
        mBeaconScheduler = _scheduler;
    }

    @Override
    public final void onConfigInfoLoaded(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public final void startInitializing(final IInitCallback _callback) {
        mCallback = _callback;
        getToken(); //begin
    }

    public final InitData getInitData() {
        return mInitData;
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
                mInitData.setToken(_respObj.token);
                ArTvLogger.printMessage(_respObj.apiType + ": " + Resources.getSystem().getString(android.R.string.ok));
                getGlobalConfig();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
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
                _respObj.globalConfig.prepareEntries();

                mInitData.setGlobalConfig(_respObj.globalConfig);
                mPlaybackWorker.setGlobalConfig(_respObj.globalConfig);
                mMessageWorker.setGlobalConfig(_respObj.globalConfig);
                mBeaconScheduler.setGlobalConfig(_respObj.globalConfig);

                ArTvLogger.printMessage(_respObj.apiType + ": " + Resources.getSystem().getString(android.R.string.ok));
                getDeviceConfig();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
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
                ArTvLogger.printMessage(_respObj.apiType + ": " + Resources.getSystem().getString(android.R.string.ok));
                mCallback.onInitSuccess(buildInitResult(true, "Initializing success"));
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
            }
        });
    }

    private final ArTvResult buildInitResult(final boolean _success, final String _message) {
        return new ArTvResult.Builder()
                .setSuccess(_success)
                .setMessage(_message + (_success ? "" : " \tfuuuuuuuuuu"))
                .build();
    }
}
