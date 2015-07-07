package com.artv.android.core.config_info;

/**
 * Created by ZOG on 7/6/2015.
 */
public interface IConfigInfoListener {

    void onEnteredConfigInfo(final ConfigInfo _configInfo);
    void onNeedRemoveConfigInfo();

}