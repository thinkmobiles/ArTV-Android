package com.artv.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.gen.DBAsset;
import com.artv.android.database.gen.DBAssetDao;
import com.artv.android.database.gen.DBCampaign;
import com.artv.android.database.gen.DBCampaignDao;
import com.artv.android.database.gen.DBCampaignsAssets;
import com.artv.android.database.gen.DBCampaignsAssetsDao;
import com.artv.android.database.gen.DBMessage;
import com.artv.android.database.gen.DBMessageDao;
import com.artv.android.database.gen.DBmsgBoardCampaign;
import com.artv.android.database.gen.DBmsgBoardCampaignDao;
import com.artv.android.database.gen.DaoMaster;
import com.artv.android.database.gen.DaoSession;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by ZOG on 8/17/2015.
 */
public final class DbManager implements DbWorker {

    private static DbManager instance;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private Transformer mTransformer;

    private DbManager(final Context _context) {
        mHelper = new DaoMaster.DevOpenHelper(_context, "artv-database", null);
        mTransformer = new Transformer();
    }


    public static DbManager getInstance(final Context _context) {
        if (instance == null) {
            instance = new DbManager(_context);
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
            deleteRelatedCampaignsAssets(daoSession, _campaign);
            writeCampaignsAssetsRelation(daoSession, _campaign);
            return campaignDao.insertOrReplace(dbCampaign);
        } finally {
            daoSession.clear();
        }
    }

    private final void deleteRelatedCampaignsAssets(final DaoSession _daoSession,
                                                    final Campaign _campaign) {
        final DBCampaignsAssetsDao campaignsAssetsDao = _daoSession.getDBCampaignsAssetsDao();
        final List<DBCampaignsAssets> dbCampaignsAssetses = getDBRelationsForCampaign(daoSession, _campaign);

        for (final DBCampaignsAssets dbCampaignsAssets : dbCampaignsAssetses) {
            campaignsAssetsDao.delete(dbCampaignsAssets);
        }
    }

    private final void writeCampaignsAssetsRelation(final DaoSession _daoSession, final Campaign _campaign) {
        final List<DBCampaignsAssets> dbCampaignsAssetsList = new ArrayList<>();

        for (final Asset asset : _campaign.assets) {
            final DBCampaignsAssets dbCampaignsAssets = new DBCampaignsAssets();
            dbCampaignsAssets.setId(generateId(_campaign.campaignId, asset.getAssetId()));
            Log.d("DB_Relation", String.format("C_id: %d, A_id: %d, Gen_id: %d",
                    _campaign.campaignId, asset.getAssetId(), dbCampaignsAssets.getId()));
            dbCampaignsAssets.setCampaignId(_campaign.campaignId);
            dbCampaignsAssets.setAssetId(asset.getAssetId());
            dbCampaignsAssetsList.add(dbCampaignsAssets);
        }

        final DBCampaignsAssetsDao dbCampaignsAssetsDao = _daoSession.getDBCampaignsAssetsDao();
        dbCampaignsAssetsDao.insertOrReplaceInTx(dbCampaignsAssetsList);
    }

    protected final long generateId(final Integer... ints) {
        if (ints.length < 2) throw new RuntimeException("Must pass minimum two numbers");
        int id = ints[0].hashCode();
        for (int i = 1; i < ints.length; i++) {
            id = 31 * id + ints[i].hashCode();
        }

        return id;
    }

    @Override
    public final boolean contains(final Campaign _campaign) {
        try {
            openReadableDb();
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
            final List<Campaign> campaigns = mTransformer.createCampaignList(dbCampaigns);
            for (final Campaign campaign : campaigns) campaign.assets = getAssets(campaign);
            return campaigns;
        } finally {
            daoSession.clear();
        }
    }

    protected final List<Asset> getAssets(final Campaign _campaign) {
        try {
            openReadableDb();
            final List<DBCampaignsAssets> dbCampaignsAssetses = getDBRelationsForCampaign(daoSession, _campaign);

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

    private final List<DBCampaignsAssets> getDBRelationsForCampaign(final DaoSession _daoSession,
                                                                    final Campaign _campaign) {
        final DBCampaignsAssetsDao campaignsAssetsDao = _daoSession.getDBCampaignsAssetsDao();
        final List<DBCampaignsAssets> dbCampaignsAssetses = campaignsAssetsDao
                .queryBuilder()
                .where(DBCampaignsAssetsDao.Properties.CampaignId.eq(_campaign.campaignId))
                .list();
        return dbCampaignsAssetses;
    }

    @Override
    public final Campaign getCampaignById(final int _campaignId) {
        try {
            openReadableDb();
            final DBCampaignDao campaignDao = daoSession.getDBCampaignDao();
            final DBCampaign dbCampaign = campaignDao.load((long) _campaignId);
            if (dbCampaign == null) throw new RuntimeException("Bad campaignId");
            final Campaign campaign = mTransformer.createCampaign(dbCampaign);
            campaign.assets = getAssets(campaign);
            return campaign;
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final long write(final MsgBoardCampaign _msgBoardCampaign) {
        if (_msgBoardCampaign == null) return -1;
        try {
            openWritableDb();
            final DBmsgBoardCampaign dBmsgBoardCampaign = mTransformer.createDBmsgBoardCampaign(_msgBoardCampaign);

            final DBmsgBoardCampaignDao msgBoardCampaignDao = daoSession.getDBmsgBoardCampaignDao();
            final DBMessageDao messageDao = daoSession.getDBMessageDao();

            msgBoardCampaignDao.deleteAll();
            messageDao.deleteAll();

            messageDao.insertInTx(mTransformer.createDBMessageList(_msgBoardCampaign.messages));
            return msgBoardCampaignDao.insertOrReplace(dBmsgBoardCampaign);
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public final MsgBoardCampaign getMsgBoardCampaign() {
        try {
            openReadableDb();
            final DBmsgBoardCampaignDao dBmsgBoardCampaignDao = daoSession.getDBmsgBoardCampaignDao();
            final DBMessageDao messageDao = daoSession.getDBMessageDao();

            final List msgs = dBmsgBoardCampaignDao.loadAll();
            if (msgs.isEmpty()) return null;

            final MsgBoardCampaign msg = mTransformer.createMsgBoardCampaign(dBmsgBoardCampaignDao.loadAll().get(0));
            msg.messages = mTransformer.createMessagesList(messageDao.loadAll());
            return msg;
        } finally {
            daoSession.clear();
        }
    }

    protected final List<Message> getAllMessages() {
        try {
            openReadableDb();
            final DBMessageDao messageDao = daoSession.getDBMessageDao();
            return mTransformer.createMessagesList(messageDao.loadAll());
        } finally {
            daoSession.clear();
        }
    }

}
