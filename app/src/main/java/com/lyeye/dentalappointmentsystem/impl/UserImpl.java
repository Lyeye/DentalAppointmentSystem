package com.lyeye.dentalappointmentsystem.impl;

import android.content.Context;
import android.util.Log;

import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.greendao.DaoManager;
import com.lyeye.dentalappointmentsystem.greendao.DaoSession;
import com.lyeye.dentalappointmentsystem.greendao.UserDao;
import com.lyeye.dentalappointmentsystem.mapper.UserMapper;

import java.util.List;


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
    public List<User> findAll() {
        return userDao.queryBuilder().list();
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
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void insertUser(User user) {
        userDao.insert(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Override
    public void clear() {
        userDao.deleteAll();
    }
}
