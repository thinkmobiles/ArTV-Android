package com.artv.android.core.api.api_model.response;


import com.artv.android.core.api.ApiType;

/**
 * Created by ZOG on 7/10/2015.
 *
 * todo: check BeaconResponseObject and replace with GetCampaignResponseObject if possible
 */

public final class BeaconResponseObject extends GetCampaignResponseObject {

    public BeaconResponseObject() {
        apiType = ApiType.BEACON;
    }
}
