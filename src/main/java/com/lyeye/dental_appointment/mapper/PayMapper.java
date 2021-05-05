package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Pay;
import org.apache.ibatis.annotations.Insert;

public interface PayMapper {
    @Insert("insert into pay(user_id,payer,payee,amount,date,time) values(#{userId},#{payer},#{payee},#{amount},#{date},#{time})")
    boolean insert(Pay pay);
}
