package com.artv.android.database;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.gen.DBAsset;
import com.artv.android.database.gen.DBCampaign;
import com.artv.android.database.gen.DBMessage;
import com.artv.android.database.gen.DBmsgBoardCampaign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 8/17/2015.
 */
public final class Transformer {

    protected final DBAsset createDBAsset(final Asset _asset) {
        final DBAsset dbAsset = new DBAsset();
        dbAsset.setId((long) _asset.getAssetId());
        dbAsset.setSequence(_asset.sequence);
        dbAsset.setDuration(_asset.duration);
        dbAsset.setName(_asset.name);
        dbAsset.setUrl(_asset.url);
        return dbAsset;
    }

    private final Asset createAsset(final DBAsset _dbAsset) {
        final Asset asset = new Asset();
        asset.sequence = _dbAsset.getSequence();
        asset.duration = _dbAsset.getDuration();
        asset.name = _dbAsset.getName();
        asset.url = _dbAsset.getUrl();
        return asset;
    }

    protected final List<Asset> createAssetsList(final List<DBAsset> _dbAssets) {
        final List<Asset> assets = new ArrayList<>(_dbAssets.size());
        for (final DBAsset dbAsset : _dbAssets) assets.add(createAsset(dbAsset));
        return assets;
    }

    protected final DBCampaign createDBCampaign(final Campaign _campaign) {
        final DBCampaign dbCampaign = new DBCampaign();
        dbCampaign.setId((long) _campaign.campaignId);
        dbCampaign.setCrcVersion(_campaign.crcVersion);
        dbCampaign.setStartDate(_campaign.startDate);
        dbCampaign.setEndDate(_campaign.endDate);
        dbCampaign.setPlayDay(_campaign.playDay);
        dbCampaign.setOverrideTime(_campaign.overrideTime);
        dbCampaign.setSequence(_campaign.sequence);
        return dbCampaign;
    }

    protected final Campaign createCampaign(final DBCampaign _dbCampaign) {
        final Campaign campaign = new Campaign();
        campaign.campaignId = _dbCampaign.getId().intValue();
        campaign.crcVersion = _dbCampaign.getCrcVersion();
        campaign.startDate = _dbCampaign.getStartDate();
        campaign.endDate = _dbCampaign.getEndDate();
        campaign.overrideTime = _dbCampaign.getOverrideTime();
        campaign.sequence = _dbCampaign.getSequence();
        campaign.playDay = _dbCampaign.getPlayDay();

        return campaign;
    }

    protected final List<Campaign> createCampaignList(final List<DBCampaign> _dbCampaigns) {
        final List<Campaign> campaigns = new ArrayList<>(_dbCampaigns.size());
        for (final DBCampaign dbCampaign : _dbCampaigns) campaigns.add(createCampaign(dbCampaign));
        return campaigns;
    }

    protected final DBmsgBoardCampaign createDBmsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        final DBmsgBoardCampaign dBmsgBoardCampaign = new DBmsgBoardCampaign();
        dBmsgBoardCampaign.setId((long) _msgBoardCampaign.msgBoardId);
        dBmsgBoardCampaign.setBottomBkgURL(_msgBoardCampaign.bottomBkgURL);
        dBmsgBoardCampaign.setRightBkgURL(_msgBoardCampaign.rightBkgURL);
        dBmsgBoardCampaign.setCrcVersion(_msgBoardCampaign.crcVersion);
        dBmsgBoardCampaign.setPlayDay(_msgBoardCampaign.playDay);
        dBmsgBoardCampaign.setTextColor(_msgBoardCampaign.textColor);
        dBmsgBoardCampaign.setStartDate(_msgBoardCampaign.startDate);
        dBmsgBoardCampaign.setEndDate(_msgBoardCampaign.endDate);
        return dBmsgBoardCampaign;
    }

    protected final MsgBoardCampaign createMsgBoardCampaign(final DBmsgBoardCampaign _dBmsgBoardCampaign) {
        if (_dBmsgBoardCampaign == null) return null;
        final MsgBoardCampaign msgBoardCampaign = new MsgBoardCampaign();
        msgBoardCampaign.bottomBkgURL = _dBmsgBoardCampaign.getBottomBkgURL();
        msgBoardCampaign.crcVersion = _dBmsgBoardCampaign.getCrcVersion();
        msgBoardCampaign.msgBoardId = _dBmsgBoardCampaign.getId().intValue();
        msgBoardCampaign.textColor = _dBmsgBoardCampaign.getTextColor();
        msgBoardCampaign.playDay = _dBmsgBoardCampaign.getPlayDay();
        msgBoardCampaign.rightBkgURL = _dBmsgBoardCampaign.getRightBkgURL();
        msgBoardCampaign.startDate = _dBmsgBoardCampaign.getStartDate();
        msgBoardCampaign.endDate = _dBmsgBoardCampaign.getEndDate();
        return msgBoardCampaign;
    }

    protected final List<DBMessage> createDBMessageList(final List<Message> _messages) {
        final List<DBMessage> dbMessages = new ArrayList<>(_messages.size());
        for (final Message message : _messages) dbMessages.add(createDBMessage(message));
        return dbMessages;
    }

    protected final List<Message> createMessagesList(final List<DBMessage> _dbMessages) {
        final List<Message> messages = new ArrayList<>(_dbMessages.size());
        for (final DBMessage dbMessage : _dbMessages) messages.add(createMessage(dbMessage));
        return messages;
    }

    private final Message createMessage(final DBMessage _dbMessage) {
       final Message message = new Message();
        message.position = _dbMessage.getPosition();
        message.sequence = _dbMessage.getSequence();
        message.text = _dbMessage.getText();
        return message;
    }

    private final DBMessage createDBMessage(final Message _message) {
        final DBMessage dbMessage = new DBMessage();
        dbMessage.setPosition(_message.position);
        dbMessage.setSequence(_message.sequence);
        dbMessage.setText(_message.text);
        return dbMessage;
    }

}
