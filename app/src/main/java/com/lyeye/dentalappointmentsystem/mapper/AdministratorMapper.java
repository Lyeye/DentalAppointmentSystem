package com.lyeye.dentalappointmentsystem.mapper;

import com.lyeye.dentalappointmentsystem.entity.Administrator;

public interface AdministratorMapper {

    void addAdmin(Administrator administrator);

    Administrator findAdminById(long adminId);

    Administrator findAdminByEmail(String adminEmail);
}
