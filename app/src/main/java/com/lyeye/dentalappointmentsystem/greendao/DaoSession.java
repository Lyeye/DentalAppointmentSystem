package com.lyeye.dentalappointmentsystem.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.User;

import com.lyeye.dentalappointmentsystem.greendao.AppointmentInfoDao;
import com.lyeye.dentalappointmentsystem.greendao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig appointmentInfoDaoConfig;
    private final DaoConfig userDaoConfig;

    private final AppointmentInfoDao appointmentInfoDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        appointmentInfoDaoConfig = daoConfigMap.get(AppointmentInfoDao.class).clone();
        appointmentInfoDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        appointmentInfoDao = new AppointmentInfoDao(appointmentInfoDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(AppointmentInfo.class, appointmentInfoDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        appointmentInfoDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public AppointmentInfoDao getAppointmentInfoDao() {
        return appointmentInfoDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
