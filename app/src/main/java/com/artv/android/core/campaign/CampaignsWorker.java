package com.artv.android.core.campaign;

import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.DbWorker;

import java.util.List;

import static com.artv.android.core.campaign.CampaignHelper.getAssetsCount;
import static com.artv.android.core.campaign.CampaignHelper.getCampaignsCount;

/**
 * Class that handle application campaign logic, i.e. getCampaign request, loading campaign, cancel
 * loading showing progress, writing to db etc.
 *
 * Created by ZOG on 7/28/2015.
 */
public final class CampaignsWorker {

    private static final int ID_ALL_CAMPAIGN = 0;

    private ApiWorker mApiWorker;
    private InitData mInitData;
    private ConfigInfo mConfigInfo;
    private DbWorker mDbWorker;
    private CampaignsLoaderTask mCampaignsLoaderTask;

    public final void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public final void setInitData(final InitData _initData) {
        mInitData = _initData;
    }

    public final void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final boolean hasCampaignToPlay() {
        return !mDbWorker.getAllCampaigns().isEmpty();
    }

    /**
     * Used when application launch for first time, downloading all assigned campaigns.
     * @param _listener listen for finishing operation.
     */
    public final void doInitialCampaignDownload(final ICampaignDownloadListener _listener) {
        getCampaign(ID_ALL_CAMPAIGN, new IGetCampaignsCallback() {
            @Override
            public final void onFinished(final GetCampaignsResult _result) {
                if (_result.getSuccess()) {
                    ArTvLogger.printMessage("Campaigns: " + getCampaignsCount(_result.getCampaigns()));
                    ArTvLogger.printMessage("Assets: " + getAssetsCount(_result.getCampaigns()));
                    loadCampaigns(_result.getCampaigns(), _listener);
                } else {
                    ArTvLogger.printMessage("Error loading campaigns: " + _result.getMessage());
                }
            }
        });
    }

    /**
     * GET request to server, returns result through callback.
     * @param _campaignId campaign id to get.
     * @param _callback callback for operation result.
     */
    public final void getCampaign(final int _campaignId, final IGetCampaignsCallback _callback) {
        final GetCampaignRequestObject requestObject = new GetCampaignRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setCampaignID(_campaignId)
                .build();

        mApiWorker.doGetCampaign(requestObject, new WebRequestCallback<GetCampaignResponseObject>() {
            @Override
            public final void onSuccess(final GetCampaignResponseObject _respObj) {
                ArTvLogger.printMessage(_respObj.apiType + " : success");

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
                ArTvLogger.printMessage(_errorResp.apiType + ": " + _errorResp.error);

                _callback.onFinished(
                        new GetCampaignsResult.Builder()
                                .setSuccess(false)
                                .setMessage(_errorResp.error)
                                .build()
                );
            }
        });
    }

    public final void loadCampaigns(final List<Campaign> _campaigns, final ICampaignDownloadListener _listener) {
        mCampaignsLoaderTask = new CampaignsLoaderTask();
        mCampaignsLoaderTask.setCampaigns(_campaigns);
        mCampaignsLoaderTask.setDbWorker(mDbWorker);
        mCampaignsLoaderTask.setCampaignDownloadListener(_listener);
        mCampaignsLoaderTask.execute();
    }

    public final void cancelLoading() {
        if (mCampaignsLoaderTask != null) {
            mCampaignsLoaderTask.cancel(true);
            mCampaignsLoaderTask = null;
        }
    }

    public final void doRegularDownload() {

    }

}
