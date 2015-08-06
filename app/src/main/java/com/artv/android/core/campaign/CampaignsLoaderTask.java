package com.artv.android.core.campaign;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.artv.android.core.ArTvResult;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.DbWorker;

import java.util.List;

/**
 * Created by ZOG on 8/3/2015.
 */
public final class CampaignsLoaderTask extends AsyncTask<Void, Void, ArTvResult> implements IAssetLoadProgressListener {

    private List<Campaign> mCampaigns;
    private AssetHelper mAssetHelper;
    private DbWorker mDbWorker;
    private ICampaignDownloadListener mCampaignDownloadListener;

    private static final double MAX_PROGRESS = 10000;
    private double currentProgress = 0;

    public CampaignsLoaderTask() {
        mAssetHelper = new AssetHelper();
        mAssetHelper.setProgressListener(this);
    }

    public final void setCampaigns(final List<Campaign> _campaigns) {
        mCampaigns = _campaigns;
    }

    public void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final void setCampaignDownloadListener(final ICampaignDownloadListener _listener) {
        mCampaignDownloadListener = _listener;
    }

    @Override
    protected final ArTvResult doInBackground(final Void... params) {
        final int assetsCount = CampaignHelper.getAssetsCount(mCampaigns);
        final double progressPerAsset = MAX_PROGRESS / assetsCount;

        for (final Campaign campaign : mCampaigns) {
            ArTvLogger.printMessage("Loading campaign with id = " + campaign.campaignId);
            if (mDbWorker.contains(campaign)) {
                ArTvLogger.printMessage(false, ": already contains");
                continue;
            }

            for (final Asset asset : campaign.assets) {
                ArTvLogger.printMessage("Loading asset " + asset.name + "...");
                if (mDbWorker.contains(asset)) {
                    ArTvLogger.printMessage(false, "already contains");
                    continue;
                }

                final ArTvResult assetResult = mAssetHelper.loadAsset(this, asset, progressPerAsset);
                ArTvLogger.printMessage(false, "finished: " + assetResult.getSuccess());

                if (assetResult.getSuccess()) mDbWorker.write(asset);
                else return assetResult;
            }

            mDbWorker.write(campaign);
        }
        ArTvLogger.printMessage("All campaigns loaded");

        return new ArTvResult.Builder().setSuccess(true).build();
    }

    @Override
    protected final void onCancelled() {
        super.onCancelled();
        mCampaignDownloadListener.onCampaignDownloadFinished(buildCancelledResult());
    }

    private final ArTvResult buildCancelledResult() {
        return new ArTvResult.Builder()
                .setSuccess(false)
                .setMessage("Loading task cancelled")
                .build();
    }

    @Override
    protected final void onPostExecute(final ArTvResult _result) {
        mCampaignDownloadListener.onCampaignDownloadFinished(_result);
    }

    private final void postOnUiThread(final double _progress) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public final void run() {
//                mPercentListener.onPercentUpdate(_progress);
            }
        });
    }

    @Override
    public final void onProgressLoaded(final double _progress) {
        currentProgress += _progress;
        postOnUiThread(currentProgress);
    }
}
