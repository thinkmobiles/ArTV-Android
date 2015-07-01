package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ZOG on 6/30/2015.
 */

@Root(name = "api")
public final class GetTokenResponseObject extends BaseResponseObject {

    @Element(name = "Token")
    private String mToken;

    @Element(name = "ErrorNumber")
    private int mErrorNumber;

    @Element(name = "ErrorDescription", required = false)
    private int mErrorDescription;

    public GetTokenResponseObject() {
        apiType = ApiType.GET_TOKEN;
    }
}
