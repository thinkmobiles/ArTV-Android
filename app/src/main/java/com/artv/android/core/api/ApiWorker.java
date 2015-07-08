package com.artv.android.core.api;

import android.content.Context;

import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetGlobalConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;
import com.artv.android.core.api.rest_client.TestRestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ApiWorker {

    private Context mContext;

    public ApiWorker(final Context _context) {
        mContext = _context;
    }

    public final void doGetToken(final GetTokenRequestObject _requestObject,
                                 final WebRequestCallback<GetTokenResponseObject> _callback) {
        //todo: implement
        TestRestClient.getApiService().getToken(new Callback<GetTokenResponseObject>() {
            @Override
            public void success(GetTokenResponseObject getTokenResponseObject, Response response) {
                _callback.onSuccess(getTokenResponseObject);
            }

            @Override
            public void failure(RetrofitError _error) {
                final ErrorResponseObject error = new ErrorResponseObject.Builder().setError(_error.getMessage()).build();
                _callback.onFailure(error);
            }
        });
    }

    public final void doGetGlobalConfig(final GetGlobalConfigRequestObject _requestObject,
                                 final WebRequestCallback<GetGlobalConfigResponseObject> _callback) {
        //todo: implement
        TestRestClient.getApiService().getGlobalConfig(new Callback<GetGlobalConfigResponseObject>() {

            @Override
            public void success(GetGlobalConfigResponseObject _getGlobalConfigResponseObject, Response _response) {
                if (_response != null) {
                    _callback.onSuccess(_getGlobalConfigResponseObject);
                }
            }
            @Override
            public void failure(RetrofitError _error) {
                if (_error != null) {
                    final ErrorResponseObject error = new ErrorResponseObject.Builder().setError(_error.getMessage()).build();
                    _callback.onFailure(error);
                }
            }
        });
    }

    public final void doGetDeviceConfig(final GetDeviceConfigRequestObject _requestObject,
                                        final WebRequestCallback<GetDeviceConfigResponseObject> _callback) {
        //todo: implement
        TestRestClient.getApiService().getDeviceConfig(new Callback<GetDeviceConfigResponseObject>() {

            @Override
            public void success(GetDeviceConfigResponseObject _getDeviceConfigResponseObject, Response _response) {
                if (_response != null) {
                    _callback.onSuccess(_getDeviceConfigResponseObject);
                }
            }
            @Override
            public void failure(RetrofitError _error) {
                if (_error != null) {
                    final ErrorResponseObject error = new ErrorResponseObject.Builder().setError(_error.getMessage()).build();
                    _callback.onFailure(error);
                }
            }
        });
    }

    public final void doGetCampaign(final GetCampaignRequestObject _requestObject,
                                        final WebRequestCallback<GetCampaignResponseObject> _callback) {
        //todo: implement

        TestRestClient.getApiService().getCampaign(new Callback<GetCampaignResponseObject>() {

            @Override
            public void success(GetCampaignResponseObject _getCampaignResponseObject, Response _response) {
                if (_response != null) {
                    _callback.onSuccess(_getCampaignResponseObject);
                }
            }
            @Override
            public void failure(RetrofitError _error) {
                if (_error != null) {
                    final ErrorResponseObject error = new ErrorResponseObject.Builder().setError(_error.getMessage()).build();
                    _callback.onFailure(error);
                }
            }
        });
    }
}
