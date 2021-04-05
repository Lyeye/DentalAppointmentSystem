package com.lyeye.dentalappointmentsystem.mapper;

import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;

import java.util.List;

public interface AppointmentInfoMapper {
    List<AppointmentInfo> findAppointmentInfoByUserId(long uid);

    AppointmentInfo findAppointmentInfo(long aimId);

    List<AppointmentInfo> findAll();

    List<AppointmentInfo> findAppointmentInfoByAmiDate(String amiDate);

    void insertAppointmentInfo(AppointmentInfo appointmentInfo);

    void deleteAppointmentInfo(AppointmentInfo appointmentInfo);
}
