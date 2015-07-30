package com.artv.android.core.campaign.asset_load;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.artv.android.core.model.Asset;

import java.util.List;

/**
 * Created by ZOG on 7/30/2015.
 */
public final class AssetsLoaderTask extends AsyncTask<Void, Double, Void> {

    private List<Asset> mAssets;
    private IDownloadAssetsListener mDownloadAssetsListener;

    public final void setAssets(final List<Asset> _assets) {
        mAssets = _assets;
    }

    public final void setDownloadAssetsListener(final IDownloadAssetsListener _listener) {
        mDownloadAssetsListener = _listener;
    }

    @Override
    protected final Void doInBackground(Void... _params) {
        final double percentsPerAsset = 10000.0 / mAssets.size();
        final double percentAssetStep = percentsPerAsset / 100.0;
        double totalPercent = 0;
        publishProgress(totalPercent);

        for (final Asset asset : mAssets) {
            notifyUiStartLoadingAsset(asset);
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                totalPercent += percentAssetStep;
                publishProgress(totalPercent);
            }
        }

        return null;
    }

    private final void notifyUiStartLoadingAsset(final Asset _asset) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public final void run() {
                mDownloadAssetsListener.onStartLoadingAsset(_asset);
            }
        });
    }

    @Override
    protected final void onProgressUpdate(final Double... _values) {
        mDownloadAssetsListener.onTotalProgressChanged(_values[0]);
    }

    @Override
    protected final void onPostExecute(final Void _void) {
        mDownloadAssetsListener.onLoadFinished();
    }

}