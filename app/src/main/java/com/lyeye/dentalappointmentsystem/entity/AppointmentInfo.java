package com.lyeye.dentalappointmentsystem.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

import org.greenrobot.greendao.DaoException;

import com.lyeye.dentalappointmentsystem.greendao.DaoSession;
import com.lyeye.dentalappointmentsystem.greendao.UserDao;
import com.lyeye.dentalappointmentsystem.greendao.AppointmentInfoDao;

@Entity
public class AppointmentInfo {

    @Id(autoincrement = true)
    @Property(nameInDb = "APPOINTMENT_INFO_ID")
    private Long amiId;

    @Property(nameInDb = "USER_ID")
    @NotNull
    private long userId;

    @ToOne(joinProperty = "userId")
    private User user;

    @Property(nameInDb = "AFFILIATED_HOSPITAL")
    private String affiliatedHospital;

    @Property(nameInDb = "APPOINTMENT_INFO_DATE")
    private String amiDate;

    @Property(nameInDb = "APPOINTMENT_INFO_TIME")
    private String amiTime;

    @Property(nameInDb = "APPOINTMENT_INFO_SYMPTOMS")
    private String amiSymptoms;

    @Property(nameInDb = "CREATE_AT")
    private Date createAt;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1916966759)
    private transient AppointmentInfoDao myDao;

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    @Generated(hash = 1142461733)
    public AppointmentInfo() {
    }


    @Generated(hash = 751813133)
    public AppointmentInfo(Long amiId, long userId, String affiliatedHospital,
                           String amiDate, String amiTime, String amiSymptoms, Date createAt) {
        this.amiId = amiId;
        this.userId = userId;
        this.affiliatedHospital = affiliatedHospital;
        this.amiDate = amiDate;
        this.amiTime = amiTime;
        this.amiSymptoms = amiSymptoms;
        this.createAt = createAt;
    }


    public Long getAmiId() {
        return this.amiId;
    }

    public void setAmiId(Long amiId) {
        this.amiId = amiId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAmiTime() {
        return this.amiTime;
    }

    public void setAmiTime(String amiTime) {
        this.amiTime = amiTime;
    }

    public String getAmiSymptoms() {
        return this.amiSymptoms;
    }

    public void setAmiSymptoms(String amiSymptoms) {
        this.amiSymptoms = amiSymptoms;
    }


    public String getAmiDate() {
        return this.amiDate;
    }


    public void setAmiDate(String amiDate) {
        this.amiDate = amiDate;
    }

    @Override
    public String toString() {
        return "AppointmentInfo{" +
                "amiId=" + amiId +
                ", userId=" + userId +
                ", user=" + user +
                ", affiliatedHospital='" + affiliatedHospital + '\'' +
                ", amiDate='" + amiDate + '\'' +
                ", amiTime='" + amiTime + '\'' +
                ", amiSymptoms='" + amiSymptoms + '\'' +
                ", createAt=" + createAt +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", user__resolvedKey=" + user__resolvedKey +
                '}';
    }

    public String getAffiliatedHospital() {
        return this.affiliatedHospital;
    }


    public void setAffiliatedHospital(String affiliatedHospital) {
        this.affiliatedHospital = affiliatedHospital;
    }


    public Date getCreateAt() {
        return this.createAt;
    }


    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }


    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 115391908)
    public User getUser() {
        long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }


    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1622843587)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException(
                    "To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            userId = user.getUserId();
            user__resolvedKey = userId;
        }
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1494512004)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAppointmentInfoDao() : null;
    }
}
