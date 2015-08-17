package com.artv.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.gen.DBAsset;
import com.artv.android.database.gen.DBAssetDao;
import com.artv.android.database.gen.DBCampaign;
import com.artv.android.database.gen.DBCampaignDao;
import com.artv.android.database.gen.DBCampaignsAssets;
import com.artv.android.database.gen.DBCampaignsAssetsDao;
import com.artv.android.database.gen.DBMessage;
import com.artv.android.database.gen.DBmsgBoardCampaign;
import com.artv.android.database.gen.DaoMaster;
import com.artv.android.database.gen.DaoSession;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by ZOG on 8/17/2015.
 */
public class DbManager2 implements DbWorker {

    private static DbManager2 instance;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private Transformer2 mTransformer;

    private DbManager2(final Context _context) {
        mHelper = new DaoMaster.DevOpenHelper(_context, "artv-database", null);
        mTransformer = new Transformer2();
    }


    public static DbManager2 getInstance(final Context _context) {
        if (instance == null) {
            instance = new DbManager2(_context);
        }

        return instance;
    }

    /**
     * Query for readable DB
     */
    private void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    public void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(DBAsset.class);    // clear all elements from a table
            asyncSession.deleteAll(DBCampaign.class);
            asyncSession.deleteAll(DBCampaignsAssets.class);
            asyncSession.deleteAll(DBmsgBoardCampaign.class);
            asyncSession.deleteAll(DBMessage.class);
            asyncSession.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Query for writable DB
     */
    private void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    @Override
    public final long write(final Asset _asset) {
        try {
            openWritableDb();
            final DBAsset dbAsset = mTransformer.createDBAsset(_asset);
            final DBAssetDao assetDao = daoSession.getDBAssetDao();
            return assetDao.insertOrReplace(dbAsset);
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final boolean contains(final Asset _asset) {
        try {
            openReadableDb();
            final DBAssetDao assetDao = daoSession.getDBAssetDao();
            final DBAsset dbAsset = assetDao.load((long) _asset.getAssetId());
            return dbAsset != null;
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final List<Asset> getAllAssets() {
        try {
            openReadableDb();
            final DBAssetDao assetDao = daoSession.getDBAssetDao();
            final List<DBAsset> dbAssets = assetDao.loadAll();
            return mTransformer.createAssetsList(dbAssets);
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final long write(final Campaign _campaign) {
        try {
            openWritableDb();
            final DBCampaign dbCampaign = mTransformer.createDBCampaign(_campaign);
            final DBCampaignDao campaignDao = daoSession.getDBCampaignDao();
            writeCampaignsAssetsRelation(daoSession, _campaign);
            return campaignDao.insertOrReplace(dbCampaign);
        } finally {
            daoSession.clear();
        }
    }

    private final void writeCampaignsAssetsRelation(final DaoSession _daoSession, final Campaign _campaign) {
        final List<DBCampaignsAssets> dbCampaignsAssetsList = new ArrayList<>();

        for (final Asset asset : _campaign.assets) {
            final DBCampaignsAssets dbCampaignsAssets = new DBCampaignsAssets();
            dbCampaignsAssets.setId(generateId(_campaign.campaignId, asset.getAssetId()));
            dbCampaignsAssets.setCampaignId(_campaign.campaignId);
            dbCampaignsAssets.setAssetId(asset.getAssetId());
            dbCampaignsAssetsList.add(dbCampaignsAssets);
        }

        final DBCampaignsAssetsDao dbCampaignsAssetsDao = _daoSession.getDBCampaignsAssetsDao();
        dbCampaignsAssetsDao.insertOrReplaceInTx(dbCampaignsAssetsList);
    }

    protected final long generateId(final int... ints) {
        if (ints.length < 2) throw new RuntimeException("Must pass minimum two numbers");
        int id = 0;
        for (int i = 0; i < ints.length - 1; i++) {
            id = id + (ints[i] << 2 + ints[i + 1] << 2);
        }

        return id;
    }

    @Override
    public final boolean contains(final Campaign _campaign) {
        try {
            final DBCampaignDao campaignDao = daoSession.getDBCampaignDao();
            final DBCampaign dbCampaign = campaignDao.load((long) _campaign.campaignId);
            return dbCampaign != null;
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final List<Campaign> getAllCampaigns() {
        try {
            openReadableDb();
            final DBCampaignDao campaignDao = daoSession.getDBCampaignDao();
            final List<DBCampaign> dbCampaigns = campaignDao.loadAll();
            return mTransformer.createCampaignList(dbCampaigns);
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final List<Asset> getAssets(final Campaign _campaign) {
        try {
            final DBCampaignsAssetsDao campaignsAssetsDao = daoSession.getDBCampaignsAssetsDao();
            final List<DBCampaignsAssets> dbCampaignsAssetses = campaignsAssetsDao
                    .queryBuilder()
                    .where(DBCampaignsAssetsDao.Properties.CampaignId.eq(_campaign.campaignId))
                    .list();

            final DBAssetDao assetDao = daoSession.getDBAssetDao();
            final List<DBAsset> dbAssets = new ArrayList<>();

            for (final DBCampaignsAssets dbCampaignsAssets : dbCampaignsAssetses) {
                final DBAsset dbAsset = assetDao.load(Long.valueOf(dbCampaignsAssets.getAssetId()));
                dbAssets.add(dbAsset);
            }

            return mTransformer.createAssetsList(dbAssets);
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final Campaign getCampaignById(final int _campaignId) {
        return null;
    }

    @Override
    public final long write(final MsgBoardCampaign _msgBoardCampaign) {
        return -1;
    }

    @Override
    public final MsgBoardCampaign getMsgBoardCampaign() {
        return null;
    }

}
