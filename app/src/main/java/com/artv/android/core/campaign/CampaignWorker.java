package com.artv.android.core.campaign;

import com.artv.android.core.ILogger;
import com.artv.android.core.IPercentListener;
import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.campaign.asset_load.AssetLoader;
import com.artv.android.core.campaign.asset_load.IDownloadAssetsListener;
import com.artv.android.core.campaign.campaign_load.CampaignLoader;
import com.artv.android.core.campaign.campaign_load.GetCampaignsResult;
import com.artv.android.core.campaign.campaign_load.ICampaignPrepareCallback;
import com.artv.android.core.campaign.campaign_load.IGetCampaignsCallback;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;
import com.artv.android.core.model.Asset;

import java.util.List;

import static com.artv.android.core.campaign.CampaignHelper.getAssetsCount;
import static com.artv.android.core.campaign.CampaignHelper.getAssetsToDownload;
import static com.artv.android.core.campaign.CampaignHelper.getCampaignsCount;

/**
 * Created by ZOG on 7/28/2015.
 */
public final class CampaignWorker {

    private ApiWorker mApiWorker;
    private InitData mInitData;
    private ConfigInfo mConfigInfo;
    private CampaignLoader mCampaignLoader;
    private AssetLoader mAssetLoader;
    private ILogger mUiLogger;
    private IPercentListener mPercentListener;
    private ICampaignPrepareCallback mCampaignPrepareCallback;
    private VideoFilesHolder mVideoFilesHolder;

    public CampaignWorker() {
        mCampaignLoader = new CampaignLoader();
        mAssetLoader = new AssetLoader();
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

    public final void setUiLogger(final ILogger _logger) {
        mUiLogger = _logger;
    }

    public final void setPercentListener(final IPercentListener _listener) {
        mPercentListener = _listener;
    }

    public final void setVideoFilesHolder(final VideoFilesHolder _holder) {
        mVideoFilesHolder = _holder;
    }

    public final boolean hasCampaign() {
        return false;
    }

    public final void doCampaignLogic(final ICampaignPrepareCallback _callback) {
        mCampaignPrepareCallback = _callback;

        mVideoFilesHolder.clearFiles();

        mCampaignLoader.setApiWorker(mApiWorker);
        mCampaignLoader.setInitData(mInitData);
        mCampaignLoader.setConfigInfo(mConfigInfo);
        mCampaignLoader.setUiLogger(mUiLogger);

        mAssetLoader.setVideoFilesHolder(mVideoFilesHolder);

        getCampaigns();
    }

    private final void getCampaigns() {
        mCampaignLoader.getCampaigns(new IGetCampaignsCallback() {
            @Override
            public final void onFinished(final GetCampaignsResult _result) {
                if (_result.getSuccess()) {
                    mUiLogger.printMessage("Campaigns: " + getCampaignsCount(_result.getCampaigns()));
                    mUiLogger.printMessage("Assets: " + getAssetsCount(_result.getCampaigns()));
                    downloadAssets(getAssetsToDownload(_result.getCampaigns()));
                } else {
                    mUiLogger.printMessage("Error loading campaigns: " + _result.getMessage());
                }
            }
        });
    }

    private final void downloadAssets(final List<Asset> _assets) {
        mAssetLoader.downloadAssets(_assets, new IDownloadAssetsListener() {
            @Override
            public final void onStartLoadingAsset(final Asset _asset) {
                mUiLogger.printMessage("Loading " + _asset.name + "...");
            }

            @Override
            public final void onTotalProgressChanged(final double _percent) {
                mPercentListener.onPercentUpdate(_percent);
            }

            @Override
            public final void onLoadFinished() {
                mPercentListener.onPercentUpdate(10000);
                mUiLogger.printMessage("All assets loaded");
                mCampaignPrepareCallback.onPrepared();
            }

            @Override
            public final void onLoadFailed(final String _error) {
                mUiLogger.printMessage("onLoadFailed");
            }
        });
    }

}
