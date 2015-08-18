package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;
import com.artv.android.core.model.GlobalConfig;

/**
 * Created by
 * Rogach on 30.06.2015.
 */
public final class GetGlobalConfigResponseObject extends BaseResponseObject {

    public GlobalConfig globalConfig;

    public GetGlobalConfigResponseObject() {
        apiType = ApiType.GET_GLOBAL_CONFIG;
    }

}
