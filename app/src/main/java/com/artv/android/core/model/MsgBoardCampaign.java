package com.artv.android.core.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Rogach on 30.06.2015.
 */
@Root(name = "MsgBoardCampaign")
public final class MsgBoardCampaign {

    @Element(name = "MsgBoardID", required = false)
    public int msgBoardID;

    @Element(name = "CRCVersion", required = false)
    public int crcVersion;

    @Element(name = "StartDate", required = false)
    public String startDate;

    @Element(name = "EndDate", required = false)
    public String endDate;

    @Element(name = "PlayDay", required = false)
    public String playDay;

    @Element(name = "TextColor", required = false)
    public String textColor;

    @Element(name = "RightBkgURL", required = false)
    public String rightBkgURL;

    @Element(name = "BottomBkgURL", required = false)
    public String bottomBkgURL;

    @ElementList(name = "Messages", required = false)
    public List<Message> messages;

}
