package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Appointment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AppointmentMapper {

    @Select("select * from appointment where user_id = #{id}")
    List<Appointment> getAllByUserId(int id);

    @Select("select * from appointment where hospital_id = #{id}")
    List<Appointment> getAllByHospitalId(int id);

    @Select("select * from appointment where ami_id = #{id}")
    Appointment getAppointmentInfoById(int id);

    @Insert("insert into appointment(user_id,hospital_id,hospital_name,user_name,symptom,date,time,is_remote,is_arrive) values(#{userId},#{hospitalId},#{hospitalName},#{userName},#{symptom},#{date},#{time},#{isRemote},#{isArrive})")
    boolean insert(Appointment appointment);

    @Delete("delete from appointment where ami_id = #{id}")
    boolean deleteByAmiId(int id);

    @Select("select * from appointment where hospital_id = #{id} and date = #{date}")
    List<Appointment> getAllByHospitalIdAndDate(int id, String date);
}
