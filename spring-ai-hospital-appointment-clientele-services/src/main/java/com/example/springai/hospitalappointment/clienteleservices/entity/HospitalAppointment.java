package com.example.springai.hospitalappointment.clienteleservices.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 医院挂号信息
 */
@TableName(value = "hospital_appointment")
@Data
@EqualsAndHashCode(of = {"id"})
public class HospitalAppointment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名字
     */
    private String username;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 预约科室
     */
    private String department;

    /**
     * 预约日期
     */
    private String date;

    /**
     * 预约时间 （上午|下午）
     */
    private String time;

    /**
     * 医生名字
     */
    private String doctorName;

    /**
     * 创建时间.
     */
    private Long createTime;
}