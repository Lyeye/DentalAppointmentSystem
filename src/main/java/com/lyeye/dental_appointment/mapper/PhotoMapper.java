package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Photo;
import org.apache.ibatis.annotations.Insert;

public interface PhotoMapper {

    @Insert("insert into photo(user_id,user_name,path,upload_to,upload_at) values(#{userId},#{userName},#{path},#{uploadTo},#{uploadAt})")
    boolean save(Photo photo);
}
