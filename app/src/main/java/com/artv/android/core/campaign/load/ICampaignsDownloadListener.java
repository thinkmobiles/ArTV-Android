package com.artv.android.core.campaign.load;

/**
 * Created by ZOG on 7/28/2015.
 */
public interface ICampaignsDownloadListener {

    void progressMessage(final String _message);
    void onProgress(final int _percent);
    void onCampaignLoaded(final CampaignLoadResult _result);
    void onCampaignLoadFailed(final CampaignLoadResult _result);

}
