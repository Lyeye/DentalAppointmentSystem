package com.lyeye.dentalappointmentsystem.impl;

import android.content.Context;

import com.lyeye.dentalappointmentsystem.entity.Hospital;
import com.lyeye.dentalappointmentsystem.greendao.AppointmentInfoDao;
import com.lyeye.dentalappointmentsystem.greendao.DaoManager;
import com.lyeye.dentalappointmentsystem.greendao.DaoSession;
import com.lyeye.dentalappointmentsystem.greendao.HospitalDao;
import com.lyeye.dentalappointmentsystem.mapper.HospitalMapper;

import java.util.List;

public class HospitalImpl implements HospitalMapper {

    private DaoManager daoManager;
    private Context context;
    private DaoSession daoSession;
    private HospitalDao hospitalDao;

    public HospitalImpl(Context context) {
        this.context = context;
        daoManager = DaoManager.getInstance();
        daoManager.initGreenDao(context, "DentalAppoitmentSystem");
        daoSession = daoManager.getDaoSession();
        hospitalDao = daoSession.getHospitalDao();
    }


    @Override
    public List<Hospital> findAll() {
        List<Hospital> hospitals = hospitalDao.queryBuilder().list();
        if (hospitals.size() == 0) {
            Hospital hospital1 = new Hospital();
            hospital1.setHospitalName("仁康医院");
            hospitalDao.insert(hospital1);
            Hospital hospital2 = new Hospital();
            hospital2.setHospitalName("中心医院");
            hospitalDao.insert(hospital2);
            Hospital hospital3 = new Hospital();
            hospital3.setHospitalName("人民医院");
            hospitalDao.insert(hospital3);
            Hospital hospital4 = new Hospital();
            hospital4.setHospitalName("华康医院");
            hospitalDao.insert(hospital4);
            Hospital hospital5 = new Hospital();
            hospital5.setHospitalName("协和医院");
            hospitalDao.insert(hospital5);
            return hospitalDao.queryBuilder().list();
        } else {
            return hospitalDao.queryBuilder().list();
        }
    }
}
