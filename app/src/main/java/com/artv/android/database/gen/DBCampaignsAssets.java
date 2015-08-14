package com.artv.android.database.gen;

import java.util.List;
import com.artv.android.database.gen.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table DBCAMPAIGNS_ASSETS.
 */
public class DBCampaignsAssets {

    private Long id;
    private Integer campaignId;
    private Integer assetId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DBCampaignsAssetsDao myDao;

    private List<DBCampaign> campaigns;
    private List<DBAsset> assets;

    public DBCampaignsAssets() {
    }

    public DBCampaignsAssets(Long id) {
        this.id = id;
    }

    public DBCampaignsAssets(Long id, Integer campaignId, Integer assetId) {
        this.id = id;
        this.campaignId = campaignId;
        this.assetId = assetId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDBCampaignsAssetsDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<DBCampaign> getCampaigns() {
        if (campaigns == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DBCampaignDao targetDao = daoSession.getDBCampaignDao();
            List<DBCampaign> campaignsNew = targetDao._queryDBCampaignsAssets_Campaigns(id);
            synchronized (this) {
                if(campaigns == null) {
                    campaigns = campaignsNew;
                }
            }
        }
        return campaigns;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCampaigns() {
        campaigns = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<DBAsset> getAssets() {
        if (assets == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DBAssetDao targetDao = daoSession.getDBAssetDao();
            List<DBAsset> assetsNew = targetDao._queryDBCampaignsAssets_Assets(id);
            synchronized (this) {
                if(assets == null) {
                    assets = assetsNew;
                }
            }
        }
        return assets;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetAssets() {
        assets = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
