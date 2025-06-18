CREATE TABLE `hospital_appointment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) NOT NULL COMMENT '用户名字',
  `id_card` varchar(18) NOT NULL COMMENT '用户身份证',
  `department` varchar(50) NOT NULL COMMENT '预约科室',
  `date` varchar(10) NOT NULL COMMENT '预约日期',
  `time` varchar(10) NOT NULL COMMENT '预约时间 （上午|下午）',
  `doctor_name` varchar(50) NULL DEFAULT NULL COMMENT '医生名字',
  `create_time` bigint(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) comment='医院挂号信息表';

