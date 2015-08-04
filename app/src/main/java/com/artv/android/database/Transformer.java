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
        dbCampaign.setId(campaign.getmCampaignID());
        dbCampaign.setCrcVersion(campaign.getmCRCVersion());
        dbCampaign.setStartDate(getMillisecFromStringDate(campaign.getmStartDate()));
        dbCampaign.setEndDate(getMillisecFromStringDate(campaign.getmEndDate()));
        dbCampaign.setPlayDay(campaign.getmPlayDay());
        dbCampaign.setOverrideTime(campaign.getmOverrideTime());
        dbCampaign.setSequence(campaign.getmSequence());

        return dbCampaign;
    }

    protected static DBmsgBoardCampaign createDBmsgBoardCampaign(MsgBoardCampaign msgBoardCampaign) {
        DBmsgBoardCampaign dBmsgBoardCampaign = new DBmsgBoardCampaign();
        dBmsgBoardCampaign.setId(msgBoardCampaign.getmMsgBoardID());
        dBmsgBoardCampaign.setBottomBkgURL(msgBoardCampaign.getmBottomBkgURL());
        dBmsgBoardCampaign.setRightBkgURL(msgBoardCampaign.getmRightBkgURL());
        dBmsgBoardCampaign.setCrcVersion(msgBoardCampaign.getmCRCVersion());
        dBmsgBoardCampaign.setPlayDay(msgBoardCampaign.getmPlayDay());
        dBmsgBoardCampaign.setTextColor(msgBoardCampaign.getmTextColor());
        dBmsgBoardCampaign.setStartDate(getMillisecFromStringDate(msgBoardCampaign.getmStartDate()));
        dBmsgBoardCampaign.setEndDate(getMillisecFromStringDate(msgBoardCampaign.getmEndDate()));

        return dBmsgBoardCampaign;
    }

    protected static Campaign createCampaign(DBCampaign dbCampaign) {
        Campaign campaign = new Campaign();
        campaign.setmCampaignID(dbCampaign.getId());
        campaign.setmCRCVersion(dbCampaign.getCrcVersion());
        campaign.setmStartDate(simpleDateFormat.format(new Date(dbCampaign.getStartDate())));
        campaign.setmEndDate(simpleDateFormat.format(new Date(dbCampaign.getEndDate())));
        campaign.setmOverrideTime(dbCampaign.getOverrideTime());
        campaign.setmSequence(dbCampaign.getSequence());
        campaign.setmPlayDay(dbCampaign.getPlayDay());
        campaign.setmAssets(createAssetsList(dbCampaign.getAssets()));

        return campaign;
    }

    protected static MsgBoardCampaign createMsgBoardCampaign(DBmsgBoardCampaign dBmsgBoardCampaign) {
        MsgBoardCampaign msgBoardCampaign = new MsgBoardCampaign();
        msgBoardCampaign.setmBottomBkgURL(dBmsgBoardCampaign.getBottomBkgURL());
        msgBoardCampaign.setmCRCVersion(dBmsgBoardCampaign.getCrcVersion());
        msgBoardCampaign.setmMsgBoardID(dBmsgBoardCampaign.getId());
        msgBoardCampaign.setmTextColor(dBmsgBoardCampaign.getTextColor());
        msgBoardCampaign.setmPlayDay(dBmsgBoardCampaign.getPlayDay());
        msgBoardCampaign.setmRightBkgURL(dBmsgBoardCampaign.getRightBkgURL());
        msgBoardCampaign.setmStartDate(simpleDateFormat.format(new Date(dBmsgBoardCampaign.getStartDate())));
        msgBoardCampaign.setmEndDate(simpleDateFormat.format(new Date(dBmsgBoardCampaign.getEndDate())));
        msgBoardCampaign.setmMessages(createMessagesList(dBmsgBoardCampaign.getMessages()));

        return msgBoardCampaign;
    }

    protected static List<DBAsset> createDBAssetsList(List<Asset> assetList, Long campaignId) {
        ArrayList<DBAsset> dbAssets = new ArrayList<>(assetList.size());

        DBAsset a;
        for (Asset asset : assetList) {
            a = createDBAsset(asset);
            a.setCampaignId(campaignId);
            dbAssets.add(a);
        }

        return dbAssets;
    }

    protected static List<DBMessage> createDBMessageList(List<Message> messageList, Long msgBoardID) {
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
        dbAsset.setSequence(asset.getmSequence());
        dbAsset.setDuration(asset.getmDuration());
        dbAsset.setName(asset.getmName());
        dbAsset.setUrl(asset.getmURL());

        return dbAsset;
    }

    private static Asset createAsset(DBAsset dbAsset) {
        Asset asset = new Asset();
        asset.setmSequence(dbAsset.getSequence());
        asset.setmDuration(dbAsset.getDuration());
        asset.setmName(dbAsset.getName());
        asset.setmURL(dbAsset.getUrl());

        return asset;
    }



    private static Message createMessage(DBMessage dbMessage) {
        Message message = new Message();
        message.setmPosition(dbMessage.getPosition());
        message.setmSequenceL(dbMessage.getSequence());
        message.setmText(dbMessage.getText());

        return message;
    }

    private static DBMessage createDBMessage(Message message) {
        DBMessage dbMessage = new DBMessage();
        dbMessage.setPosition(message.getmPosition());
        dbMessage.setSequence(message.getmSequenceL());
        dbMessage.setText(message.getmText());

        return dbMessage;
    }

    public static long getMillisecFromStringDate(String date) {
        return simpleDateFormat.parse(date, new ParsePosition(0)).getTime();
    }
}
