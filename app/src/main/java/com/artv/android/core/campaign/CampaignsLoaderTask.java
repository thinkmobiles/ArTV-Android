package com.artv.android.core.campaign;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.artv.android.core.ArTvResult;
import com.artv.android.core.ILogger;
import com.artv.android.core.IPercentListener;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.DbWorker;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZOG on 8/3/2015.
 */
public final class CampaignsLoaderTask extends AsyncTask<Void, Void, ArTvResult> implements IAssetLoadProgressListener {

    private List<Campaign> mCampaigns;
    private AssetHelper mAssetHelper;
    private DbWorker mDbWorker;
    private ILogger mUiLogger;
    private IPercentListener mPercentListener;

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

    public final void setUiLogger(final ILogger _logger) {
        mUiLogger = _logger;
    }

    public final void setPercentListener(final IPercentListener _listener) {
        mPercentListener = _listener;
    }

    @Override
    protected final ArTvResult doInBackground(final Void... params) {
        ArTvResult.Builder totalResult = new ArTvResult.Builder();

        final int assetsCount = CampaignHelper.getAssetsCount(mCampaigns);
        final double progressPerAsset = MAX_PROGRESS / assetsCount;

        for (final Campaign campaign : mCampaigns) {
            postOnUiThread(true, "Loading campaign with id = " + campaign.campaignId);
            if (mDbWorker.contains(campaign)) {
                postOnUiThread(false, ": already contains");
                continue;
            }

            for (final Asset asset : campaign.assets) {
                postOnUiThread(true, "Loading asset " + asset.name + "...");
                if (mDbWorker.contains(asset)) {
                    postOnUiThread(false, "already contains");
                    continue;
                }

                ArTvResult assetResult;
                try {
                    assetResult = mAssetHelper.loadAsset(asset, progressPerAsset);
                } catch (final IOException e) {
                    e.printStackTrace();
                    totalResult.setSuccess(false);
                    totalResult.setMessage(e.toString());
                    return totalResult.build();
                }
                if (assetResult.getSuccess()) {
                    postOnUiThread(false, "finished: " + assetResult.getSuccess());
                    mDbWorker.write(asset);
                } else {
                    onProgressLoaded(progressPerAsset);
                    postOnUiThread(false, "finished: " + assetResult.getSuccess());

                    totalResult.setSuccess(assetResult.getSuccess());
                    totalResult.setMessage(assetResult.getMessage());
                    return totalResult.build();
                }
            }

            mDbWorker.write(campaign);
        }
        postOnUiThread(true, "All campaigns loaded");

        return null;
    }

    private final void postOnUiThread(final boolean _newLine, final String _message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public final void run() {
                mUiLogger.printMessage(_newLine, _message);
            }
        });
    }

    private final void postOnUiThread(final double _progress) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public final void run() {
                mPercentListener.onPercentUpdate(_progress);
            }
        });
    }

    @Override
    public final void onProgressLoaded(final double _progress) {
        currentProgress += _progress;
        postOnUiThread(currentProgress);
    }
}
