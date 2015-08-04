package com.artv.android.database;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.gen.DBAsset;
import com.artv.android.database.gen.DBCampaign;
import com.artv.android.database.gen.DBMessage;
import com.artv.android.database.gen.DBmsgBoardCampaign;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Misha on 7/16/2015.
 */
public final class Transformer {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM");
    private Transformer() {}

    protected static List<DBCampaign> createDBCampaignList(List<Campaign> campaigns) {
        ArrayList<DBCampaign> dbCampaigns = new ArrayList<>(campaigns.size());

        for (Campaign campaign : campaigns)
            dbCampaigns.add(createDBCampaign(campaign));

        return dbCampaigns;
    }

    protected static List<DBmsgBoardCampaign> createDBmsgCampaignList(List<MsgBoardCampaign> msgBoardCampaigns) {
        ArrayList<DBmsgBoardCampaign> dBmsgBoardCampaigns = new ArrayList<>(msgBoardCampaigns.size());

        for (MsgBoardCampaign msgBoardCampaign : msgBoardCampaigns)
            dBmsgBoardCampaigns.add(createDBmsgBoardCampaign(msgBoardCampaign));

        return dBmsgBoardCampaigns;
    }

    protected static List<Campaign> createCampaignList(List<DBCampaign> dbCampaigns) {
        ArrayList<Campaign> campaigns = new ArrayList<>(dbCampaigns.size());

        for (DBCampaign dbCampaign : dbCampaigns)
            campaigns.add(createCampaign(dbCampaign));

        return campaigns;
    }

    protected static List<MsgBoardCampaign> createMsgBoardCampaignList(List<DBmsgBoardCampaign> dBmsgBoardCampaigns) {
        ArrayList<MsgBoardCampaign> msgBoardCampaigns = new ArrayList<>(dBmsgBoardCampaigns.size());

        for (DBmsgBoardCampaign dBmsgBoardCampaign : dBmsgBoardCampaigns)
            msgBoardCampaigns.add(createMsgBoardCampaign(dBmsgBoardCampaign));

        return msgBoardCampaigns;
    }

    protected static DBCampaign createDBCampaign(Campaign campaign) {
        DBCampaign dbCampaign = new DBCampaign();
        dbCampaign.setCampaignId(campaign.campaignId);
        dbCampaign.setCrcVersion(campaign.crcVersion);
        dbCampaign.setStartDate(campaign.startDate);
        dbCampaign.setEndDate(campaign.endDate);
        dbCampaign.setPlayDay(campaign.playDay);
        dbCampaign.setOverrideTime(campaign.overrideTime);
        dbCampaign.setSequence(campaign.sequence);

        return dbCampaign;
    }

    protected static DBmsgBoardCampaign createDBmsgBoardCampaign(MsgBoardCampaign msgBoardCampaign) {
        DBmsgBoardCampaign dBmsgBoardCampaign = new DBmsgBoardCampaign();
        dBmsgBoardCampaign.setMsgBoardId(msgBoardCampaign.msgBoardId);
        dBmsgBoardCampaign.setBottomBkgURL(msgBoardCampaign.bottomBkgURL);
        dBmsgBoardCampaign.setRightBkgURL(msgBoardCampaign.rightBkgURL);
        dBmsgBoardCampaign.setCrcVersion(msgBoardCampaign.crcVersion);
        dBmsgBoardCampaign.setPlayDay(msgBoardCampaign.playDay);
        dBmsgBoardCampaign.setTextColor(msgBoardCampaign.textColor);
        dBmsgBoardCampaign.setStartDate(msgBoardCampaign.startDate);
        dBmsgBoardCampaign.setEndDate(msgBoardCampaign.endDate);

        return dBmsgBoardCampaign;
    }

    protected static Campaign createCampaign(DBCampaign dbCampaign) {
        Campaign campaign = new Campaign();
        campaign.campaignId = dbCampaign.getCampaignId();
        campaign.crcVersion = dbCampaign.getCrcVersion();
        campaign.startDate = simpleDateFormat.format(new Date(dbCampaign.getStartDate()));
        campaign.endDate = simpleDateFormat.format(new Date(dbCampaign.getEndDate()));
        campaign.overrideTime = dbCampaign.getOverrideTime();
        campaign.sequence = dbCampaign.getSequence();
        campaign.playDay = dbCampaign.getPlayDay();
        campaign.assets = createAssetsList(dbCampaign.getAssets());

        return campaign;
    }

