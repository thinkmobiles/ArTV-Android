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

    @Element(name = "Token", required = false)
    public String mToken;

    @Element(name = "ErrorNumber")
    public int mErrorNumber;

    @Element(name = "ErrorDescription", required = false)
    public String mErrorDescription;

    public GetTokenResponseObject() {
        apiType = ApiType.GET_TOKEN;
    }
}
