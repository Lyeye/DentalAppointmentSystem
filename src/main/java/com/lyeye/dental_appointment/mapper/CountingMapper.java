package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Counting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CountingMapper {

    @Select("select * from counting where hospital = #{hospital} and date = #{date} and time = #{time}")
    Counting getCountingByHospitalAndDateAndTime(String hospital, String date, String time);

    @Insert("insert into counting(hospital_id,hospital,date,time,sum) values(#{hospitalId},#{hospital},#{date},#{time},#{sum})")
    boolean createCounting(Counting counting);

    @Update("update counting set sum = #{sum} where id = #{id}")
    boolean updateCounting(int sum, int id);

    @Delete("delete from counting where id = #{id}")
    boolean deleteCounting(int id);

}
