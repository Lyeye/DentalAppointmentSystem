package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Remote;
import org.apache.ibatis.annotations.Insert;

public interface RemoteMapper {

    @Insert("insert into remote(user_id,user_name,room_name,date,start_time,finish_time) values(#{userId},#{userName},#{roomName},#{date},#{startTime},#{finishTime})")
    boolean insert(Remote remote);
}
