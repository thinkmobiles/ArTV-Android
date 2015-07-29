package com.artv.android.core.campaign.load;

/**
 * Created by ZOG on 7/29/2015.
 */
public interface IDownloadAssetsListener {

    void onLoadFailed(final String _error);
    void onProgress(final int _percent);
    void onLoadFinished();

}
