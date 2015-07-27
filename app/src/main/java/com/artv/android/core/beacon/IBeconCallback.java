package com.artv.android.core.beacon;

import com.artv.android.core.init.InitResult;

/**
 * Created by ZOG on 7/8/2015.
 */
public interface IBeconCallback {

    void onBeconSuccess(final BeaconResult _result);
    void onInitFail(final BeaconResult _result);

}
