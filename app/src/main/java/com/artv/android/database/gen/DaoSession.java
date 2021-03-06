package com.artv.android.database.gen;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dBCampaignDaoConfig;
    private final DaoConfig dBAssetDaoConfig;
    private final DaoConfig dBCampaignsAssetsDaoConfig;
    private final DaoConfig dBmsgBoardCampaignDaoConfig;
    private final DaoConfig dBMessageDaoConfig;

    private final DBCampaignDao dBCampaignDao;
    private final DBAssetDao dBAssetDao;
    private final DBCampaignsAssetsDao dBCampaignsAssetsDao;
    private final DBmsgBoardCampaignDao dBmsgBoardCampaignDao;
    private final DBMessageDao dBMessageDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dBCampaignDaoConfig = daoConfigMap.get(DBCampaignDao.class).clone();
        dBCampaignDaoConfig.initIdentityScope(type);

        dBAssetDaoConfig = daoConfigMap.get(DBAssetDao.class).clone();
        dBAssetDaoConfig.initIdentityScope(type);

        dBCampaignsAssetsDaoConfig = daoConfigMap.get(DBCampaignsAssetsDao.class).clone();
        dBCampaignsAssetsDaoConfig.initIdentityScope(type);

        dBmsgBoardCampaignDaoConfig = daoConfigMap.get(DBmsgBoardCampaignDao.class).clone();
        dBmsgBoardCampaignDaoConfig.initIdentityScope(type);

        dBMessageDaoConfig = daoConfigMap.get(DBMessageDao.class).clone();
        dBMessageDaoConfig.initIdentityScope(type);

        dBCampaignDao = new DBCampaignDao(dBCampaignDaoConfig, this);
        dBAssetDao = new DBAssetDao(dBAssetDaoConfig, this);
        dBCampaignsAssetsDao = new DBCampaignsAssetsDao(dBCampaignsAssetsDaoConfig, this);
        dBmsgBoardCampaignDao = new DBmsgBoardCampaignDao(dBmsgBoardCampaignDaoConfig, this);
        dBMessageDao = new DBMessageDao(dBMessageDaoConfig, this);

        registerDao(DBCampaign.class, dBCampaignDao);
        registerDao(DBAsset.class, dBAssetDao);
        registerDao(DBCampaignsAssets.class, dBCampaignsAssetsDao);
        registerDao(DBmsgBoardCampaign.class, dBmsgBoardCampaignDao);
        registerDao(DBMessage.class, dBMessageDao);
    }
    
    public void clear() {
        dBCampaignDaoConfig.getIdentityScope().clear();
        dBAssetDaoConfig.getIdentityScope().clear();
        dBCampaignsAssetsDaoConfig.getIdentityScope().clear();
        dBmsgBoardCampaignDaoConfig.getIdentityScope().clear();
        dBMessageDaoConfig.getIdentityScope().clear();
    }

    public DBCampaignDao getDBCampaignDao() {
        return dBCampaignDao;
    }

    public DBAssetDao getDBAssetDao() {
        return dBAssetDao;
    }

    public DBCampaignsAssetsDao getDBCampaignsAssetsDao() {
        return dBCampaignsAssetsDao;
    }

    public DBmsgBoardCampaignDao getDBmsgBoardCampaignDao() {
        return dBmsgBoardCampaignDao;
    }

    public DBMessageDao getDBMessageDao() {
        return dBMessageDao;
    }

}
