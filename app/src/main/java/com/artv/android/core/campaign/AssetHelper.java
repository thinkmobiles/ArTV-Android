package com.artv.android.core.campaign;

import android.net.Uri;
import android.os.Environment;

import com.artv.android.core.api.ApiConst;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZOG on 8/3/2015.
 */
public final class AssetHelper {

    private ExecutorService mExecutor;
    private IAssetLoadProgressListener mProgressListener;

    private static final String FOLDER_NAME = "artv_data";
    private static final String PATH = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME;

    public AssetHelper() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public void setProgressListener(IAssetLoadProgressListener _progressListener) {
        mProgressListener = _progressListener;
    }

    public final AssetLoadResult loadAsset(final Asset _asset, final double _progressPerAsset) throws IOException {
        final AssetLoadResult result = new AssetLoadResult();
        final URL url = buildUrlFrom(_asset.url);

        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(HttpGet.METHOD_NAME);

        final int respCode = conn.getResponseCode();

        final int buffLength = 4096;
        final int fileLength = conn.getContentLength();

        if (respCode == 200) {
            final File file = new File(PATH + _asset.url);

            //if exist - publish progress and return
            final boolean reload = needReloadFile(file, fileLength);
            if (!reload) {
                mProgressListener.onProgressLoaded(_progressPerAsset);
                result.success = true;
                return result;
            }
            file.getParentFile().mkdirs();

            final InputStream inputStream = conn.getInputStream();
            final FileOutputStream fos = new FileOutputStream(file);

            int readed;
            byte[] buffer = new byte[buffLength];
            while ((readed = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, readed);
                final double writeProgress = readed / (double) fileLength * _progressPerAsset;
                mProgressListener.onProgressLoaded(writeProgress);
            }
            fos.close();
            inputStream.close();

            result.success = true;
            return result;
        }

        result.success = false;
        result.message = "Erorr: response code = " + respCode;
        return result;
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
