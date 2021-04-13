package com.lyeye.dentalappointmentsystem.mapper;

import android.content.Context;

import com.lyeye.dentalappointmentsystem.entity.Administrator;
import com.lyeye.dentalappointmentsystem.greendao.AdministratorDao;
import com.lyeye.dentalappointmentsystem.greendao.DaoManager;
import com.lyeye.dentalappointmentsystem.greendao.DaoSession;

public class AdministratorImpl implements AdministratorMapper {

    private DaoManager daoManager;
    private Context context;
    private DaoSession daoSession;
    private AdministratorDao administratorDao;

    public AdministratorImpl(Context context) {
        this.context = context;
        daoManager = DaoManager.getInstance();
        daoManager.initGreenDao(context, "DentalAppoitmentSystem");
        daoSession = daoManager.getDaoSession();
        administratorDao = daoSession.getAdministratorDao();
    }

    @Override
    public void addAdmin(Administrator administrator) {
        if (administratorDao.queryBuilder().list().isEmpty()) {
            administratorDao.insert(administrator);
        }
    }

    @Override
    public Administrator findAdminById(long adminId) {
        return administratorDao.queryBuilder()
                .where(AdministratorDao.Properties.AdministratorId.eq(adminId)).unique();
    }

    @Override
    public Administrator findAdminByEmail(String adminEmail) {
        return administratorDao.queryBuilder()
                .where(AdministratorDao.Properties.AdministratorEmail.eq(adminEmail)).unique();
    }
}
