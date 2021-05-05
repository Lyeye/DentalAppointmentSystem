package com.lyeye.dental_appointment.controller;

import com.alibaba.fastjson.JSON;
import com.lyeye.dental_appointment.entity.Notice;
import com.lyeye.dental_appointment.entity.Photo;
import com.lyeye.dental_appointment.entity.User;
import com.lyeye.dental_appointment.mapper.NoticeMapper;
import com.lyeye.dental_appointment.mapper.PhotoMapper;
import com.lyeye.dental_appointment.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SuppressWarnings("ALL")
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PhotoMapper photoMapper;

    @Autowired
    NoticeMapper noticeMapper;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/getAllUser")
    public String getAll() {
        StringBuilder stringBuilder = new StringBuilder();
        List<User> userList = userMapper.getAll();
        if (userList.size() != 0) {
            for (int i = 0; i < userList.size(); i++) {
                stringBuilder.append(userList.get(i).toString() + "\n");
            }
            return stringBuilder.toString();
        } else {
            return "无数据";
        }
    }

    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    public String getUserById(@RequestParam("userId") String id) {
        User userById = userMapper.getUserById(Integer.valueOf(id));
        if (userById != null) {
            String jsonString = JSON.toJSONString(userById);
            return jsonString;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("email") String email, @RequestParam(value = "password", required = false) String password) {
        User userByEmail = userMapper.getUserByEmail(email);
        if (userByEmail != null) {
            if (userByEmail.getPassword().equals(password)) {
                return "SUCCESS==>" + userByEmail.getUserId() + "==>" + userByEmail.getName() + "==>" + userByEmail.getGender();
            } else {
                return "PwdError==>密码错误";
            }
        } else {
            return "NoData==>账户不存在";
        }
    }

    @RequestMapping(value = "/familyLogin", method = RequestMethod.GET)
    public String familyLogin(@RequestParam("number") String number, @RequestParam(value = "birthday", required = false) String birthday) {
        User userByNumber = userMapper.getUserByNumber(number);
        if (userByNumber != null) {
            if (userByNumber.getBirthday().equals(birthday)) {
                return "SUCCESS==>" + userByNumber.getUserId() + "==>" + userByNumber.getName() + "==>" + userByNumber.getGender();
            } else {
                return "BirthdayError==>生日错误";
            }
        } else {
            return "NoData==>账户不存在";
        }
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public boolean signUp(@RequestBody User user) {
        return userMapper.insert(user);
    }

    @RequestMapping(value = "/updateMyInfo", method = RequestMethod.POST)
    public boolean update(@RequestParam(value = "userId", required = false) String userId, @RequestBody User user) {
        user.setUserId(Integer.valueOf(userId));
        return userMapper.update(user);
    }



    @RequestMapping(value = "/myNotice", method = RequestMethod.GET)
    public String getNoticeByUserId(@RequestParam("userId") String id) {
        List<Notice> notices = noticeMapper.getAllByUserId(Integer.valueOf(id));
        if (notices != null) {
            String jsonString = JSON.toJSONString(notices);
            return jsonString;
        } else {
            return "无通知信息";
        }
    }
}
