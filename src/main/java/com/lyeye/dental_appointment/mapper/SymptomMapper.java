package com.lyeye.dental_appointment.mapper;

import com.lyeye.dental_appointment.entity.Symptom;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SymptomMapper {

    @Select("select * from symptom")
    List<Symptom> getAll();
}
