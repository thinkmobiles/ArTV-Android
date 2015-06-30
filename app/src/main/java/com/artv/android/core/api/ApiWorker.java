package com.artv.android.core.api;

import android.content.Context;

import com.artv.android.core.api.model.request.GetTokenRequestObject;
import com.artv.android.core.api.model.response.GetTokenResponseObject;

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
    }

}
