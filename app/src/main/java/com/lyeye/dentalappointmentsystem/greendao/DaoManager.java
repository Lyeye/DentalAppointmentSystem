package com.lyeye.dentalappointmentsystem.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DaoManager {

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManager daoManager = new DaoManager();
    private static DaoSession daoSession;
    private static DaoMaster daoMaster;
    private static DaoMaster.DevOpenHelper helper;

    public static DaoManager getInstance() {
        return daoManager;
    }

    public void initGreenDao(Context context, String dbName) {
        helper = new DaoMaster.DevOpenHelper(context, dbName);
        SQLiteDatabase database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    private void closeDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    private void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }
}
