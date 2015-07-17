package com.artv.android.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class ArtvDaoGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/")
            .replaceFirst("greendao","app");

    private static final String OUT_DIR = PROJECT_DIR + "/src/main/java/";

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
        Entity campaigns = addCampaign(schema);
        Entity assets = addAssets(schema);

        /* properties */
        Property campaignIdForAssets = assets.addLongProperty("campaignId").notNull().getProperty();

        /* relationships between entities */
        ToMany campaignToAssets = campaigns.addToMany(assets, campaignIdForAssets);
        campaignToAssets.setName("assets"); // one-to-many

    }

    /**
     * Create campaigns Properties
     *
     * @return Campaigns entity
     */
    private static Entity addCampaign(Schema schema) {
        Entity user = schema.addEntity("DBCampaign");
        user.addIdProperty().primaryKey();
        user.addIntProperty("crcVersion");
        user.addStringProperty("startDate");
        user.addStringProperty("endDate");
        user.addIntProperty("sequence");
        user.addStringProperty("playDay");
        user.addStringProperty("overrideTime");
        return user;
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
}
