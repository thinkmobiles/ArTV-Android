package com.artv.android.core.campaign.load;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;
import com.artv.android.core.model.Asset;

import java.util.List;

/**
 * Created by ZOG on 7/29/2015.
 */
public final class CampaignLoader {

    private ApiWorker mApiWorker;
    private InitData mInitData;
    private ConfigInfo mConfigInfo;

    public final void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public final void setInitData(final InitData _initData) {
        mInitData = _initData;
    }

    public final void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public final void getCampaigns(final IGetCampaignsCallback _callback) {
        final GetCampaignRequestObject requestObject = new GetCampaignRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setCampaignID(0)
                .build();

        mApiWorker.doGetCampaign(requestObject, new WebRequestCallback<GetCampaignResponseObject>() {
            @Override
            public final void onSuccess(final GetCampaignResponseObject _respObj) {
//                notifyProgressMessage(_respObj.apiType + " : success\n"
//                                + "Campaigns: " + getCampaignsCount(mCampaigns) + "\n"
//                                + "Assets: " + getAssetsCount(mCampaigns)
//                );
                _callback.onFinished(
                        new GetCampaignsResult.Builder()
                                .setSuccess(true)
                                .setMessage("Success")
                                .setCampaigns(_respObj.campaigns)
                                .build()
                );
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
//                notifyCampaignLoadFailed(buildCampaignLoadResult(false, _errorResp.apiType + ": " + _errorResp.error));
                _callback.onFinished(
                        new GetCampaignsResult.Builder()
                                .setSuccess(false)
                                .setMessage(_errorResp.error)
                                .build()
                );
            }
        });
    }

    public final void downloadAssets(final List<Asset> _assets) {

    }

}
