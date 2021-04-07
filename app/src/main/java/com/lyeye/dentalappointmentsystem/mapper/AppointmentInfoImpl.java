package com.lyeye.dentalappointmentsystem.mapper;

import android.content.Context;

import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.greendao.AppointmentInfoDao;
import com.lyeye.dentalappointmentsystem.greendao.DaoManager;
import com.lyeye.dentalappointmentsystem.greendao.DaoSession;

import java.text.SimpleDateFormat;
import java.util.List;

public class AppointmentInfoImpl implements AppointmentInfoMapper {

    private DaoManager daoManager;
    private Context context;
    private DaoSession daoSession;
    private AppointmentInfoDao appointmentInfoDao;

    public AppointmentInfoImpl(Context context) {
        this.context = context;
        daoManager = DaoManager.getInstance();
        daoManager.initGreenDao(context, "DentalAppoitmentSystem");
        daoSession = daoManager.getDaoSession();
        appointmentInfoDao = daoSession.getAppointmentInfoDao();
    }

    @Override
    public List<AppointmentInfo> findAppointmentInfoByUserId(long uid) {
        List<AppointmentInfo> appointmentInfos =
                appointmentInfoDao.queryBuilder()
                        .where(AppointmentInfoDao.Properties.UserId.eq(uid)).list();
        return appointmentInfos;
    }

    @Override
    public AppointmentInfo findAppointmentInfo(long aimId) {
        return appointmentInfoDao.queryBuilder()
                .where(AppointmentInfoDao.Properties.AmiId.eq(aimId)).unique();
    }

    @Override
    public List<AppointmentInfo> findAllInAffiliatedHospital(String affiliatedHospital) {
        return appointmentInfoDao.queryBuilder()
                .where(AppointmentInfoDao.Properties.AffiliatedHospital.eq(affiliatedHospital)).list();
    }

    @Override
    public List<AppointmentInfo> findAppointmentInfoByAmiDateAndAffiliatedHospital(String amiDate, String affiliatedHospital) {
        return appointmentInfoDao.queryBuilder()
                .where(AppointmentInfoDao.Properties.AmiDate.eq(amiDate))
                .where(AppointmentInfoDao.Properties.AffiliatedHospital.eq(affiliatedHospital)).list();
    }


    @Override
    public void insertAppointmentInfo(AppointmentInfo appointmentInfo) {
        appointmentInfoDao.insert(appointmentInfo);
    }

    @Override
    public void deleteAppointmentInfo(AppointmentInfo appointmentInfo) {
        appointmentInfoDao.delete(appointmentInfo);
    }
}