    protected static MsgBoardCampaign createMsgBoardCampaign(DBmsgBoardCampaign dBmsgBoardCampaign) {
        MsgBoardCampaign msgBoardCampaign = new MsgBoardCampaign();
        msgBoardCampaign.bottomBkgURL = dBmsgBoardCampaign.getBottomBkgURL();
        msgBoardCampaign.crcVersion = dBmsgBoardCampaign.getCrcVersion();
        msgBoardCampaign.msgBoardId = dBmsgBoardCampaign.getMsgBoardId();
        msgBoardCampaign.textColor = dBmsgBoardCampaign.getTextColor();
        msgBoardCampaign.playDay = dBmsgBoardCampaign.getPlayDay();
        msgBoardCampaign.rightBkgURL = dBmsgBoardCampaign.getRightBkgURL();
        msgBoardCampaign.startDate = simpleDateFormat.format(new Date(dBmsgBoardCampaign.getStartDate()));
        msgBoardCampaign.endDate = simpleDateFormat.format(new Date(dBmsgBoardCampaign.getEndDate()));
        msgBoardCampaign.messages = createMessagesList(dBmsgBoardCampaign.getMessages());

        return msgBoardCampaign;
    }

    protected static List<DBAsset> createDBAssetsList(List<Asset> assetList, Integer campaignId) {
        ArrayList<DBAsset> dbAssets = new ArrayList<>(assetList.size());

        DBAsset a;
        for (Asset asset : assetList) {
            a = createDBAsset(asset);
            a.setCampaignId(campaignId);
            dbAssets.add(a);
        }

        return dbAssets;
    }

    protected static List<DBMessage> createDBMessageList(List<Message> messageList, Integer msgBoardID) {
        ArrayList<DBMessage> dbMessages = new ArrayList<>(messageList.size());

        DBMessage dbMessage;
        for (Message message : messageList) {
            dbMessage = createDBMessage(message);
            dbMessage.setMsgBoardID(msgBoardID);
            dbMessages.add(dbMessage);
        }

        return dbMessages;
    }

    protected static List<Asset> createAssetsList(List<DBAsset> dbAssetsList) {
        ArrayList<Asset> assets = new ArrayList<>(dbAssetsList.size());

        for (DBAsset dbAsset : dbAssetsList)
            assets.add(createAsset(dbAsset));

        return assets;
    }

    protected static List<Message> createMessagesList(List<DBMessage> dbMessages) {
        ArrayList<Message> messages = new ArrayList<>(dbMessages.size());

        for (DBMessage dbMessage : dbMessages)
            messages.add(createMessage(dbMessage));

        return messages;
    }

    private static DBAsset createDBAsset(Asset asset) {
        DBAsset dbAsset = new DBAsset();
        dbAsset.setSequence(asset.sequence);
        dbAsset.setDuration(asset.duration);
        dbAsset.setName(asset.name);
        dbAsset.setUrl(asset.url);

        return dbAsset;
    }

    private static Asset createAsset(DBAsset dbAsset) {
        Asset asset = new Asset();
        asset.sequence = dbAsset.getSequence();
        asset.duration = dbAsset.getDuration();
        asset.name = dbAsset.getName();
        asset.url = dbAsset.getUrl();

        return asset;
    }



    private static Message createMessage(DBMessage dbMessage) {
        Message message = new Message();
        message.position = dbMessage.getPosition();
        message.sequence = dbMessage.getSequence();
        message.text = dbMessage.getText();

        return message;
    }

    private static DBMessage createDBMessage(Message message) {
        DBMessage dbMessage = new DBMessage();
        dbMessage.setPosition(message.position);
        dbMessage.setSequence(message.sequence);
        dbMessage.setText(message.text);

        return dbMessage;
    }

    public static long getMillisecFromStringDate(String date) {
        return simpleDateFormat.parse(date, new ParsePosition(0)).getTime();
    }
}
