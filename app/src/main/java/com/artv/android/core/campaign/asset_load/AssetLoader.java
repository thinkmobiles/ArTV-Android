package com.artv.android.core.campaign.asset_load;

import com.artv.android.core.model.Asset;

import java.util.List;

/**
 * Created by ZOG on 7/30/2015.
 */
public final class AssetLoader {

    public final void downloadAssets(final List<Asset> _assets, final IDownloadAssetsListener _listener) {
        final AssetsLoaderTask loaderTask = new AssetsLoaderTask();
        loaderTask.setAssets(_assets);
        loaderTask.setDownloadAssetsListener(_listener);
        loaderTask.execute();
    }

}
