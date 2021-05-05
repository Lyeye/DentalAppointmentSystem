package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Register;
import org.apache.ibatis.annotations.Insert;

public interface RegisterMapper {

    @Insert("insert into register(user_id,user_name,hospital_name,date,time) values(#{userId},#{userName},#{hospitalName},#{date},#{time})")
    boolean insert(Register register);
}
