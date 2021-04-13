package com.lyeye.dentalappointmentsystem.mapper;

import com.lyeye.dentalappointmentsystem.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> findAll();

    User findUserById(long id);

    User findUserByEmail(String email);

    User findUserByDiagnosisNumber(String diagnosisNumber);

    void insertUser(User user);

    void deleteUser(User user);
}
