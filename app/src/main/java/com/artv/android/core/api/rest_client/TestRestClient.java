package com.artv.android.core.api.rest_client;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by
 * Rogach on 01.07.2015.
 */
public abstract class TestRestClient {

    private static MainTVApi REST_CLIENT;
    public static final String API_URL = "http://tvm.hasbrain.ru/api";

    static {
        setupRestClient();
    }

    private TestRestClient() {}

    public static MainTVApi getApiService() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter restAdapter = getAdapter(API_URL);
        REST_CLIENT = restAdapter.create(MainTVApi.class);
    }

    private static RestAdapter getAdapter(final String _url) {
        OkHttpClient ok = new OkHttpClient();

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(_url)
                .setConverter(new SimpleXMLConverter())
                .setClient(new OkClient(ok))
                .build();
    }

}
