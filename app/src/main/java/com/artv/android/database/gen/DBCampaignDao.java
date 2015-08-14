package com.artv.android.database.gen;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

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
        public final static Property CrcVersion = new Property(1, String.class, "crcVersion", false, "CRC_VERSION");
        public final static Property StartDate = new Property(2, String.class, "startDate", false, "START_DATE");
        public final static Property EndDate = new Property(3, String.class, "endDate", false, "END_DATE");
        public final static Property Sequence = new Property(4, Integer.class, "sequence", false, "SEQUENCE");
        public final static Property PlayDay = new Property(5, String.class, "playDay", false, "PLAY_DAY");
        public final static Property OverrideTime = new Property(6, String.class, "overrideTime", false, "OVERRIDE_TIME");
        public final static Property CampaignsAssetsId = new Property(7, long.class, "campaignsAssetsId", false, "CAMPAIGNS_ASSETS_ID");
    };

    private Query<DBCampaign> dBCampaignsAssets_CampaignsQuery;

    public DBCampaignDao(DaoConfig config) {
        super(config);
    }
    
    public DBCampaignDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DBCAMPAIGN' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'CRC_VERSION' TEXT," + // 1: crcVersion
                "'START_DATE' TEXT," + // 2: startDate
                "'END_DATE' TEXT," + // 3: endDate
                "'SEQUENCE' INTEGER," + // 4: sequence
                "'PLAY_DAY' TEXT," + // 5: playDay
                "'OVERRIDE_TIME' TEXT," + // 6: overrideTime
                "'CAMPAIGNS_ASSETS_ID' INTEGER NOT NULL );"); // 7: campaignsAssetsId
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
 
        String crcVersion = entity.getCrcVersion();
        if (crcVersion != null) {
            stmt.bindString(2, crcVersion);
        }
 
        String startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindString(3, startDate);
        }
 
        String endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindString(4, endDate);
        }
 
        Integer sequence = entity.getSequence();
        if (sequence != null) {
            stmt.bindLong(5, sequence);
        }
 
        String playDay = entity.getPlayDay();
        if (playDay != null) {
            stmt.bindString(6, playDay);
        }
 
        String overrideTime = entity.getOverrideTime();
        if (overrideTime != null) {
            stmt.bindString(7, overrideTime);
        }
        stmt.bindLong(8, entity.getCampaignsAssetsId());
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
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // crcVersion
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // startDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // endDate
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // sequence
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // playDay
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // overrideTime
            cursor.getLong(offset + 7) // campaignsAssetsId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBCampaign entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCrcVersion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setStartDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEndDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSequence(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setPlayDay(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOverrideTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCampaignsAssetsId(cursor.getLong(offset + 7));
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
    
    /** Internal query to resolve the "campaigns" to-many relationship of DBCampaignsAssets. */
    public List<DBCampaign> _queryDBCampaignsAssets_Campaigns(long campaignsAssetsId) {
        synchronized (this) {
            if (dBCampaignsAssets_CampaignsQuery == null) {
                QueryBuilder<DBCampaign> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CampaignsAssetsId.eq(null));
                dBCampaignsAssets_CampaignsQuery = queryBuilder.build();
            }
        }
        Query<DBCampaign> query = dBCampaignsAssets_CampaignsQuery.forCurrentThread();
        query.setParameter(0, campaignsAssetsId);
        return query.list();
    }

}
