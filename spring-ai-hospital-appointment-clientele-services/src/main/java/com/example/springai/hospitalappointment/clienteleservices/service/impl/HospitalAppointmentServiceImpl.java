package com.example.springai.hospitalappointment.clienteleservices.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springai.hospitalappointment.clienteleservices.entity.HospitalAppointment;
import com.example.springai.hospitalappointment.clienteleservices.mapper.HospitalAppointmentMapper;
import com.example.springai.hospitalappointment.clienteleservices.service.HospitalAppointmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HospitalAppointmentServiceImpl extends ServiceImpl<HospitalAppointmentMapper, HospitalAppointment> implements HospitalAppointmentService {
    @Resource
    private HospitalAppointmentMapper hospitalAppointmentMapper;

    @Override
    public HospitalAppointment existAppointment(String username, String idCard, String department, String date, String time) {
        LambdaQueryWrapper<HospitalAppointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HospitalAppointment::getUsername, username);
        queryWrapper.eq(HospitalAppointment::getIdCard, idCard);
        queryWrapper.eq(HospitalAppointment::getDepartment, department);
        queryWrapper.eq(HospitalAppointment::getDate, date);
        queryWrapper.eq(HospitalAppointment::getTime, time);
        HospitalAppointment hospitalAppointment = hospitalAppointmentMapper.selectOne(queryWrapper);
        return hospitalAppointment;
    }
}
