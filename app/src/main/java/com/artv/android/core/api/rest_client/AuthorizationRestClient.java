package com.artv.android.core.api.rest_client;


import com.artv.android.core.api.ApiConst;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by
 * Rogach on 01.07.2015.
 */

public class AuthorizationRestClient extends BaseRestClient {

    private static AuthorizationApi mAuthorizationApi;

    public static AuthorizationApi getApi() {
        if (mAuthorizationApi == null) {
            setupRestClient();
        }
        return mAuthorizationApi;
    }

    private static void setupRestClient() {

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ApiConst.getAuthority())
                .setClient(getDefaultClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new SimpleXMLConverter())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade request) {
                        request.addHeader("Content-Type", "application/xml; charset=UTF-8");
                    }
                });

        RestAdapter adapter = builder.build();
        mAuthorizationApi = adapter.create(AuthorizationApi.class);
    }
}