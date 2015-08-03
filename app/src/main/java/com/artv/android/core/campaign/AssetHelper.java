package com.artv.android.core.campaign;

import com.artv.android.core.model.Asset;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ZOG on 8/3/2015.
 */
public final class AssetHelper {

    private ExecutorService mExecutor;
    private IAssetLoadProgressListener mProgressListener;

    public AssetHelper() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public void setProgressListener(IAssetLoadProgressListener _progressListener) {
        mProgressListener = _progressListener;
    }

    public final boolean loadAsset(final Asset _asset, final double _progressPerAsset) {
        final double progressPerAssetStep = _progressPerAsset / 20;  //download for 20 steps

        final Future<Boolean> future = mExecutor.submit(new Callable<Boolean>() {
            @Override
            public final Boolean call() throws Exception {
                for (int i = 0; i < 20; i++) {
                    mProgressListener.onProgressLoad(progressPerAssetStep);
                    Thread.sleep(40);
                }
                return true;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

}
