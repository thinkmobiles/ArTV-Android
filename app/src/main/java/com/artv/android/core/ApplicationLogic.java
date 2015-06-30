package com.artv.android.core;

import android.content.Context;

import com.artv.android.core.api.ApiWorker;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ApplicationLogic {

    private Context mContext;

    private ApiWorker mApiWorker;

    public ApplicationLogic(final Context _context) {
        mContext = _context;

        mApiWorker = new ApiWorker(mContext);
    }

    public final ApiWorker getApiWorker() {
        return mApiWorker;
    }

}
