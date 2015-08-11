package com.artv.android.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;
import de.greenrobot.daogenerator.ToOne;

public class ArtvDaoGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");

    private static final String OUT_DIR = PROJECT_DIR + "/src/main/java/";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.artv.android.database.gen");

        addTables(schema);

        new DaoGenerator().generateAll(schema, OUT_DIR.replace("greendao","app"));
    }

    /**
     * Create tables and the relationships between them
     */
    private static void addTables(Schema schema) {
        /* entities */
        Entity campaigns = addCampaign(schema);
        Entity assets = addAssets(schema);
        Entity msgBoardCampaign = addMsgBoardCampaign(schema);
        Entity message = addMessage(schema);

        /* properties */
        Property campaignIdForAssets = assets.addLongProperty("campaignId").notNull().getProperty();
        Property msgBoardIDForMessages = message.addLongProperty("msgBoardID").notNull().getProperty();


        /* relationships between entities */
        ToMany campaignToAssets = campaigns.addToMany(assets, campaignIdForAssets);
        campaignToAssets.setName("assets"); // one-to-many

        ToMany msgBoardCampaignToMessages = msgBoardCampaign.addToMany(message,msgBoardIDForMessages);
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
    private static Entity addAssets(Schema schema) {
        Entity asset = schema.addEntity("DBAsset");
        asset.addIdProperty().primaryKey().autoincrement();
        asset.addStringProperty("name");
        asset.addStringProperty("url");
        asset.addIntProperty("duration");
        asset.addIntProperty("sequence");

        return asset;
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
