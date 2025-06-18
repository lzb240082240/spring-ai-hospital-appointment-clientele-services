package com.example.springai.hospitalappointment.clienteleservices.tools;

import com.example.springai.hospitalappointment.clienteleservices.entity.HospitalAppointment;
import com.example.springai.hospitalappointment.clienteleservices.service.HospitalAppointmentService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class HospitalAppointmentTools {
    private final HospitalAppointmentService hospitalAppointmentService;

    public HospitalAppointmentTools(HospitalAppointmentService hospitalAppointmentService) {
        this.hospitalAppointmentService = hospitalAppointmentService;
    }

    @Tool(name = "预约挂号", description = "根据参数，先执行工具方法queryDepartment查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约")
    public String bookAppointment(@ToolParam(description = "用户名字") String username,
                                  @ToolParam(description = "身份证") String idCard,
                                  @ToolParam(description = "科室名称") String department,
                                  @ToolParam(description = "日期") String date,
                                  @ToolParam(description = "时间，可选值：上午、下午") String time,
                                  @ToolParam(description = "医生姓名") String doctorName) {
        //查找数据库中是否包含对应的预约记录
        HospitalAppointment hospitalAppointmentDB = hospitalAppointmentService.existAppointment(username, idCard, department, date, time);
        if (hospitalAppointmentDB == null) {
            hospitalAppointmentDB = new HospitalAppointment();
            hospitalAppointmentDB.setUsername(username);
            hospitalAppointmentDB.setIdCard(idCard);
            hospitalAppointmentDB.setDepartment(department);
            hospitalAppointmentDB.setDate(date);
            hospitalAppointmentDB.setTime(time);
            hospitalAppointmentDB.setDoctorName(doctorName);
            hospitalAppointmentDB.setCreateTime(System.currentTimeMillis());
            boolean saveResult = hospitalAppointmentService.save(hospitalAppointmentDB);
            if (saveResult) {
                return "预约成功";
            } else {
                return "预约失败";
            }
        }
        return "您在相同的科室和时间已有预约";
    }

    @Tool(name = "取消预约挂号", description = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回取消预约成功，否则返回取消预约失败")
    public String cancelAppointment(@ToolParam(description = "用户名字") String username,
                                    @ToolParam(description = "身份证") String idCard,
                                    @ToolParam(description = "科室名称") String department,
                                    @ToolParam(description = "日期") String date,
                                    @ToolParam(description = "时间，可选值：上午、下午") String time) {
        HospitalAppointment hospitalAppointmentDB = hospitalAppointmentService.existAppointment(username, idCard, department, date, time);
        if (hospitalAppointmentDB != null) {
            boolean removeResult = hospitalAppointmentService.removeById(hospitalAppointmentDB.getId());
            //删除预约记录
            if (removeResult) {
                return "取消预约成功";
            } else {
                return "取消预约失败";
            }
        }
        return "您没有预约记录，请核对预约科室和时间";
    }

    @Tool(name = "查询是否有号源", description = "根据参数查询是否有号源，并返回给用户")
    public Boolean queryDepartment(
            @ToolParam(description = "科室名称") String department,
            @ToolParam(description = "日期") String date,
            @ToolParam(description = "时间，可选值：上午、下午") String time,
            @ToolParam(description = "医生姓名") String doctorName) {
        System.out.println("查询是否有号源：" + department + "," + date + "," + time + "," + doctorName);

        //  维护医生的排班信息:如果没有指定医生名字，则根据其他条件查询是否有可以预约的医生(有返回true，否则返回false);
        //  如果指定了医生名字，则判断医生是否有排班(没有排版返回false);
        //  如果有排班，则判断医生排班时间段是否已约满(约满返回false，有空闲时间返回true);
        return true;
    }
}
