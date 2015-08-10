package com.artv.android.core.campaign;

import com.artv.android.ArTvResult;

/**
 * Created by ZOG on 8/6/2015.
 */
public interface ICampaignDownloadListener {

    void onCampaignDownloadFinished(final ArTvResult _result);
    void onPercentLoaded(final double _percent);

}
