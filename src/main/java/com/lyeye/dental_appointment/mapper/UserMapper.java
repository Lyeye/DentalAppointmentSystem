package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    List<User> getAll();

    @Select("select * from user where user_id = #{id}")
    User getUserById(int id);

    @Select("select * from user where email = #{email}")
    User getUserByEmail(String email);

    @Select("select * from user where number = #{number}")
    User getUserByNumber(String number);

    @Insert("insert into user(name,gender,email,password,phone,number,birthday,create_at,type) values(#{name},#{gender},#{email},#{password},#{phone},#{number},#{birthday},#{createAt},#{type})")
    boolean insert(User user);

    @Update("update user set name = #{name}, gender=#{gender},email = #{email},password=#{password},phone=#{phone},number=#{number},birthday=#{birthday},create_at=#{createAt},type=#{type} where user_id=#{userId}")
    boolean update(User user);

}
