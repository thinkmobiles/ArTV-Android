package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ZOG on 7/8/2015.
 */

@Root(name = "DeviceConfigXML")
public final class DeviceConfig {

    @Element(name = "TagID", required = false)
    public String tagId;

    @Element(name = "FTPUsr", required = false)
    public String fTPUsr;

    @Element(name = "FTPPwd", required = false)
    public String fTPPwd;

    @Element(name = "HomeFolder", required = false)
    public String homeFolder;

    @Element(name = "SerialNo", required = false)
    public long serialNo;

    @Element(name = "Latitude", required = false)
    public double latitude;

    @Element(name = "Longitude", required = false)
    public double longitude;

    @Element(name = "TurnOnDisp", required = false)
    public String turnOnDisp;

    @Element(name = "TurnOffDisp", required = false)
    public String turnOffDisp;

    @Element(name = "DevGroup", required = false)
    public int devGroup;

}
