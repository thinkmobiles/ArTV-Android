package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.api_model.BaseResponseObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ZOG on 7/10/2015.
 */
@Root
public final class BeaconResponseObject extends BaseResponseObject {

    @Element(name = "ErrorNumber", required = false)
    public int errorNumber;

    @Element(name = "ErrorDescription", required = false)
    public String errorDescription;

}
