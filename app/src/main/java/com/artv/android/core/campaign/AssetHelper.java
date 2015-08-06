package com.artv.android.core.campaign;

import android.net.Uri;
import android.os.Environment;

import com.artv.android.core.ArTvResult;
import com.artv.android.core.Constants;
import com.artv.android.core.api.ApiConst;
import com.artv.android.core.model.Asset;

import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZOG on 8/3/2015.
 */
public final class AssetHelper {

    private IAssetLoadProgressListener mProgressListener;

    public void setProgressListener(IAssetLoadProgressListener _progressListener) {
        mProgressListener = _progressListener;
    }

    public final ArTvResult loadAsset(final CampaignsLoaderTask _task, final Asset _asset, final double _progressPerAsset) {
        final ArTvResult.Builder result = new ArTvResult.Builder();

        try {
            final URL url = buildUrlFrom(_asset.url);

            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod(HttpGet.METHOD_NAME);

            final int respCode = conn.getResponseCode();

            final int buffLength = 4096;
            final int fileLength = conn.getContentLength();

            if (respCode == 200) {
                final File file = new File(Constants.PATH + _asset.url);
                file.getParentFile().mkdirs();

                final InputStream inputStream = conn.getInputStream();
                final FileOutputStream fos = new FileOutputStream(file);

                int read;
                byte[] buffer = new byte[buffLength];
                while ((read = inputStream.read(buffer)) != -1) {
                    if (_task.isCancelled()) {
                        result.setSuccess(false);
                        return result.build();
                    }
                    fos.write(buffer, 0, read);
                    final double writeProgress = read / (double) fileLength * _progressPerAsset;
                    mProgressListener.onProgressLoaded(writeProgress);
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

    private final boolean needReloadFile(final File _file, final int _fileSize) {
        if (_file.exists() && _file.length() == _fileSize) return false;
        return true;
    }

}
