package com.artv.android.core.campaign.old.asset_load;

import com.artv.android.core.model.Asset;

/**
 * Used when downloading both all assets and single asstes.
 *
 * Created by ZOG on 7/29/2015.
 */
public interface IDownloadAssetsListener {

    //percent accuracy - 2 digits, i.e. 43.65%
    void onTotalProgressChanged(final double _percent);
    void onStartLoadingAsset(final Asset _asset);
    void onLoadFinished();
    void onLoadFailed(final String _error);

}
