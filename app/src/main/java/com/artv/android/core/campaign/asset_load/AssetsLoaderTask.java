package com.artv.android.core.campaign.asset_load;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.artv.android.core.api.ApiConst;
import com.artv.android.core.campaign.VideoFilesHolder;
import com.artv.android.core.model.Asset;

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
 * Created by ZOG on 7/30/2015.
 */
public final class AssetsLoaderTask extends AsyncTask<Void, Double, Boolean> {

    private List<Asset> mAssets;
    private IDownloadAssetsListener mDownloadAssetsListener;
    private VideoFilesHolder mVideoFilesHolder;

    private static final String FOLDER_NAME = "artv_data";
    private static final String PATH = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME;

    private double totalPercent = 0;

    public final void setAssets(final List<Asset> _assets) {
        mAssets = _assets;
    }

    public final void setDownloadAssetsListener(final IDownloadAssetsListener _listener) {
        mDownloadAssetsListener = _listener;
    }

    public final void setVideoFilesHolder(final VideoFilesHolder _holder) {
        mVideoFilesHolder = _holder;
    }

    @Override
    protected final Boolean doInBackground(Void... _params) {
        final double percentsPerAsset = 10000.0 / mAssets.size();

        publishProgress(totalPercent);

        for (final Asset asset : mAssets) {
            notifyUiStartLoadingAsset(asset);
            try {
                loadAsset(asset, percentsPerAsset);
            } catch (final IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
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
    protected final void onPostExecute(final Boolean _success) {
        if (_success) mDownloadAssetsListener.onLoadFinished();
        else mDownloadAssetsListener.onLoadFailed("Loading failed");
    }

    private final void loadAsset(final Asset _asset, final double _percentsPerAsset) throws IOException {
        final URL url = buildUrlFrom(_asset.url);

        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(HttpGet.METHOD_NAME);

        final int respCode = conn.getResponseCode();

        final int buffLength = 4096;
        final int fileLength = conn.getContentLength();
        final int steps = (int) Math.floor(fileLength / (double) buffLength);
        final double percentsPerStep = _percentsPerAsset / steps;

        if (respCode == 200) {
            final File file = new File(PATH + _asset.url);
            mVideoFilesHolder.addFile(file);
            //if exist - publish progress and return
            final boolean reload = needReloadFile(file, fileLength);
            if (!reload) {
                totalPercent += _percentsPerAsset;
                publishProgress(totalPercent);
                return;
            }
            file.getParentFile().mkdirs();

            final InputStream inputStream = conn.getInputStream();
            final FileOutputStream fos = new FileOutputStream(file);

            int len;
            byte[] buffer = new byte[buffLength];
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                totalPercent += percentsPerStep;
                publishProgress(totalPercent);
            }
            fos.close();
            inputStream.close();
        }
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
                .scheme(ApiConst.PROTOCOL)
                .authority(ApiConst.SERVER_AUTHORITY)
                .path(_path)
                .build();

        final String address = URLDecoder.decode(uri.toString(), "utf-8");
        return new URL(address);
    }

    private final boolean needReloadFile(final File _file, final int _fileSize) {
        if (_file.exists() && _file.length() == _fileSize) return false;
        return true;
    }

}