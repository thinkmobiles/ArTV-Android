package com.artv.android.core.init;

/**
 * Created by ZOG on 7/8/2015.
 */
public interface InitCallback {

    void onInitSuccess(final InitResult _result);
    void onInitFail(final InitResult _result);

}
