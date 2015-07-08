package com.artv.android.core.api.api_model.response;

import com.artv.android.core.api.ApiType;
import com.artv.android.core.api.api_model.BaseResponseObject;
import com.artv.android.core.model.DeviceConfig;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by
 * Rogach on 30.06.2015.
 */

@Root(name = "DeviceConfigXML")
public final class GetDeviceConfigResponseObject extends BaseResponseObject {

    @Element(name = "TagID")
    private String mTagID;

    @Element(name = "FTPUsr")
    private String mFTPUsr;

    @Element(name = "FTPPwd")
    private String mFTPPwd;

    @Element(name = "HomeFolder")
    private String mHomeFolder;

    @Element(name = "SerialNo")
    private long mSerialNo;

    @Element(name = "Latitude")
    private double mLatitude;

    @Element(name = "Longitude")
    private double mLongitude;

    @Element(name = "TurnOnDisp")
    private int mTurnOnDisp;

    @Element(name = "TurnOffDisp")
    private int mTurnOffDisp;

    @Element(name = "DevGroup")
    private int mDevGroup;

    public GetDeviceConfigResponseObject() {
        apiType = ApiType.GET_DEVICE_CONFIG;
    }

    //todo: rework to root element
    public final DeviceConfig getDeviceConfig() {
        final DeviceConfig deviceConfig = new DeviceConfig();
        deviceConfig.tagID = mTagID;
        deviceConfig.fTPUsr = mFTPUsr;
        deviceConfig.fTPPwd = mFTPPwd;
        deviceConfig.homeFolder = mHomeFolder;
        deviceConfig.serialNo = mSerialNo;
        deviceConfig.latitude = mLatitude;
        deviceConfig.longitude = mLongitude;
        deviceConfig.turnOnDisp = mTurnOnDisp;
        deviceConfig.turnOffDisp = mTurnOffDisp;
        deviceConfig.devGroup = mDevGroup;
        return deviceConfig;
    }
}
