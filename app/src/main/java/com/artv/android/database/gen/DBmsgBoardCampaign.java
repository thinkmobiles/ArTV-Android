package com.artv.android.database.gen;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "DBMSG_BOARD_CAMPAIGN".
 */
public class DBmsgBoardCampaign {

    private Long id;
    private String crcVersion;
    private String startDate;
    private String endDate;
    private String playDay;
    private String textColor;
    private String RightBkgURL;
    private String BottomBkgURL;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DBmsgBoardCampaignDao myDao;

    private List<DBMessage> messages;

    public DBmsgBoardCampaign() {
    }

    public DBmsgBoardCampaign(Long id) {
        this.id = id;
    }

    public DBmsgBoardCampaign(Long id, String crcVersion, String startDate, String endDate, String playDay, String textColor, String RightBkgURL, String BottomBkgURL) {
        this.id = id;
        this.crcVersion = crcVersion;
        this.startDate = startDate;
        this.endDate = endDate;
        this.playDay = playDay;
        this.textColor = textColor;
        this.RightBkgURL = RightBkgURL;
        this.BottomBkgURL = BottomBkgURL;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDBmsgBoardCampaignDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrcVersion() {
        return crcVersion;
    }

    public void setCrcVersion(String crcVersion) {
        this.crcVersion = crcVersion;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPlayDay() {
        return playDay;
    }

    public void setPlayDay(String playDay) {
        this.playDay = playDay;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getRightBkgURL() {
        return RightBkgURL;
    }

    public void setRightBkgURL(String RightBkgURL) {
        this.RightBkgURL = RightBkgURL;
    }

    public String getBottomBkgURL() {
        return BottomBkgURL;
    }

    public void setBottomBkgURL(String BottomBkgURL) {
        this.BottomBkgURL = BottomBkgURL;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<DBMessage> getMessages() {
        if (messages == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DBMessageDao targetDao = daoSession.getDBMessageDao();
            List<DBMessage> messagesNew = targetDao._queryDBmsgBoardCampaign_Messages(id);
            synchronized (this) {
                if(messages == null) {
                    messages = messagesNew;
                }
            }
        }
        return messages;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetMessages() {
        messages = null;
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
