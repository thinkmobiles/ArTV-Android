package com.artv.android.core.init;

import com.artv.android.ArTvResult;

/**
 * Created by ZOG on 7/8/2015.
 *
 * todo: remove onProgress, use logger instead
 */
public interface IInitCallback {

    void onInitSuccess(final ArTvResult _result);
    void onProgress(final ArTvResult _result);
    void onInitFail(final ArTvResult _result);

}
