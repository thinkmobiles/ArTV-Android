package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;
import com.artv.android.core.model.Entry;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by
 * Rogach on 30.06.2015.
 */

@Root(name = "GlobalConfigXML")
public final class GetGlobalConfigResponseObject extends BaseResponseObject {

    @ElementList (entry = "Entry", inline = true)
    private List<Entry> list;

    public GetGlobalConfigResponseObject() {
        apiType = ApiType.GET_GLOBAL_GONFIG;
    }

}
