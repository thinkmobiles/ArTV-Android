package com.artv.android.core.beacon;

import com.artv.android.ArTvResult;

/**
 * Created by ZOG on 7/8/2015.
 */
public interface IBeaconCallback {

    void onBeaconSuccess(final ArTvResult _result);
    void onBeaconFail(final ArTvResult _result);

}
