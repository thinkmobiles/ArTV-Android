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
    public String token;

    @Element(name = "ErrorNumber")
    public int errorNumber;

    @Element(name = "ErrorDescription", required = false)
    public String errorDescription;

    public GetTokenResponseObject() {
        apiType = ApiType.GET_TOKEN;
    }
}
