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
        daoManager.initGreenDao(context, "DentalAppointmentSystem");
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
        long start = System.currentTimeMillis();
        User user = userDao.queryBuilder()
                .where(UserDao.Properties.UserEmail.eq(email)).unique();
        long end = System.currentTimeMillis();
        Log.d(null, "findUserByEmail使用时间: " + (end - start) + "ms");
        return user;
    }

    @Override
    public User findUserByDiagnosisNumber(String diagnosisNumber) {
        return userDao.queryBuilder()
                .where(UserDao.Properties.DiagnosisNumber.eq(diagnosisNumber)).unique();
    }

    @Override
    public void updateUser(User user) {
        long start = System.currentTimeMillis();
        userDao.update(user);
        long end = System.currentTimeMillis();
        Log.d(null, "updateUser使用时间: " + (end - start) + "ms");
    }

    @Override
    public void insertUser(User user) {
        long start = System.currentTimeMillis();
        userDao.insert(user);
        long end = System.currentTimeMillis();
        Log.d(null, "insertUser使用时间: " + (end - start) + "ms");
    }

    @Override
    public void deleteUser(User user) {
        long start = System.currentTimeMillis();
        userDao.delete(user);
        long end = System.currentTimeMillis();
        Log.d(null, "deleteUser使用时间: " + (end - start) + "ms");
    }

    @Override
    public void clear() {
        long start = System.currentTimeMillis();
        userDao.deleteAll();
        long end = System.currentTimeMillis();
        Log.d(null, "clear使用时间: " + (end - start) + "ms");
    }
}
