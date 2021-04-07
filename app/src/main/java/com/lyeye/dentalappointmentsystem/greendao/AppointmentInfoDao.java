package com.lyeye.dentalappointmentsystem.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

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
        public final static Property UserId = new Property(1, long.class, "userId", false, "USER_NAME");
        public final static Property AffiliatedHospital = new Property(2, String.class, "affiliatedHospital", false, "AFFLILIATED_HOSPITAL");
        public final static Property AmiDate = new Property(3, String.class, "amiDate", false, "APPOINTMENT_INFO_DATE");
        public final static Property AmiTime = new Property(4, String.class, "amiTime", false, "APPOINTMENT_INFO_TIME");
        public final static Property AmiSymptoms = new Property(5, String.class, "amiSymptoms", false, "APPOINTMENT_INFO_SYMPTOMS");
        public final static Property CreateAt = new Property(6, java.util.Date.class, "createAt", false, "CREATE_AT");
    }


    public AppointmentInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AppointmentInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APPOINTMENT_INFO\" (" + //
                "\"APPOINTMENT_INFO_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: amiId
                "\"USER_NAME\" INTEGER NOT NULL ," + // 1: userId
                "\"AFFLILIATED_HOSPITAL\" TEXT," + // 2: affiliatedHospital
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
    
}
