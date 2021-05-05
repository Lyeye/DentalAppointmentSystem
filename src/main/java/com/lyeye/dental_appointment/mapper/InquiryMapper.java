package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Inquiry;
import org.apache.ibatis.annotations.Insert;

public interface InquiryMapper {
    @Insert("insert into inquiry(user_id,hospital_id,user_name,hospital,symptom,level,is_remote) values(#{userId},#{hospitalId},#{userName},#{hospital},#{symptom},#{level},#{isRemote})")
    boolean insert(Inquiry inquiry);
}
