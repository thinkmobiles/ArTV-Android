package com.artv.android.core.api.model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.model.BaseResponseObject;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class GetTokenResponseObject extends BaseResponseObject {

    public GetTokenResponseObject() {
        apiType = ApiType.GET_TOKEN;
    }
}
