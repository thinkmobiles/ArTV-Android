package com.artv.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.artv.android.core.model.Campaign;
import com.artv.android.database.gen.DBAsset;
import com.artv.android.database.gen.DBAssetDao;
import com.artv.android.database.gen.DBCampaign;
import com.artv.android.database.gen.DBCampaignDao;
import com.artv.android.database.gen.DaoMaster;
import com.artv.android.database.gen.DaoSession;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by Misha on 7/16/2015.
 */
public class DBManager implements AsyncOperationListener {
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
     * @param context The Android {@link android.content.Context}.
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
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
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

    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(DBAsset.class);    // clear all elements from a table
            asyncSession.deleteAll(DBCampaign.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean addNewOrUpdateCampaigns(List<Campaign> campaigns) {
        try {
            if (campaigns != null && campaigns.size() > 0) {
                openWritableDb();

                //firstly add campaigns
                List<DBCampaign> dbCampaigns = Transformer.createDBCompaignList(campaigns);
                DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
                dbCampaignDao.insertInTx(dbCampaigns);

                //then add assets
                LinkedList<DBAsset> dbAssets = new LinkedList<>();
                for (Campaign campaign : campaigns)
                    dbAssets.addAll(Transformer.createDBAssetsLis(campaign.getmAssets(),campaign.getmCampaignID()));

                DBAssetDao dbAssetDao = daoSession.getDBAssetDao();
                dbAssetDao.insertInTx(dbAssets);

                daoSession.clear();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized List<Campaign> getAllCampaigns() {
        List<Campaign> campaigns = null;
        try {
            openReadableDb();
            DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
            campaigns = Transformer.createCompaignList(dbCampaignDao.loadAll());

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campaigns;
    }

    public synchronized List<Campaign> getCapaignsFromDate(long startTimeMillis) {
        List<Campaign> campaigns = null;
        try {
            openReadableDb();
            DBCampaignDao dbCampaignDao = daoSession.getDBCampaignDao();
            campaigns = Transformer.createCompaignList(dbCampaignDao.queryBuilder()
                    .where(DBCampaignDao.Properties.StartDate.ge(startTimeMillis)).list());

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campaigns;
    }

    public synchronized Campaign getCampaignById(Long id) {
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
