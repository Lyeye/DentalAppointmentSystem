package com.lyeye.dentalappointmentsystem.mapper;

import com.lyeye.dentalappointmentsystem.entity.User;

public interface UserMapper {
    User findUserById(long id);

    User findUserByEmail(String email);

    User findUserByDiagnosisNumber(String diagnosisNumber);

    void insertUser(User user);
}
