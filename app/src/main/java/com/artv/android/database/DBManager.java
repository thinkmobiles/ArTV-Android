package com.artv.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Paint;

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
import com.artv.android.database.gen.DBMessageDao;
import com.artv.android.database.gen.DBmsgBoardCampaign;
import com.artv.android.database.gen.DBmsgBoardCampaignDao;
import com.artv.android.database.gen.DaoMaster;
import com.artv.android.database.gen.DaoSession;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Misha on 7/16/2015.
 */
public class DBManager implements AsyncOperationListener, DbWorker {
    private static final String TAG = DBManager.class.getSimpleName();

    private static DBManager instance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     *
     * @param context The Android {@link android.content.Context}.
     */
    private DBManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, "artv-database", null);
        completedOperations = new CopyOnWriteArrayList<>();
    }

    /**
     * @param applicationContext The Android {@link android.content.Context}.
     * @return this.instance
     */
    public static DBManager getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new DBManager(applicationContext);
        }

        return instance;
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    /**
     * Query for readable DB
     */
    private void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    private void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
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

    public boolean addNewOrUpdateCampaigns(List<Campaign> campaigns) {
        try {
            if (campaigns != null && campaigns.size() > 0) {
                openWritableDb();

                //firstly add campaigns
                List<DBCampaign> dbCampaigns = Transformer.createDBCampaignList(campaigns);
                DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
                dbCampaignDao.insertOrReplaceInTx(dbCampaigns);

                //then add assets
                LinkedList<DBAsset> dbAssets = new LinkedList<>();
                for (Campaign campaign : campaigns)
                    dbAssets.addAll(Transformer.createDBAssetsList(campaign.assets, campaign.campaignId));

                DBAssetDao dbAssetDao = daoSession.getDBAssetDao();
                dbAssetDao.insertOrReplaceInTx(dbAssets);
                daoSession.clear();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean addNewOrUpdateMsgBoardCampaigns(List<MsgBoardCampaign> msgBoardCampaigns) {
        try {
            if (msgBoardCampaigns != null && msgBoardCampaigns.size() > 0) {
                openWritableDb();

                //firstly add msgBoardCampaigns
                List<DBmsgBoardCampaign> dBmsgBoardCampaigns = Transformer.createDBmsgCampaignList(msgBoardCampaigns);
                DBmsgBoardCampaignDao dBmsgBoardCampaignDao = daoSession.getDBmsgBoardCampaignDao();
                dBmsgBoardCampaignDao.insertOrReplaceInTx(dBmsgBoardCampaigns);

                //then add Messages
                LinkedList<DBMessage> dbMessages = new LinkedList<>();
                for (MsgBoardCampaign msgBoardCampaign : msgBoardCampaigns)
                    dbMessages.addAll(Transformer.createDBMessageList(msgBoardCampaign.messages,
                            msgBoardCampaign.msgBoardId));

                DBMessageDao messageDao = daoSession.getDBMessageDao();
                messageDao.insertOrReplaceInTx(dbMessages);
                daoSession.clear();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean contains(Campaign _campaign) {
        try {
            openReadableDb();
            final DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
//            List<DBCampaign> resCampaigns = dbCampaignDao.queryBuilder()
//                    .where(DBCampaignDao.Properties.CampaignId.eq(_campaign.campaignId))
//                    .build().list();
            final DBCampaign campaign = dbCampaignDao.load((long) _campaign.campaignId);
            daoSession.clear();

            return campaign != null;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public final boolean contains(final Asset _asset) {
        try {
            openReadableDb();
            DBAssetDao assetDao = daoSession.getDBAssetDao();
//            List<DBAsset> resAssets = assetDao.queryBuilder()
//                    .where(_asset.url != null ? DBAssetDao.Properties.Url.eq(_asset.url) :
//                                    DBAssetDao.Properties.Url.isNull(),
//                            _asset.name != null ? DBAssetDao.Properties.Name.eq(_asset.name) :
//                                    DBAssetDao.Properties.Name.isNull(),
//                            DBAssetDao.Properties.Sequence.eq(_asset.sequence),
//                            DBAssetDao.Properties.Duration.eq(_asset.duration))
//                    .build().list();
            final DBAsset dbAsset = assetDao.load((long) _asset.getAssetId());
            daoSession.clear();
//            return resAssets.size() > 0;
            return dbAsset != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Write campaign into db, and relation between campaign and assets. Do not write assets.
     * @param _campaign
     */
    @Override
    public final void write(final Campaign _campaign) {
        if(_campaign == null) throw new NullPointerException("Campaign object or campaign id is zero");
        try {
            openWritableDb();

            final DBCampaign dbCampaign = Transformer.createDBCampaign(_campaign);
            final DBCampaignDao dao = daoSession.getDBCampaignDao();
            dao.insertOrReplace(dbCampaign);

            final List<DBCampaignsAssets> dbCampaignsAssetsList = new ArrayList<>();
            for (final Asset asset : _campaign.assets) {
                final DBCampaignsAssets dbCampaignsAssets = new DBCampaignsAssets();
                dbCampaignsAssets.setCampaignId(_campaign.campaignId);
                dbCampaignsAssets.setAssetId(asset.getAssetId());
                dbCampaignsAssetsList.add(dbCampaignsAssets);
            }

            final DBCampaignsAssetsDao dbCampaignsAssetsDao = daoSession.getDBCampaignsAssetsDao();
            dbCampaignsAssetsDao.insertOrReplaceInTx(dbCampaignsAssetsList);

//            List<DBAsset> dbAssets = Transformer.createDBAssetsList(_campaign.assets, _campaign.campaignId);
//            DBAssetDao dbAssetDao = daoSession.getDBAssetDao();
//            dbAssetDao.insertOrReplaceInTx(dbAssets);

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void write(final Asset _asset) {
        if(_asset == null) return;
        try {
            openWritableDb();

            DBAsset dbAsset = Transformer.createDBAsset(_asset);
            DBAssetDao dao = daoSession.getDBAssetDao();
            dao.insertOrReplace(dbAsset);

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        List<Campaign> campaigns = new LinkedList<>();
        try {
            openReadableDb();
            DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
            campaigns = Transformer.createCampaignList(dbCampaignDao.loadAll());

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campaigns;
    }

    @Override
    public List<Asset> getAllAssets() {
        List<Asset> assets = new LinkedList<>();
        try {
            openReadableDb();
            DBAssetDao dao = daoSession.getDBAssetDao();
            assets = Transformer.createAssetsList(dao.loadAll());
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public final List<Asset> getAssets(final Campaign _campaign) {
        if(_campaign == null) return null;
        final List<Asset> resAssets = new LinkedList<>();
        try {
            openReadableDb();
//            final DBAssetDao dao = daoSession.getDBAssetDao();
//            List<DBAsset> resDBAssets = dao.queryBuilder()
//                    .where(DBAssetDao.Properties.CampaignsAssetsId.eq(_campaign.campaignId))
//                    .build().list();
//            daoSession.clear();
//            return Transformer.createAssetsList(resDBAssets);

//            final DBAssetDao assetDao = daoSession.getDBAssetDao();
//            final List<Asset> assets = null;
//            final QueryBuilder<DBAsset> qb = assetDao.queryBuilder()
//                    .join(DBCampaignsAssets.class, DBCampaignsAssetsDao.Properties.CampaignId)
//                    .where(DBCampaignsAssetsDao.Properties.CampaignId.eq(_campaign.campaignId))
//                    .li


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resAssets;
    }

    @Override
    public Campaign getCampaignById(int _campaignId) {
        Campaign campaign = null;
        try {
            openReadableDb();
            DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
            campaign = Transformer.createCampaign(dbCampaignDao.load((long) _campaignId));
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return campaign;
    }

    @Override
    public void write(MsgBoardCampaign _msgBoardCampaign) {
        try {
            openWritableDb();

            DBmsgBoardCampaignDao dBmsgBoardCampaignDao = daoSession.getDBmsgBoardCampaignDao();
            DBMessageDao messageDao = daoSession.getDBMessageDao();
            dBmsgBoardCampaignDao.deleteAll();
            messageDao.deleteAll();

            if(_msgBoardCampaign != null) {
                //firstly add msgBoardCampaign
//                DBmsgBoardCampaignDao dBmsgBoardCampaignDao = daoSession.getDBmsgBoardCampaignDao();
                DBmsgBoardCampaign dBmsgBoardCampaign = Transformer.createDBmsgBoardCampaign(_msgBoardCampaign);
                dBmsgBoardCampaignDao.insertOrReplace(dBmsgBoardCampaign);

                //then add Messages
//                DBMessageDao messageDao = daoSession.getDBMessageDao();
                LinkedList<DBMessage> dbMessages = new LinkedList<>();
                dbMessages.addAll(Transformer.createDBMessageList(_msgBoardCampaign.messages,
                        _msgBoardCampaign.msgBoardId));
                messageDao.insertOrReplaceInTx(dbMessages);
            }

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MsgBoardCampaign getMsgBoardCampaign() {
        MsgBoardCampaign msgBoardCampaign = null;
        try {
            openReadableDb();
            DBmsgBoardCampaignDao dao = daoSession.getDBmsgBoardCampaignDao();
            List<MsgBoardCampaign> msgBoardCampaigns = Transformer.createMsgBoardCampaignList(dao.loadAll());
            if(msgBoardCampaigns.size() != 0) msgBoardCampaign = msgBoardCampaigns.get(0);

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msgBoardCampaign;
    }

    public List<MsgBoardCampaign> getAllMsgBoardCampaigns() {
        List<MsgBoardCampaign> msgBoardCampaigns = new LinkedList<>();
        try {
            openReadableDb();
            DBmsgBoardCampaignDao dao = daoSession.getDBmsgBoardCampaignDao();
            msgBoardCampaigns = Transformer.createMsgBoardCampaignList(dao.loadAll());

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgBoardCampaigns;
    }

    public List<Campaign> getCampaignsFromDate(long startTimeMillis) {
        List<Campaign> campaigns = new LinkedList<>();
        try {
            openReadableDb();
            DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
            campaigns = Transformer.createCampaignList(dbCampaignDao.queryBuilder()
                    .where(DBCampaignDao.Properties.StartDate.ge(startTimeMillis)).list());

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campaigns;
    }

    public List<Campaign> getCampaignsFromDate(String startTime) {
        return getCampaignsFromDate(Transformer.getMillisecFromStringDate(startTime));
    }

    public List<MsgBoardCampaign> getMsgBoardCampaignsFromDate(long startTimeMillis) {
        List<MsgBoardCampaign> msgBoardCampaigns = new LinkedList<>();
        try {
            openReadableDb();
            DBmsgBoardCampaignDao dao = daoSession.getDBmsgBoardCampaignDao();
            msgBoardCampaigns = Transformer.createMsgBoardCampaignList(dao.queryBuilder()
                    .where(DBCampaignDao.Properties.StartDate.ge(startTimeMillis)).list());

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgBoardCampaigns;
    }

    public List<MsgBoardCampaign> getMsgBoardCampaignsFromDate(String startTime) {
        return getMsgBoardCampaignsFromDate(Transformer.getMillisecFromStringDate(startTime));
    }

    public Campaign getCampaignById(Long id) {
        Campaign campaign = null;
        try {
            openReadableDb();
            DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
            campaign = Transformer.createCampaign(dbCampaignDao.load(id));
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return campaign;
    }


}
