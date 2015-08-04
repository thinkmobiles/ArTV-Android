package com.artv.android.core.api.api_model.request;

import com.artv.android.core.api.ApiConst;
import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseRequestObject;
import com.artv.android.core.api.api_model.IQueryCreator;
import com.artv.android.core.model.Beacon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZOG on 7/10/2015.
 */
public final class BeaconRequestObject extends BaseRequestObject implements IQueryCreator {

    public String token;
    public String tagId;
    public Beacon beacon;

    public BeaconRequestObject() {
        apiType = ApiType.BEACON;
    }

    @Override
    public final Map<String, String> getQuery() {
        final Map<String, String> query = new HashMap<>(2);
        query.put(ApiConst.KEY_TOKEN, token);
        query.put(ApiConst.KEY_TAG_ID, tagId);
        return query;
    }
}
