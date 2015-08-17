package com.artv.android.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class ArtvDaoGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");

    private static final String OUT_DIR = PROJECT_DIR + "/app/src/main/java/";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.artv.android.database.gen");

        addTables(schema);

        new DaoGenerator().generateAll(schema, OUT_DIR);
    }

    /**
     * Create tables and the relationships between them
     */
    private static void addTables(Schema schema) {
        /* entities */
        Entity campaign = addCampaign(schema);
        Entity asset = addAsset(schema);
        Entity campaignsAssets = addCampaignsAssets(schema);
        Entity msgBoardCampaign = addMsgBoardCampaign(schema);
        Entity message = addMessage(schema);

        /* properties */
        final Property campaignsAssetsIdForCampaign = campaign.addLongProperty("campaignsAssetsId").notNull().getProperty();
        final Property campaignsAssetsIdForAsset = asset.addLongProperty("campaignsAssetsId").notNull().getProperty();
        final Property msgBoardIDForMessages = message.addLongProperty("msgBoardID").notNull().getProperty();

        /* relationships between entities */
        final ToMany campaignsAssetsToCampaigns = campaignsAssets.addToMany(campaign, campaignsAssetsIdForCampaign);
        campaignsAssetsToCampaigns.setName("campaigns");
        final ToMany campaignsAssetsToAssets = campaignsAssets.addToMany(asset, campaignsAssetsIdForAsset);
        campaignsAssetsToAssets.setName("assets");

        ToMany msgBoardCampaignToMessages = msgBoardCampaign.addToMany(message, msgBoardIDForMessages);
        msgBoardCampaignToMessages.setName("messages");
    }

    /**
     * Create campaigns Properties
     *
     * @return Campaigns entity
     */
    private static Entity addCampaign(Schema schema) {
        Entity campaign = schema.addEntity("DBCampaign");
        campaign.addIdProperty().primaryKey();
        campaign.addStringProperty("crcVersion");
        campaign.addStringProperty("startDate");
        campaign.addStringProperty("endDate");
        campaign.addIntProperty("sequence");
        campaign.addStringProperty("playDay");
        campaign.addStringProperty("overrideTime");
        return campaign;
    }

    /**
     * Create assets Properties
     *
     * @return Assets entity
     */
    private static Entity addAsset(Schema schema) {
        Entity asset = schema.addEntity("DBAsset");
        asset.addIdProperty().primaryKey();
        asset.addStringProperty("name");
        asset.addStringProperty("url");
        asset.addIntProperty("duration");
        asset.addIntProperty("sequence");

        return asset;
    }

    private static Entity addCampaignsAssets(final Schema _schema) {
        Entity campaignsAssets = _schema.addEntity("DBCampaignsAssets");
        campaignsAssets.addIdProperty().primaryKey();
        campaignsAssets.addIntProperty("campaignId");
        campaignsAssets.addIntProperty("assetId");

        return campaignsAssets;
    }

    private static Entity addMsgBoardCampaign(Schema schema) {
        Entity msgBoardCampaign = schema.addEntity("DBmsgBoardCampaign");
        msgBoardCampaign.addIdProperty().primaryKey();
        msgBoardCampaign.addStringProperty("crcVersion");
        msgBoardCampaign.addStringProperty("startDate");
        msgBoardCampaign.addStringProperty("endDate");
        msgBoardCampaign.addStringProperty("playDay");
        msgBoardCampaign.addStringProperty("textColor");
        msgBoardCampaign.addStringProperty("RightBkgURL");
        msgBoardCampaign.addStringProperty("BottomBkgURL");

        return msgBoardCampaign;
    }

    private static Entity addMessage(Schema schema) {
        Entity message = schema.addEntity("DBMessage");
        message.addIdProperty().primaryKey().autoincrement();
        message.addStringProperty("text");
        message.addStringProperty("position");
        message.addIntProperty("sequence");

        return message;
    }
}
