package com.artv.android.core.api;

import android.content.Context;

import com.artv.android.core.api.api_model.ErrorResponseObject;
import com.artv.android.core.api.api_model.request.BeaconRequestObject;
import com.artv.android.core.api.api_model.request.GetCampaignRequestObject;
import com.artv.android.core.api.api_model.request.GetDeviceConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetGlobalConfigRequestObject;
import com.artv.android.core.api.api_model.request.GetTokenRequestObject;
import com.artv.android.core.api.api_model.response.BeaconResponseObject;
import com.artv.android.core.api.api_model.response.GetCampaignResponseObject;
import com.artv.android.core.api.api_model.response.GetDeviceConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetGlobalConfigResponseObject;
import com.artv.android.core.api.api_model.response.GetTokenResponseObject;
import com.artv.android.core.api.rest_client.TestRestClient;
import com.artv.android.core.model.DeviceConfig;

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
        TestRestClient.getApiService().getToken(_requestObject.getQuery(), new Callback<GetTokenResponseObject>() {
            @Override
            public void success(GetTokenResponseObject getTokenResponseObject, Response _response) {
                if (_response != null) {
                    if(getTokenResponseObject.mErrorNumber == 0) {
                        _callback.onSuccess(getTokenResponseObject);
                    } else
                        _callback.onFailure(new ErrorResponseObject.Builder()
                                .setApiType(_requestObject.apiType)
                                .setError(getTokenResponseObject.mErrorDescription)
                                .build());
                }
            }

            @Override
            public void failure(RetrofitError _error) {
                final ErrorResponseObject error = new ErrorResponseObject.Builder()
                        .setApiType(_requestObject.apiType)
                        .setError(_error.getMessage())
                        .build();
                _callback.onFailure(error);
            }
        });
    }

    public final void doGetGlobalConfig(final GetGlobalConfigRequestObject _requestObject,
                                 final WebRequestCallback<GetGlobalConfigResponseObject> _callback) {
        TestRestClient.getApiService().getGlobalConfig(_requestObject.getQuery(), new Callback<GetGlobalConfigResponseObject>() {

            @Override
            public void success(GetGlobalConfigResponseObject _getGlobalConfigResponseObject, Response _response) {
                if (_response != null) {
                    _callback.onSuccess(_getGlobalConfigResponseObject);
                }
            }

            @Override
            public void failure(RetrofitError _error) {
                if (_error != null) {
                    final ErrorResponseObject error = new ErrorResponseObject.Builder()
                            .setApiType(_requestObject.apiType)
                            .setError(_error.getMessage())
                            .build();
                    _callback.onFailure(error);
                }
            }
        });
    }

    public final void doGetDeviceConfig(final GetDeviceConfigRequestObject _requestObject,
                                        final WebRequestCallback<GetDeviceConfigResponseObject> _callback) {
        TestRestClient.getApiService().getDeviceConfig(_requestObject.getQuery(), new Callback<DeviceConfig>() {

            @Override
            public void success(DeviceConfig _deviceConfig, Response _response) {
                if (_response != null) {
                    final GetDeviceConfigResponseObject response = new GetDeviceConfigResponseObject();
                    response.setDeviceConfig(_deviceConfig);
                    _callback.onSuccess(response);
                }
            }

            @Override
            public void failure(RetrofitError _error) {
                if (_error != null) {
                    final ErrorResponseObject error = new ErrorResponseObject.Builder()
                            .setApiType(_requestObject.apiType)
                            .setError(_error.getMessage())
                            .build();
                    _callback.onFailure(error);
                }
            }
        });
    }

    public final void doBeacon(final BeaconRequestObject _requestObject,
                               final WebRequestCallback<BeaconResponseObject> _callback) {
        TestRestClient.getApiService().beacon(_requestObject.getQuery(), _requestObject.beacon,
                new Callback<BeaconResponseObject>() {
                    @Override
                    public final void success(final BeaconResponseObject _beaconResponseObject, final Response _response) {
                        if (_response != null) {
                            _callback.onSuccess(_beaconResponseObject);
                        }
                    }

                    @Override
                    public final void failure(final RetrofitError _error) {
                        if (_error != null) {
                            final ErrorResponseObject error = new ErrorResponseObject.Builder()
                                    .setApiType(_requestObject.apiType)
                                    .setError(_error.getMessage())
                                    .build();
                            _callback.onFailure(error);
                        }
                    }
                });

    }


    public final void doGetCampaign(final GetCampaignRequestObject _requestObject,
                                        final WebRequestCallback<GetCampaignResponseObject> _callback) {
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
                    final ErrorResponseObject error = new ErrorResponseObject.Builder()
                            .setApiType(_requestObject.apiType)
                            .setError(_error.getMessage())
                            .build();
                    _callback.onFailure(error);
                }
            }
        });
    }
}
