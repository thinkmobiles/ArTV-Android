package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;
import com.artv.android.core.model.DeviceConfig;

/**
 * Created by
 * Rogach on 30.06.2015.
 */

public final class GetDeviceConfigResponseObject extends BaseResponseObject {

    private DeviceConfig deviceConfig;

    public GetDeviceConfigResponseObject() {
        apiType = ApiType.GET_DEVICE_CONFIG;
        deviceConfig = new DeviceConfig();
    }

    public final DeviceConfig getDeviceConfig() {
        return deviceConfig;
    }
}
