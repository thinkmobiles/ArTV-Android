package com.artv.android.core.beacon;

import com.artv.android.core.init.InitResult;

/**
 * Created by ZOG on 7/8/2015.
 */
public interface IBeaconCallback {

    void onBeaconSuccess(final BeaconResult _result);
    void onBeaconFail(final BeaconResult _result);

}
