package com.artv.android.core.campaign;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZOG on 7/28/2015.
 */
public final class CampaignWorker {

    private ApiWorker mApiWorker;
    private InitData mInitData;
    private ConfigInfo mConfigInfo;

    private Set<ICampaignLoadListener> mCampaignLoadListeners;

    public CampaignWorker() {
        mCampaignLoadListeners = new HashSet<>();
    }

    public final void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public final void setInitData(final InitData _initData) {
        mInitData = _initData;
    }

    public final void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public final boolean addCampaignLoadListener(final ICampaignLoadListener _listener) {
        return mCampaignLoadListeners.add(_listener);
    }

    public final boolean removeCampaignLoadListener(final ICampaignLoadListener _listener) {
        return mCampaignLoadListeners.remove(_listener);
    }

    public final void notifyProgressMessage(final String _message) {
        for (final ICampaignLoadListener listener : mCampaignLoadListeners) listener.progressMessage(_message);
    }

    public final void notifyProgress(final int _percent) {
        for (final ICampaignLoadListener listener : mCampaignLoadListeners) listener.onProgress(_percent);
    }

    public final void notifyCampaignLoaded(final CampaignLoadResult _result) {
        for (final ICampaignLoadListener listener : mCampaignLoadListeners) listener.onCampaignLoaded(_result);
    }

    public final void notifyCampaignLoadFailed(final CampaignLoadResult _result) {
        for (final ICampaignLoadListener listener : mCampaignLoadListeners) listener.onCampaignLoadFailed(_result);
    }

    public final void doCampaignLogic() {
        getCampaign(0);

    }

    public final void getCampaign(final int _id) {
        final GetCampaignRequestObject requestObject = new GetCampaignRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setCampaignID(_id)
                .build();

        mApiWorker.doGetCampaign(requestObject, new WebRequestCallback<GetCampaignResponseObject>() {
            @Override
            public final void onSuccess(final GetCampaignResponseObject _respObj) {
                notifyProgressMessage(_respObj.apiType + " : success\n" + "campaigns: ");

//                mCallback.onProgress(buildInitResult(true, _respObj.apiType + " : success"));
//                mCallback.onInitSuccess(buildInitResult(true, "Initializing success"));
//                mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
//                mCallback.onInitFail(buildInitResult(false, _errorResp.apiType + ": " + _errorResp.error));
            }
        });
    }

    public final boolean hasCampaign() {
        return false;
    }

    private final CampaignLoadResult buildCampaignLoadResult(final boolean _success, final String _message) {
        return new CampaignLoadResult.Builder()
                .setSuccess(_success)
                .setMessage(_message + (_success ? "" : " \tfuuuuuuuuuu"))
                .build();
    }

}
