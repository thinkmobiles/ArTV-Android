package com.artv.android.core.campaign;

import android.net.Uri;
import android.os.AsyncTask;

import com.artv.android.ArTvResult;
import com.artv.android.core.Constants;
import com.artv.android.core.api.ApiConst;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.DbWorker;

import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by ZOG on 8/3/2015.
 */
public final class CampaignLoaderTask extends AsyncTask<Void, Void, ArTvResult> {

    private List<Campaign> mCampaigns;
    private DbWorker mDbWorker;
    private ICampaignDownloadListener mCampaignDownloadListener;

    private static final double MAX_PROGRESS = 100;
    private double mTotalProgress = 0;

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
                ArTvLogger.printMessage(false, ": already loaded");
                mTotalProgress += campaign.assets.size() * progressPerAsset;
                publishProgress();
                continue;
            }

            for (final Asset asset : campaign.assets) {
                ArTvLogger.printMessage("Loading asset " + asset.name + "...");
                if (mDbWorker.contains(asset)) {
                    ArTvLogger.printMessage(false, "already loaded");
                    mTotalProgress += progressPerAsset;
                    publishProgress();
                    continue;
                }

                final ArTvResult assetResult = loadAsset(asset, progressPerAsset);
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
    protected final void onProgressUpdate(Void... values) {
        mCampaignDownloadListener.onPercentLoaded(mTotalProgress);
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

    public final ArTvResult loadAsset(final Asset _asset, final double _progressPerAsset) {
        final ArTvResult.Builder result = new ArTvResult.Builder();

        try {
            final URL url = buildUrlFrom(_asset.url);

            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod(HttpGet.METHOD_NAME);

            final int respCode = conn.getResponseCode();

            final int buffLength = 4096 * 2;
            final int fileLength = conn.getContentLength();

            if (respCode == 200) {
                final File file = new File(Constants.PATH + _asset.url);
                file.getParentFile().mkdirs();
                if (file.exists() && file.length() == conn.getContentLength()) {        //temporary
                    result.setSuccess(true);
                    mTotalProgress += _progressPerAsset;
                    publishProgress();
                    return result.build();
                }

                final InputStream inputStream = conn.getInputStream();
                final FileOutputStream fos = new FileOutputStream(file);

                int read;
                byte[] buffer = new byte[buffLength];
                while ((read = inputStream.read(buffer)) != -1) {
                    if (isCancelled()) {
                        result.setSuccess(false);
                        return result.build();
                    }
                    fos.write(buffer, 0, read);
                    mTotalProgress += read / (double) fileLength * _progressPerAsset;
                    publishProgress();
                }
                fos.close();
                inputStream.close();

                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("Error: response code = " + respCode);
            }
        } catch (final IOException _e) {
            _e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(_e.toString());
        }
        return result.build();
    }

    private final URL buildUrlFrom(final String _path) throws UnsupportedEncodingException, MalformedURLException {
        final URL url;
        try {
            url = new URL(_path);
            return url;
        } catch (final MalformedURLException _e) {
            _e.printStackTrace();
        }

        final Uri uri = new Uri.Builder()
                .scheme(ApiConst.getProtocol())
                .authority(ApiConst.getAuthority())
                .path(_path)
                .build();

        final String address = URLDecoder.decode(uri.toString(), "utf-8");
        return new URL(address);
    }
}
