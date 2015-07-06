package com.artv.android.core.api.rest_client;

import android.provider.SyncStateContract;

import com.artv.android.core.api.ApiConst;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * Created by Rogach on 01.07.2015.
 */
abstract class BaseRestClient {

    protected static Client getDefaultClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(ApiConst.CONN_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(ApiConst.READ_TIMEOUT, TimeUnit.SECONDS);
        return new OkClient(client);
    }

}
