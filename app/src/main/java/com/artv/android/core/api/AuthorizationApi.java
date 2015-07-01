package com.artv.android.core.api;

import com.artv.android.core.api.api_model.response.GetTokenResponseObject;

import retrofit.Callback;
import retrofit.http.GET;


/**
 * Created by
 * Rogach on 01.07.2015.
 */

public interface AuthorizationApi {


    @GET("/GetToken.xml")
    void getToken(
//            @Body GetTokenRequestObject _request,
            Callback<GetTokenResponseObject> _callback);
}
