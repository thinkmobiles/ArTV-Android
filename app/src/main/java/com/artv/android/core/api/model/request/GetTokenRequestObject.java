package com.artv.android.core.api.model.request;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.model.BaseRequestObject;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class GetTokenRequestObject extends BaseRequestObject {

    public GetTokenRequestObject() {
        apiType = ApiType.GET_TOKEN;
    }
}
