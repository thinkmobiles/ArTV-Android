package com.artv.android.core.campaign;

import com.artv.android.core.ArTvResult;
import com.artv.android.core.ILogger;
import com.artv.android.core.IPercentListener;
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
 * Created by ZOG on 7/28/2015.
 */
public final class CampaignsWorker {

    private static final int ID_ALL_CAMPAIGN = 0;

    private ApiWorker mApiWorker;
    private InitData mInitData;
    private ConfigInfo mConfigInfo;
    private ILogger mUiLogger;
    private IPercentListener mPercentListener;
    private DbWorker mDbWorker;

    private IInitialDownloadListener mInitialDownloadListener;

    public final void setApiWorker(final ApiWorker _apiWorker) {
        mApiWorker = _apiWorker;
    }

    public final void setInitData(final InitData _initData) {
        mInitData = _initData;
    }

    public final void setConfigInfo(final ConfigInfo _configInfo) {
        mConfigInfo = _configInfo;
    }

    public final void setUiLogger(final ILogger _logger) {
        mUiLogger = _logger;
    }

    public final void setPercentListener(final IPercentListener _listener) {
        mPercentListener = _listener;
    }

    public void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final boolean hasCampaignToPlay() {
        return !mDbWorker.getAllCampaigns().isEmpty();
    }

    public final void doInitialCampaignDownload(final IInitialDownloadListener _listener) {
        mInitialDownloadListener = _listener;

        getCampaign(ID_ALL_CAMPAIGN, new IGetCampaignsCallback() {
            @Override
            public final void onFinished(final GetCampaignsResult _result) {
                if (_result.getSuccess()) {
                    mUiLogger.printMessage("Campaigns: " + getCampaignsCount(_result.getCampaigns()));
                    mUiLogger.printMessage("Assets: " + getAssetsCount(_result.getCampaigns()));
                    loadCampaigns(_result.getCampaigns(), new ICampaignDownloadListener() {
                        @Override
                        public final void onCampaignDownloaded(final ArTvResult _result) {
                            _listener.onInitialDownloadFinished(_result);
                        }
                    });
                } else {
                    mUiLogger.printMessage("Error loading campaigns: " + _result.getMessage());
                }
            }
        });
    }

    public final void getCampaign(final int _campaignId, final IGetCampaignsCallback _callback) {
        final GetCampaignRequestObject requestObject = new GetCampaignRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setCampaignID(_campaignId)
                .build();

        mApiWorker.doGetCampaign(requestObject, new WebRequestCallback<GetCampaignResponseObject>() {
            @Override
            public final void onSuccess(final GetCampaignResponseObject _respObj) {
                mUiLogger.printMessage(_respObj.apiType + " : success");

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
                mUiLogger.printMessage(_errorResp.apiType + ": " + _errorResp.error);

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
        final CampaignsLoaderTask task = new CampaignsLoaderTask();
        task.setCampaigns(_campaigns);
        task.setDbWorker(mDbWorker);
        task.setCampaignDownloadListener(_listener);
        task.setUiLogger(mUiLogger);
        task.setPercentListener(mPercentListener);
        task.execute();
    }

    public final void doRegularDownload() {

    }

}
