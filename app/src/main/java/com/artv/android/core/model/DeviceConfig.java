package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ZOG on 7/8/2015.
 */

@Root(name = "DeviceConfigXML")
public final class DeviceConfig {

    @Element(name = "TagID")
    public String tagID;

    @Element(name = "FTPUsr")
    public String fTPUsr;

    @Element(name = "FTPPwd")
    public String fTPPwd;

    @Element(name = "HomeFolder")
    public String homeFolder;

    @Element(name = "SerialNo")
    public long serialNo;

    @Element(name = "Latitude")
    public double latitude;

    @Element(name = "Longitude")
    public double longitude;

    @Element(name = "TurnOnDisp")
    public int turnOnDisp;

    @Element(name = "TurnOffDisp")
    public int turnOffDisp;

    @Element(name = "DevGroup")
    public int devGroup;

}
