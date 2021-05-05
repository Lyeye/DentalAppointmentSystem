package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Notice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper {

    @Select("select * from notice where user_id = #{id}")
    List<Notice> getAllByUserId(int id);

}
