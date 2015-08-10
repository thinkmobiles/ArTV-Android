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
import com.artv.android.database.gen.DBMessage;
import com.artv.android.database.gen.DBMessageDao;
import com.artv.android.database.gen.DBmsgBoardCampaign;
import com.artv.android.database.gen.DBmsgBoardCampaignDao;
import com.artv.android.database.gen.DaoMaster;
import com.artv.android.database.gen.DaoSession;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;

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
            DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
            List<DBCampaign> resCampaigns = dbCampaignDao.queryBuilder()
                    .where(DBCampaignDao.Properties.CampaignId.eq(_campaign.campaignId))
                    .build().list();
            daoSession.clear();
            return resCampaigns.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean contains(Asset _asset) {
        try {
            openReadableDb();
            DBAssetDao dao = daoSession.getDBAssetDao();
            List<DBAsset> resAssets = dao.queryBuilder()
                    .where(_asset.url != null ? DBAssetDao.Properties.Url.eq(_asset.url) :
                                    DBAssetDao.Properties.Url.isNull(),
                            _asset.name != null ? DBAssetDao.Properties.Name.eq(_asset.name) :
                                    DBAssetDao.Properties.Name.isNull(),
                            DBAssetDao.Properties.Sequence.eq(_asset.sequence),
                            DBAssetDao.Properties.Duration.eq(_asset.duration))
                    .build().list();
            daoSession.clear();
            return resAssets.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void write(Campaign _campaign) {
        if(_campaign == null) throw new NullPointerException("Campaign object or campaign id is zero");
        try {
            openWritableDb();

            DBCampaign dbCampaign = Transformer.createDBCampaign(_campaign);
            DBCampaignDao dao = daoSession.getDBCampaignDao();
            dao.insertOrReplace(dbCampaign);

            List<DBAsset> dbAssets = Transformer.createDBAssetsList(_campaign.assets, _campaign.campaignId);
            DBAssetDao dbAssetDao = daoSession.getDBAssetDao();
            dbAssetDao.insertOrReplaceInTx(dbAssets);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Asset _asset) {
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
    public List<Asset> getAssets(Campaign _campaign) {
        if(_campaign == null) return null;
        List<Asset> resAssets = new LinkedList<>();
        try {
            openReadableDb();
            DBAssetDao dao = daoSession.getDBAssetDao();
            List<DBAsset> resDBAssets = dao.queryBuilder()
                    .where(DBAssetDao.Properties.CampaignId.eq(_campaign.campaignId))
                    .build().list();
            daoSession.clear();
            return Transformer.createAssetsList(resDBAssets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resAssets;
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


    public MsgBoardCampaign getMsgBoardCampaignById(Long id) {
        MsgBoardCampaign msgBoardCampaign = null;
        try {
            openReadableDb();
            DBmsgBoardCampaignDao dao = daoSession.getDBmsgBoardCampaignDao();
            msgBoardCampaign = Transformer.createMsgBoardCampaign(dao.load(id));
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msgBoardCampaign;
    }
}
