package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Hospital;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HospitalMapper {

    @Select("select * from hospital")
    List<Hospital> getAll();

    @Select("select * from hospital where hospital_id = #{id}")
    Hospital getHospitalById(int id);

    @Insert("insert into hospital(name,address,phone,doctor,holiday) values(#{name},#{address},#{phone},#{doctor},#{holiday})")
    boolean insert(Hospital hospital);
}
