package com.artv.android.core.campaign;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;

/**
 * Created by ZOG on 7/28/2015.
 */
public final class CampaignWorker {

    private ApiWorker mApiWorker;
    private InitData mInitData;
    private ConfigInfo mConfigInfo;

    public final void getCampaign() {
        final GetCampaignRequestObject requestObject = new GetCampaignRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setCampaignID(0)
                .build();

        mApiWorker.doGetCampaign(requestObject, new WebRequestCallback<GetCampaignResponseObject>() {
            @Override
            public final void onSuccess(final GetCampaignResponseObject _respObj) {
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

}
