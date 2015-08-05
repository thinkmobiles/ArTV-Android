package com.artv.android.core.campaign;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

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
public final class CampaignsLoaderTask extends AsyncTask<Void, Void, CampaignsLoadResult> implements IAssetLoadProgressListener {

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
    protected final CampaignsLoadResult doInBackground(final Void... params) {
        CampaignsLoadResult campaignsLoadResult = new CampaignsLoadResult();

        final int assetsCount = CampaignHelper.getAssetsCount(mCampaigns);
        final double progressPerAsset = MAX_PROGRESS / assetsCount;

        for (final Campaign campaign : mCampaigns) {
            postOnUiThread(true, "Loading campaign with id = " + campaign.campaignId);
            if (mDbWorker.contains(campaign)) {
                postOnUiThread(false, ": already contains");
                continue;
            }

            for (final Asset asset : campaign.assets) {
                AssetLoadResult result = new AssetLoadResult();

                postOnUiThread(true, "Loading asset " + asset.name + "...");
                if (mDbWorker.contains(asset)) {
                    result.success = true;
                    postOnUiThread(false, "already contains");
                    continue;
                }

                try {
                    result = mAssetHelper.loadAsset(asset, progressPerAsset);
                } catch (IOException e) {
                    e.printStackTrace();
                    result.success = false;
                    result.message = e.toString();
                }
                if (result.success) {
                    postOnUiThread(false, "finished: " + result.success);
                    mDbWorker.write(asset);
                } else {
                    onProgressLoaded(progressPerAsset);
                    postOnUiThread(false, "finished: " + result.success);

                    campaignsLoadResult.success = result.success;
                    campaignsLoadResult.message = result.message;
                    return campaignsLoadResult;
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
