package com.artv.android.core.campaign;

import android.os.Handler;
import android.os.Looper;

import com.artv.android.core.api.ApiWorker;
import com.artv.android.core.api.WebRequestCallback;
import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.campaign.load.CampaignLoadResult;
import com.artv.android.core.campaign.load.CampaignLoader;
import com.artv.android.core.campaign.load.ICampaignsDownloadListener;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.init.InitData;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    private CampaignLoader mCampaignLoader;

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
//        getCampaign(0);

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
        notifyProgressMessage("Begin loading campaigns");
        loadCampaigns();
    }

    private final void loadCampaigns() {
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
        if (assetIterator.hasNext()) {
            loadAsset();
        } else {
            notifyProgressMessage("All assets loaded");
            loadCampaigns();
        }
    }

    private final void loadAsset() {
        final Asset asset = assetIterator.next();
        notifyProgressMessage("Loading asset " + asset.name + " ...");

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
                            loadAssets();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
