package com.artv.android.database.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DBMESSAGE.
*/
public class DBMessageDao extends AbstractDao<DBMessage, Void> {

    public static final String TABLENAME = "DBMESSAGE";

    /**
     * Properties of entity DBMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Text = new Property(0, String.class, "text", false, "TEXT");
        public final static Property Position = new Property(1, String.class, "position", false, "POSITION");
        public final static Property Sequence = new Property(2, Integer.class, "sequence", false, "SEQUENCE");
        public final static Property MsgBoardID = new Property(3, long.class, "msgBoardID", false, "MSG_BOARD_ID");
    };

    private Query<DBMessage> dBmsgBoardCampaign_MessagesQuery;

    public DBMessageDao(DaoConfig config) {
        super(config);
    }
    
    public DBMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DBMESSAGE' (" + //
                "'TEXT' TEXT," + // 0: text
                "'POSITION' TEXT," + // 1: position
                "'SEQUENCE' INTEGER," + // 2: sequence
                "'MSG_BOARD_ID' INTEGER NOT NULL );"); // 3: msgBoardID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DBMESSAGE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBMessage entity) {
        stmt.clearBindings();
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(1, text);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(2, position);
        }
 
        Integer sequence = entity.getSequence();
        if (sequence != null) {
            stmt.bindLong(3, sequence);
        }
        stmt.bindLong(4, entity.getMsgBoardID());
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public DBMessage readEntity(Cursor cursor, int offset) {
        DBMessage entity = new DBMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // text
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // position
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // sequence
            cursor.getLong(offset + 3) // msgBoardID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBMessage entity, int offset) {
        entity.setText(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPosition(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSequence(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setMsgBoardID(cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(DBMessage entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(DBMessage entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "messages" to-many relationship of DBmsgBoardCampaign. */
    public List<DBMessage> _queryDBmsgBoardCampaign_Messages(long msgBoardID) {
        synchronized (this) {
            if (dBmsgBoardCampaign_MessagesQuery == null) {
                QueryBuilder<DBMessage> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MsgBoardID.eq(null));
                dBmsgBoardCampaign_MessagesQuery = queryBuilder.build();
            }
        }
        Query<DBMessage> query = dBmsgBoardCampaign_MessagesQuery.forCurrentThread();
        query.setParameter(0, msgBoardID);
        return query.list();
    }

}
