package com.example.springai.hospitalappointment.clienteleservices.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springai.hospitalappointment.clienteleservices.entity.HospitalAppointment;

public interface HospitalAppointmentService extends IService<HospitalAppointment> {
    HospitalAppointment existAppointment(String username, String idCard, String department, String date, String time);
}
