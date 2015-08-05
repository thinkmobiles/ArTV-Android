package com.artv.android.core.api.rest_client;

import android.text.TextUtils;

import com.artv.android.core.api.ApiConst;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by
 * Rogach on 01.07.2015.
 */

public class MainRestClient extends BaseRestClient {

    private static MainRestClient mClient;
    private MainTVApi mMainApi;

    public static void initClient(final String userToken){
        if (TextUtils.isEmpty(userToken)) {
            throw new IllegalArgumentException("Token is empty");
        }
        mClient = new MainRestClient(userToken);
    }

    private MainRestClient(final String userToken){
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ApiConst.getAuthority())
                .setClient(getDefaultClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new SimpleXMLConverter())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade request) {
                        //TODO need add token in header
                    }
                });

        RestAdapter adapter = builder.build();
        mMainApi = adapter.create(MainTVApi.class);
    }


    public static MainRestClient getClient(){
        if (mClient == null) {
            throw new NullPointerException("Client isn't initialized");
        }else {
            return mClient;
        }
    }

    public MainTVApi getMainApi(){
        return mMainApi;
    }

}
