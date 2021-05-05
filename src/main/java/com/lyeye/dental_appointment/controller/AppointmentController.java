package com.lyeye.dental_appointment.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyeye.dental_appointment.entity.Appointment;
import com.lyeye.dental_appointment.entity.Counting;
import com.lyeye.dental_appointment.entity.Inquiry;
import com.lyeye.dental_appointment.mapper.AppointmentMapper;
import com.lyeye.dental_appointment.mapper.CountingMapper;
import com.lyeye.dental_appointment.mapper.InquiryMapper;
import com.lyeye.dental_appointment.util.JsonXMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@RestController
public class AppointmentController {

    @Autowired
    AppointmentMapper appointmentMapper;

    @Autowired
    InquiryMapper inquiryMapper;

    @Autowired
    CountingMapper countingMapper;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/makeAnAppointment", method = RequestMethod.POST)
    private boolean MakeAnAppointment(@RequestBody Map<String, Object> models,
                                      @RequestParam(value = "userId", required = false) String userId,
                                      @RequestParam(value = "hospitalId", required = false) String hospitalId,
                                      @RequestParam(value = "isRemote", required = false) String isRemote) throws Exception {
        Appointment appointmentInfo = JsonXMLUtils.map2obj((Map<String, Object>) models.get("appointmentInfo"), Appointment.class);
        Inquiry inquiry = JsonXMLUtils.map2obj((Map<String, Object>) models.get("inquiryInfo"), Inquiry.class);
        appointmentInfo.setUserId(Integer.valueOf(userId));
        appointmentInfo.setHospitalId(Integer.valueOf(hospitalId));
        appointmentInfo.setIsRemote(Integer.valueOf(isRemote));
        inquiry.setUserId(Integer.valueOf(userId));
        inquiry.setHospitalId(Integer.valueOf(hospitalId));
        inquiry.setIsRemote(Integer.valueOf(isRemote));
        log.info(appointmentInfo.toString() + "====" + inquiry.toString());

        String hospital = appointmentInfo.getHospitalName();
        String date = appointmentInfo.getDate();
        String time = appointmentInfo.getTime();
        Counting countingByDateAndTime = countingMapper.getCountingByHospitalAndDateAndTime(hospital, date, time);
        if (countingByDateAndTime != null) {
            if ((countingByDateAndTime.getSum() + 1) <= 2) {
                countingMapper.updateCounting(countingByDateAndTime.getSum() + 1, countingByDateAndTime.getId());
            }
        } else {
            Counting counting = new Counting();
            counting.setHospitalId(Integer.valueOf(hospitalId));
            counting.setHospital(hospital);
            counting.setDate(date);
            counting.setTime(time);
            counting.setSum(1);
            countingMapper.createCounting(counting);
            System.out.println(counting.toString());
            log.info(counting.toString());
        }
        return appointmentMapper.insert(appointmentInfo) && inquiryMapper.insert(inquiry);
    }

    @RequestMapping(value = "/AppointmentListByUserId", method = RequestMethod.GET)
    private String getAllByUserId(@RequestParam(value = "userId", required = false) String userId) {
        List<Appointment> allByUserId = appointmentMapper.getAllByUserId(Integer.valueOf(userId));
        if (allByUserId.size() == 0) {
            return "该用户无预约信息";
        } else {
            String jsonString = JSONArray.parseArray(JSONObject.toJSONString(allByUserId)).toJSONString();
            return jsonString;
        }
    }

    @RequestMapping(value = "/AppointmentListByHospitalId", method = RequestMethod.GET)
    private String getAllByHospitalId(@RequestParam(value = "hospitalId", required = false) String hospitalId) {
        List<Appointment> allByHospitalId = appointmentMapper.getAllByHospitalId(Integer.valueOf(hospitalId));
        if (allByHospitalId.size() == 0) {
            return "该诊所无预约信息";
        } else {
            String jsonString = JSONArray.parseArray(JSONObject.toJSONString(allByHospitalId)).toJSONString();
            return jsonString;
        }
    }

    @RequestMapping(value = "/cancelAppointment", method = RequestMethod.POST)
    private void deleteAppointmentInfoById(@RequestParam String amiId) {
        Appointment appointmentInfoById = appointmentMapper.getAppointmentInfoById(Integer.valueOf(amiId));
        Counting countingByHospitalAndDateAndTime = countingMapper.getCountingByHospitalAndDateAndTime(appointmentInfoById.getHospitalName(), appointmentInfoById.getDate(), appointmentInfoById.getTime());
        int sum = countingByHospitalAndDateAndTime.getSum();
        if ((sum - 1) == 0) {
            countingMapper.deleteCounting(countingByHospitalAndDateAndTime.getId());
        } else {
            countingMapper.updateCounting(sum - 1, countingByHospitalAndDateAndTime.getId());
        }
        appointmentMapper.deleteByAmiId(Integer.valueOf(amiId));
    }

    @RequestMapping(value = "/AppointmentListByHospitalIdAndDate", method = RequestMethod.GET)
    private String getAllByHospitalIdAndDate(@RequestParam(value = "hospitalId", required = false) String hospitalId,
                                             @RequestParam(value = "date", required = false) String date) {
        List<Appointment> allByHospitalIdAndDate = appointmentMapper.getAllByHospitalIdAndDate(Integer.valueOf(hospitalId), date);
        if (allByHospitalIdAndDate.size() == 0) {
            return "该诊所该日无预约信息";
        } else {
            String jsonString = JSONArray.parseArray(JSONObject.toJSONString(allByHospitalIdAndDate)).toJSONString();
            return jsonString;
        }
    }
}
