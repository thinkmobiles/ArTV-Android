package com.artv.android.core.campaign;

import android.os.Handler;
import android.os.Looper;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.artv.android.core.campaign.CampaignHelper.*;

/**
 * Created by ZOG on 7/28/2015.
 */
public final class CampaignWorker {

    private ApiWorker mApiWorker;
    private InitData mInitData;
    private ConfigInfo mConfigInfo;
    private Set<ICampaignsDownloadListener> mCampaignLoadListeners;

    private List<Campaign> mCampaigns;

    public CampaignWorker() {
        mCampaignLoadListeners = new HashSet<>();
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

    public final boolean addCampaignLoadListener(final ICampaignsDownloadListener _listener) {
        return mCampaignLoadListeners.add(_listener);
    }

    public final boolean removeCampaignLoadListener(final ICampaignsDownloadListener _listener) {
        return mCampaignLoadListeners.remove(_listener);
    }

    private final void notifyProgressMessage(final String _message) {
        for (final ICampaignsDownloadListener listener : mCampaignLoadListeners) listener.progressMessage(_message);
    }

    private final void notifyProgress(final int _percent) {
        for (final ICampaignsDownloadListener listener : mCampaignLoadListeners) listener.onProgress(_percent);
    }

    private final void notifyCampaignLoaded(final CampaignLoadResult _result) {
        for (final ICampaignsDownloadListener listener : mCampaignLoadListeners) listener.onCampaignLoaded(_result);
    }

    private final void notifyCampaignLoadFailed(final CampaignLoadResult _result) {
        for (final ICampaignsDownloadListener listener : mCampaignLoadListeners) listener.onCampaignLoadFailed(_result);
    }

    public final boolean hasCampaign() {
        return false;
    }

    public final void doCampaignLogic() {
        getCampaign(0);

    }

    private final void getCampaign(final int _id) {
        final GetCampaignRequestObject requestObject = new GetCampaignRequestObject.Builder()
                .setToken(mInitData.getToken())
                .setTagID(mConfigInfo.getDeviceId())
                .setCampaignID(_id)
                .build();

        mApiWorker.doGetCampaign(requestObject, new WebRequestCallback<GetCampaignResponseObject>() {
            @Override
            public final void onSuccess(final GetCampaignResponseObject _respObj) {
                mCampaigns = _respObj.campaigns;
                notifyProgressMessage(_respObj.apiType + " : success\n"
                                + "Campaigns: " + getCampaignsCount(mCampaigns) + "\n"
                                + "Assets: " + getAssetsCount(mCampaigns)
                );
                initLoading();
            }

            @Override
            public final void onFailure(final ErrorResponseObject _errorResp) {
                notifyCampaignLoadFailed(buildCampaignLoadResult(false, _errorResp.apiType + ": " + _errorResp.error));
            }
        });
    }

    private final CampaignLoadResult buildCampaignLoadResult(final boolean _success, final String _message) {
        return new CampaignLoadResult.Builder()
                .setSuccess(_success)
                .setMessage(_message + (_success ? "" : " \tfuuuuuuuuuu"))
                .build();
    }

    private Iterator<Campaign> campaignIterator;
    private Iterator<Asset> assetIterator;

    private final void initLoading() {
        campaignIterator = mCampaigns.iterator();
        loadCampaigns();
    }

    private final void loadCampaigns() {
        notifyProgressMessage("Begin loading campaigns");

        if (campaignIterator.hasNext()) {
            loadCampaign();
        } else {
            notifyCampaignLoaded(buildCampaignLoadResult(true, "All campaigns loaded"));
        }
    }

    private final void loadCampaign() {
        final Campaign campaign = campaignIterator.next();
        notifyProgressMessage("Loading campaign with id = " + campaign.campaignId);

        assetIterator = campaign.assets.iterator();
        loadAssets();
    }

    private final void loadAssets() {
        notifyProgressMessage("Begin loading assets");

        if (assetIterator.hasNext()) {
            loadAsset();
        } else {
            notifyProgressMessage("All assets loaded");
            loadCampaigns();
        }
    }

    private final void loadAsset() {
        final Asset asset = assetIterator.next();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final int slept = 1000 + new Random(this.hashCode()).nextInt(4000);
                try {
                    Thread.sleep(slept);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public final void run() {
                            notifyProgressMessage("load " + asset.name + " finished " + slept);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        loadAssets();

    }

    private interface CampaignLoadCallback {
        void campaignLoaded();
    }

    private interface AssetLoadCallback {
        void assetLoaded();
    }

    private final CampaignLoadCallback campaignLoadCallback = new CampaignLoadCallback() {
        @Override
        public void campaignLoaded() {

        }
    };

    private final AssetLoadCallback assetLoadCallback = new AssetLoadCallback() {
        @Override
        public final void assetLoaded() {

        }
    };


    private final void loadAssets_test() {
        notifyProgressMessage("Loading assets:");
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Task("task 1"));
        executor.execute(new Task("task 2"));
        executor.execute(new Task("task 3"));
        executor.execute(new Task("task 4"));
        executor.execute(new Task("task 5"));
        executor.execute(new Task("task 6"));
        executor.execute(new Task("task 7"));
        executor.execute(new Task("task 8"));
        executor.execute(new Task("task 9"));
        executor.execute(new Task("task 10"));
        executor.execute(new Task("task 11"));
        executor.execute(new Task("task 12"));
        executor.execute(new Task("task 13"));
        executor.execute(new Task("task 14"));
    }


    private final class MyExecutor implements Executor {
        @Override
        public final void execute(final Runnable _command) {
            new Thread(_command).start();
        }
    }

    private final class Task implements Runnable {

        private String mName;

        public Task(final String _name) {
            mName = _name;
        }

        @Override
        public final void run() {
            try {
                final int slept = 1000 + new Random(this.hashCode()).nextInt(4000);
                Thread.sleep(slept);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public final void run() {
                        notifyProgressMessage(mName + " finished " + slept);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
