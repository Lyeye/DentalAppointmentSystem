package com.lyeye.dental_appointment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyeye.dental_appointment.entity.Hospital;
import com.lyeye.dental_appointment.entity.Pay;
import com.lyeye.dental_appointment.entity.Register;
import com.lyeye.dental_appointment.entity.Symptom;
import com.lyeye.dental_appointment.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("ALL")
@RestController
public class AdditionController {

    @Autowired
    PayMapper payMapper;

    @Autowired
    RegisterMapper registerMapper;

    @Autowired
    AppointmentMapper appointmentMapper;


    @Autowired
    HospitalMapper hospitalMapper;

    @Autowired
    SymptomMapper symptomMapper;

    @RequestMapping("/getAllHospital")
    public String getAllHospital() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Hospital> hospitalList = hospitalMapper.getAll();
        if (hospitalList.size() != 0) {
            String hospitalListJson = JSON.toJSONString(hospitalList);
            return hospitalListJson;
        } else {
            return "无医院数据";
        }
    }

    @RequestMapping("/getAllSymptom")
    public String getAllSymptom() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Symptom> symptomList = symptomMapper.getAll();
        if (symptomList.size() != 0) {
            String jsonString = JSONArray.parseArray(JSONObject.toJSONString(symptomList)).toJSONString();
            return jsonString;
        } else {
            return "无症状数据";
        }
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public boolean uploadPhoto(@RequestBody Pay pay,
                               @RequestParam(value = "userId", required = false) String userId,
                               @RequestParam(value = "amount", required = false) String amount) {
        pay.setUserId(Integer.valueOf(userId));
        pay.setAmount(Integer.valueOf(amount));
        System.out.println(pay.toString() + " userId" + userId + " amount" + amount);
        return payMapper.insert(pay);
    }

    @RequestMapping(value = "/checkIn", method = RequestMethod.POST)
    public String checkIn(@RequestBody Register register,
                          @RequestParam(value = "userId", required = false) String userId) {
        register.setUserId(Integer.valueOf(userId));
        if (registerMapper.insert(register)) {
            return "SUCCESS";
        } else {
            return "FAILURE";
        }
    }

}
