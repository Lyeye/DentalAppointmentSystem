package com.lyeye.dentalappointmentsystem.mapper;

import android.content.Context;

import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.greendao.DaoManager;
import com.lyeye.dentalappointmentsystem.greendao.DaoSession;
import com.lyeye.dentalappointmentsystem.greendao.UserDao;


public class UserImpl implements UserMapper {

    private DaoManager daoManager;
    private Context context;
    private DaoSession daoSession;
    private UserDao userDao;

    public UserImpl(Context context) {
        this.context = context;
        daoManager = DaoManager.getInstance();
        daoManager.initGreenDao(context, "DentalAppoitmentSystem");
        daoSession = daoManager.getDaoSession();
        userDao = daoSession.getUserDao();
    }

    @Override
    public User findUserById(long id) {
        return userDao.queryBuilder()
                .where(UserDao.Properties.UserId.eq(id)).unique();
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.queryBuilder()
                .where(UserDao.Properties.UserEmail.eq(email)).unique();
    }

    @Override
    public User findUserByDiagnosisNumber(String diagnosisNumber) {
        return userDao.queryBuilder()
                .where(UserDao.Properties.DiagnosisNumber.eq(diagnosisNumber)).unique();
    }

    @Override
    public void insertUser(User user) {
        userDao.insert(user);
    }
}
