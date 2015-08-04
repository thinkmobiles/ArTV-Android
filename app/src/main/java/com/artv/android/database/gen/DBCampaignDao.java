package com.artv.android.database.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.artv.android.database.gen.DBCampaign;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DBCAMPAIGN.
*/
public class DBCampaignDao extends AbstractDao<DBCampaign, Long> {

    public static final String TABLENAME = "DBCAMPAIGN";

    /**
     * Properties of entity DBCampaign.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CampaignId = new Property(1, Integer.class, "campaignId", false, "CAMPAIGN_ID");
        public final static Property CrcVersion = new Property(2, String.class, "crcVersion", false, "CRC_VERSION");
        public final static Property StartDate = new Property(3, String.class, "startDate", false, "START_DATE");
        public final static Property EndDate = new Property(4, String.class, "endDate", false, "END_DATE");
        public final static Property Sequence = new Property(5, Integer.class, "sequence", false, "SEQUENCE");
        public final static Property PlayDay = new Property(6, String.class, "playDay", false, "PLAY_DAY");
        public final static Property OverrideTime = new Property(7, String.class, "overrideTime", false, "OVERRIDE_TIME");
    };

    private DaoSession daoSession;


    public DBCampaignDao(DaoConfig config) {
        super(config);
    }
    
    public DBCampaignDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DBCAMPAIGN' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'CAMPAIGN_ID' INTEGER," + // 1: campaignId
                "'CRC_VERSION' TEXT," + // 2: crcVersion
                "'START_DATE' TEXT," + // 3: startDate
                "'END_DATE' TEXT," + // 4: endDate
                "'SEQUENCE' INTEGER," + // 5: sequence
                "'PLAY_DAY' TEXT," + // 6: playDay
                "'OVERRIDE_TIME' TEXT);"); // 7: overrideTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DBCAMPAIGN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBCampaign entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer campaignId = entity.getCampaignId();
        if (campaignId != null) {
            stmt.bindLong(2, campaignId);
        }
 
        String crcVersion = entity.getCrcVersion();
        if (crcVersion != null) {
            stmt.bindString(3, crcVersion);
        }
 
        String startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindString(4, startDate);
        }
 
        String endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindString(5, endDate);
        }
 
        Integer sequence = entity.getSequence();
        if (sequence != null) {
            stmt.bindLong(6, sequence);
        }
 
        String playDay = entity.getPlayDay();
        if (playDay != null) {
            stmt.bindString(7, playDay);
        }
 
        String overrideTime = entity.getOverrideTime();
        if (overrideTime != null) {
            stmt.bindString(8, overrideTime);
        }
    }

    @Override
    protected void attachEntity(DBCampaign entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DBCampaign readEntity(Cursor cursor, int offset) {
        DBCampaign entity = new DBCampaign( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // campaignId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // crcVersion
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // startDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // endDate
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // sequence
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // playDay
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // overrideTime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBCampaign entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCampaignId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setCrcVersion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStartDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEndDate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSequence(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setPlayDay(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOverrideTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DBCampaign entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(DBCampaign entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}