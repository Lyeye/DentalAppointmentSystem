package com.lyeye.dental_appointment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lyeye.dental_appointment.mapper")
public class DentalAppointmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentalAppointmentApplication.class, args);
    }

}
