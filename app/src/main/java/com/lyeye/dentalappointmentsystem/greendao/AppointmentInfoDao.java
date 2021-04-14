package com.lyeye.dentalappointmentsystem.greendao;

import java.util.List;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lyeye.dentalappointmentsystem.entity.User;

import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "APPOINTMENT_INFO".
*/
public class AppointmentInfoDao extends AbstractDao<AppointmentInfo, Long> {

    public static final String TABLENAME = "APPOINTMENT_INFO";

    /**
     * Properties of entity AppointmentInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property AmiId = new Property(0, Long.class, "amiId", true, "APPOINTMENT_INFO_ID");
        public final static Property UserId = new Property(1, long.class, "userId", false, "USER_ID");
        public final static Property AffiliatedHospital = new Property(2, String.class, "affiliatedHospital", false, "AFFILIATED_HOSPITAL");
        public final static Property AmiDate = new Property(3, String.class, "amiDate", false, "APPOINTMENT_INFO_DATE");
        public final static Property AmiTime = new Property(4, String.class, "amiTime", false, "APPOINTMENT_INFO_TIME");
        public final static Property AmiSymptoms = new Property(5, String.class, "amiSymptoms", false, "APPOINTMENT_INFO_SYMPTOMS");
        public final static Property CreateAt = new Property(6, java.util.Date.class, "createAt", false, "CREATE_AT");
    }

    private DaoSession daoSession;


    public AppointmentInfoDao(DaoConfig config) {
        super(config);
    }

    public AppointmentInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APPOINTMENT_INFO\" (" + //
                "\"APPOINTMENT_INFO_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: amiId
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: userId
                "\"AFFILIATED_HOSPITAL\" TEXT," + // 2: affiliatedHospital
                "\"APPOINTMENT_INFO_DATE\" TEXT," + // 3: amiDate
                "\"APPOINTMENT_INFO_TIME\" TEXT," + // 4: amiTime
                "\"APPOINTMENT_INFO_SYMPTOMS\" TEXT," + // 5: amiSymptoms
                "\"CREATE_AT\" INTEGER);"); // 6: createAt
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"APPOINTMENT_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AppointmentInfo entity) {
        stmt.clearBindings();

        Long amiId = entity.getAmiId();
        if (amiId != null) {
            stmt.bindLong(1, amiId);
        }
        stmt.bindLong(2, entity.getUserId());

        String affiliatedHospital = entity.getAffiliatedHospital();
        if (affiliatedHospital != null) {
            stmt.bindString(3, affiliatedHospital);
        }

        String amiDate = entity.getAmiDate();
        if (amiDate != null) {
            stmt.bindString(4, amiDate);
        }

        String amiTime = entity.getAmiTime();
        if (amiTime != null) {
            stmt.bindString(5, amiTime);
        }

        String amiSymptoms = entity.getAmiSymptoms();
        if (amiSymptoms != null) {
            stmt.bindString(6, amiSymptoms);
        }

        java.util.Date createAt = entity.getCreateAt();
        if (createAt != null) {
            stmt.bindLong(7, createAt.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AppointmentInfo entity) {
        stmt.clearBindings();

        Long amiId = entity.getAmiId();
        if (amiId != null) {
            stmt.bindLong(1, amiId);
        }
        stmt.bindLong(2, entity.getUserId());

        String affiliatedHospital = entity.getAffiliatedHospital();
        if (affiliatedHospital != null) {
            stmt.bindString(3, affiliatedHospital);
        }

        String amiDate = entity.getAmiDate();
        if (amiDate != null) {
            stmt.bindString(4, amiDate);
        }

        String amiTime = entity.getAmiTime();
        if (amiTime != null) {
            stmt.bindString(5, amiTime);
        }

        String amiSymptoms = entity.getAmiSymptoms();
        if (amiSymptoms != null) {
            stmt.bindString(6, amiSymptoms);
        }

        java.util.Date createAt = entity.getCreateAt();
        if (createAt != null) {
            stmt.bindLong(7, createAt.getTime());
        }
    }

    @Override
    protected final void attachEntity(AppointmentInfo entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public AppointmentInfo readEntity(Cursor cursor, int offset) {
        AppointmentInfo entity = new AppointmentInfo( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // amiId
                cursor.getLong(offset + 1), // userId
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // affiliatedHospital
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // amiDate
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // amiTime
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // amiSymptoms
                cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)) // createAt
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AppointmentInfo entity, int offset) {
        entity.setAmiId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getLong(offset + 1));
        entity.setAffiliatedHospital(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAmiDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAmiTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAmiSymptoms(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreateAt(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
    }
    
    @Override
    protected final Long updateKeyAfterInsert(AppointmentInfo entity, long rowId) {
        entity.setAmiId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AppointmentInfo entity) {
        if(entity != null) {
            return entity.getAmiId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AppointmentInfo entity) {
        return entity.getAmiId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM APPOINTMENT_INFO T");
            builder.append(" LEFT JOIN USER T0 ON T.\"USER_ID\"=T0.\"USER_ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected AppointmentInfo loadCurrentDeep(Cursor cursor, boolean lock) {
        AppointmentInfo entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        User user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        if (user != null) {
            entity.setUser(user);
        }

        return entity;
    }

    public AppointmentInfo loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[]{key.toString()};
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /**
     * Reads all available rows from the given cursor and returns a list of new ImageTO objects.
     */
    public List<AppointmentInfo> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<AppointmentInfo> list = new ArrayList<AppointmentInfo>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    protected List<AppointmentInfo> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public List<AppointmentInfo> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

}
