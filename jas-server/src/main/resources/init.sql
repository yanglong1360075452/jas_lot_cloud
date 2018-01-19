/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : pathcloud_wechat

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-05-24 14:44:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------

drop TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`           BIGINT(20)       NOT NULL AUTO_INCREMENT,
  `open_id`      VARCHAR(255)    NOT NULL UNIQUE,#微信用户ID
  `real_name`    VARCHAR(30)     NOT NULL , #用户真实姓名
  `sex`          INT(1)          DEFAULT NULL , #性别
  `phone`        VARCHAR(30)    NOT NULL , #手机号码
  `birth_date`   DATETIME        DEFAULT null, #出生年月
  `score_value`  int      DEFAULT NULL ,#积分的分值
  `head_portrait`  VARCHAR(255)  DEFAULT NULL,  #头像
  `create_time`  datetime        DEFAULT CURRENT_TIMESTAMP ,
  `update_time`   DATETIME      DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

INSERT INTO `user` (`id`, `open_id`, `real_name`, `sex`, `phone`, `birth_date`, `score_value`, `head_portrait`, `create_time`, `update_time`) VALUES ('1', 'admin', 'admin', '1', '123456789', '2017-08-03 09:50:48', '14', '', '2017-08-03 09:50:48', '2017-08-03 09:50:48');

ALTER TABLE `user` ADD UNIQUE (`open_id`);
ALTER TABLE `user` ADD INDEX index_phone ( `phone` );



DROP TABLE if EXISTS `patient`;
CREATE TABLE `patient`(
  `id` bigint(20)  NOT NULL auto_increment,
  `user_id` bigint(20) NOT NULL ,#用户id
  PRIMARY KEY (`id`)

)engine=InnoDB DEFAULT charset=utf8;

ALTER TABLE `patient` ADD INDEX index_userId ( `user_id` );



DROP TABLE if EXISTS `doctor`;
   create table `doctor` (
    `id` bigint(20)  not null auto_increment,
    `user_id` bigint(20) NOT NULL ,#用户id
    `hospital` int(11) DEFAULT NULL , #医院
    `department` int(11) DEFAULT null, #科室
    `professiona` VARCHAR(255) DEFAULT NULL ,#职称
    `description` VARCHAR(255) DEFAULT NULL,#简介
--     `status` int(1) DEFAULT NULL,#审核状态0正在审核中,1审核通过,2未通过
     PRIMARY KEY (`id`)
  )engine=InnoDB DEFAULT charset=utf8;

  ALTER TABLE `doctor` ADD INDEX index_userId ( `user_id` );


DROP TABLE if EXISTS `score`; #积分变化表
 create table `score` (
   `id` bigint(20)  not null auto_increment,
   `user_id` bigint(20) NOT NULL ,#用户id
   `score_change` INT(1) DEFAULT NULL ,#积分操作
   `change_value` VARCHAR (20) DEFAULT NULL , #变化积分
   `surplus_score` bigint(20) DEFAULT NULL ,#剩余积分
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   `update_time`  datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

DROP TABLE if EXISTS `exchange_record`; #兑换记录表
 create table `exchange_record` (
   `id` bigint(20)  not null auto_increment,
   `user_id` bigint(20) NOT NULL ,#用户id
   `coupon_id` bigint(20)  NOT NULL ,#优惠券id
   `status`     INT(1) DEFAULT NULL ,#优惠券状态
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   `update_time`  datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

DROP TABLE if EXISTS `coupon`; #优惠券表
 create table `coupon` (
   `id` bigint(20)  not null auto_increment,
   `coupon_password` VARCHAR(255) DEFAULT NULL ,#优惠券密码
   `redeem_score` int(20) DEFAULT NULL , #兑换的积分值
   `coupon_count` int(20) DEFAULT NULL ,#优惠券数量
   `coupon_money` FLOAT (20) DEFAULT NULL ,#优惠券金额
   `validity` datetime DEFAULT NULL,#优惠券有效期
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   `update_time`  datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;


DROP TABLE if EXISTS `exercise_process`; #训练计划表
 create table `exercise_process` (
   `id` bigint(20)  not null auto_increment,
   `patient_id` bigint(20) NOT NULL ,#病人id
   `doctor_id` bigint(20) DEFAULT NULL ,#治疗师id
   `arthro` int(1) DEFAULT NULL , #关节
   `postion` int(1) DEFAULT NULL ,#部位
   `direction`  int(11) DEFAULT NULL ,#方向
   `start_time`     datetime  DEFAULT NULL ,#训练计划开始时间
   `end_time`   datetime DEFAULT NULL ,#训练计划结束时间
    `note`   VARCHAR(255) DEFAULT NULL ,#备注
   `create_by`   bigint(20) DEFAULT NULL ,#创建人
   `update_by`  bigint(20) DEFAULT NULL ,#更新人
   `sign`      int(1)  DEFAULT NULL,#医生有没有读到
   `report`      int(1)  DEFAULT NULL,#0代表没有新的评估报告,1代表新的评估报告
   `record`      int(1)  DEFAULT NULL,#0代表没有新的训练记录,1代表有新的训练记录
   `is_comfirmed`  int(1)  DEFAULT NULL,#0正在申请,1同意,-1拒绝
   comfirmed_time  datetime DEFAULT CURRENT_TIMESTAMP, #确认时间
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   `update_time`  datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;
alter table exercise_process add column is_comfirmed int(1);
alter table exercise_process add column comfirmed_time DEFAULT CURRENT_TIMESTAMP;
alter table exercise_process add column record int(1);
alter table exercise_process add column recort int(1);



DROP TABLE IF EXISTS `exercise_record`; #训练记录表
create table `exercise_record` (
    `id` bigint(20)  not null auto_increment,
   `patient_id` bigint(20) NOT NULL ,#病人id
   `device_id` bigint(20) DEFAULT NULL ,#设备id
   `arthro` int(1) DEFAULT NULL , #关节
   `postion` int(1) DEFAULT NULL ,#左右
   `direction`  int(1) DEFAULT NULL ,#方向
   `geolocation` VARCHAR(255) DEFAULT null, #地理位置
   `start_time`     datetime  DEFAULT NULL ,#训练开始时间
   `end_time`   datetime DEFAULT NULL ,#训练结束时间
   `start_angle`   INT(1) DEFAULT null ,#训练起始角度
   `end_angle`  INT(1) DEFAULT null ,#训练结束角度
    `sr`  INT(1) DEFAULT null ,#进步次数
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;
alter table exercise_record add column record_data Text;
/*[{"startTime": "2017-08-30 11:05:34", "startAngle": "10", "endAngle": "15"},
 {"startTime": "2017-08-30 11:10:34", "startAngle": "10", "endAngle": "10"},
 {"startTime": "2017-08-30 11:15:34", "startAngle": "15", "endAngle": "20"},
 {"startTime": "2017-08-30 11:20:34", "startAngle": "20", "endAngle": "10"},
 {"startTime": "2017-08-30 11:25:34", "startAngle": "10", "endAngle": "15"},
 {"startTime": "2017-08-30 11:35:34", "startAngle": "20", "endAngle": "25"}]*/


/*[
 {"time": "2017-08-30 11:00:00", "angle": "80", "level": "1"},
 {"time": "2017-08-30 11:05:34", "angle": "70", "level": "1"},
 {"time": "2017-08-30 11:08:34", "angle": "78", "level": "1"},
 {"time": "2017-08-30 11:10:34", "event": "pause"},
 {"time": "2017-08-30 11:15:34", "angle": "68", "level": "1"},
 {"time": "2017-08-30 11:20:34", "angle": "69", "level": "2"},
 {"time": "2017-08-30 11:25:34", "angle": "40", "level": "2"},
 {"time": "2017-08-30 11:30:00", "event": "end"}
 ]*/

/*[
 {"time": "2017-08-01 11:00:00", "startAngle": "10", "endAngle": "30"},
 {"time": "2017-08-02 11:00:00", "startAngle": "15", "endAngle": "35"},
 {"time": "2017-08-03 11:00:00", "startAngle": "18", "endAngle": "40"},
 {"time": "2017-08-04 11:00:00", "startAngle": "20", "endAngle": "45"},
 {"time": "2017-08-05 11:00:00", "startAngle": "30", "endAngle": "47"},
 {"time": "2017-08-06 11:00:00", "startAngle": "40", "endAngle": "50"},
 {"time": "2017-08-07 11:00:00", "startAngle": "45", "endAngle": "60"},
 ]*/

DROP TABLE IF EXISTS `report`; #评估报告表
create table `report` (
    `id` bigint(20)  not null auto_increment,
   `ep_id` bigint(20) NOT NULL ,#训练计划id
   `doctor_id` bigint(20) DEFAULT NULL ,#治疗师id
   `start_time` datetime DEFAULT NULL , #评估开始时间
   `end_time` datetime DEFAULT NULL ,#评估结束时间
   `verdict`  int(20) DEFAULT NULL ,#评估结论
   `content` VARCHAR(255) DEFAULT null, #评估内容
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;


DROP TABLE IF EXISTS `medical_archival`; #病历档案表
create table `medical_archival` (
    `id` bigint(20)  not null auto_increment,
   `patient_id` bigint(20) NOT NULL ,#病人id
   `surgery_time` datetime DEFAULT NULL ,#手术时间
   `start_time` datetime DEFAULT NULL , #初诊时间
   `illness_state` INT(11) DEFAULT NULL ,#病情说明
   `activity`  INT(11) DEFAULT NULL ,#活动不足因素
   `process` int(11) DEFAULT null, #病程
   `department` int(11) DEFAULT NULL ,#科室
   `hospital`  int(11) DEFAULT NULL ,#手术医院
   `doctor_name` VARCHAR(100) DEFAULT NULL ,#主刀医生
   `content` VARCHAR(255) DEFAULT NULL ,#病例描述
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;




DROP TABLE IF EXISTS `clinical_data`; #临床资料表
create table `clinical_data` (
    `id` bigint(20)  not null auto_increment,
     `ma_id` bigint(20) not NULL ,#病历档案id
   `content` VARCHAR(255) DEFAULT NULL ,#临床资料文字内容
   `src` VARCHAR(255) DEFAULT NULL , #图片
   `time` datetime DEFAULT NULL ,#图片时间点
   `type` VARCHAR (255) DEFAULT NULL ,#图片标签
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;
ALTER TABLE `clinical_data` ADD INDEX index_maid (`ma_id`);
alter table `clinical_data` add column index_type int(1);

DROP TABLE IF EXISTS `device`; #设备表
create table `device` (
    `id` bigint(20)  not null auto_increment,
    `user_id` bigint(20)  DEFAULT NULL ,#用户id
     `model` VARCHAR(255) not NULL ,#设备型号
   `sn` VARCHAR(255) DEFAULT NULL ,#设备序列号
   `code_Url` VARCHAR(255) DEFAULT NULL ,#设备二维码
   `config` VARCHAR(255) DEFAULT NULL , #产品配置信息
   `life_status` INT(1) DEFAULT NULL ,#产品生命周期
   `type` int(1) DEFAULT NULL ,#设备类型,#1短租2长租
   `produce_date` datetime DEFAULT   NULL ,#生产日期
   `activate_date` datetime DEFAULT   NULL ,#激活日期
    `scrap_date` datetime DEFAULT   NULL ,#报废日期
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;
alter table device add column user_id bigint(20);


DROP TABLE IF EXISTS `device_record`; #设备履历表
create table `device_record` (
    `id` bigint(20)  not null auto_increment,
     `device_id` bigint(20) not NULL ,#设备id
   `patient_id` bigint(20)  not NULL ,#病人id
   `event` VARCHAR(255) DEFAULT NULL , #事件,目前只有绑定和解绑
   `details` VARCHAR(255) DEFAULT NULL , #事件详情
   `modify_time` datetime DEFAULT NULL ,#修改时间
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;




DROP TABLE IF EXISTS `data_dict`; #字典表
create table `data_dict` (
    `id` bigint(20)  not null auto_increment,
     `key` VARCHAR (100) not NULL ,#标识名
   `content` Text  not NULL ,#内容
   `sequencing` bigint(20) DEFAULT NULL , #排序
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

ALTER TABLE `data_dict` ADD UNIQUE (`key`);

INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (1, 'illness_state', '[{"code": 1, "name": "历史遗留"}, {"code": 2, "name": "近期手术"}]', 1);
INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (2, 'nactivity_factor', '[{"code": 1, "name": "长时间固定"},{"code": 2, "name": "关节内源性病变"},
 {"code": 3, "name": "静态生活方式"},{"code": 4, "name": "瘫痪/张力异常/失衡"},{"code": 5, "name": "姿势排列不正"}]', 1);
 INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (3, 'disease_course', '[{"code": 1, "name": "1周"},{"code": 2, "name": "2周内"},
 {"code": 3, "name": "3-12周"},{"code": 4, "name": "12周以上"}]', 1);
 INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (4, 'picture_label', '[{"code": 1, "name": "术前"},{"code": 2, "name": "术后"},
 {"code": 3, "name": "最近一次"},{"code": 4, "name": "最大变化"}]', 1);
 INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (5, 'arthro', '[{"code": 1, "name": "肘关节","ico": "icon_zhou"},{"code": 2, "name": "腕关节","ico": "icon_wan"},
 {"code": 3, "name": "膝关节","ico": "icon_xi"},{"code": 4, "name": "裸关节","ico": "icon_huai"},{"code": 5, "name": "其他","ico": "icon_other"}]', 1);
 INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (6, 'postion', '[{"code": 1, "name": "左"},{"code": 2, "name": "右"}]', 1);
 INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (7, 'direction', '[{"code": 1, "name": "伸展"},{"code": 2, "name": "屈曲"}]', 1);
 INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (8, 'score_change', '[{"code": 1, "name": "注册"},{"code": 2, "name": "兑换"} ,{"code": 3, "name": "锻炼"},{"code": 4, "name": "分享"}]', 1);


  INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (9, 'hospital', '[{"code": 1, "name": "中国医院","group":"Z"},{"code": 2, "name": "美国医院","group":"M"} ,{"code": 3, "name": "非洲医院","group":"F"},{"code": 4, "name": "日本医院","group":"R"},
   {"code": 5, "name": "安徽医院","group":"A"},{"code": 6, "name": "安庆医院","group":"A"},{"code": 7, "name": "巢湖医院","group":"C"},{"code": 8, "name": "淮北医院","group":"H"},{"code": 9, "name": "上海医院","group":"S"},{"code": 10, "name": "新疆医院","group":"X"},{"code": 11, "name": "合肥医院","group":"H"}]', 1);
  INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (10, 'departments', '[{"code": 1, "name": "呼吸内科"},{"code": 2, "name": "肾脏内科"} ,{"code": 3, "name": "眼科"},{"code": 4, "name": "耳科"}]', 1);

   INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (11, 'appraisal ', '[{"code": 1, "name": "维持"},{"code": 2, "name": "改善"} ,{"code": 3, "name": "恶化"},{"code": 4, "name": "脱落"}]', 1);
  INSERT INTO data_dict (id, `key`, content, sequencing)VALUE (12, 'professiona', '[{"code": 1, "name": "助理医师"},{"code": 2, "name": "住院医师"} ,{"code": 3, "name": "主治医师"},{"code": 4, "name": "副主任医师"},{"code": 5, "name": "主任医师"},{"code": 6, "name": "治疗士"},{"code": 7, "name": "治疗师"},{"code": 8, "name": "主管治疗师"},{"code": 9, "name": "副主任治疗师"},{"code": 10, "name": "主任治疗师"}]', 1);




DROP TABLE IF EXISTS `application`; #申请表
create table `application` (
    `id` bigint(20)  not null auto_increment,
     `patient_id` bigint(20) not NULL ,#病人id
   `doctor_id` bigint(20)  not NULL ,#医生id
   `status` int(1) DEFAULT NULL , #0没接收1接受
   `createtime` datetime DEFAULT CURRENT_TIMESTAMP , #排序
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

DROP TABLE IF EXISTS `operation_log`; #日志表
create table `operation_log` (
    `id` bigint(20)  not null auto_increment,
    `user_id` bigint(20) not NULL ,#用户id
   `user_name` varchar(20)  not NULL ,#用户姓名
   `ip_address` varchar(30) DEFAULT NULL , #ip地址
   `event` varchar(30) DEFAULT NULL , #事件
   `status_code` varchar(30) DEFAULT NULL , #状态码
   `request_address` varchar(50) DEFAULT NULL ,#请求地址
   `reponse_content` varchar(2000) DEFAULT NULL , #响应内容
   `createtime` datetime DEFAULT CURRENT_TIMESTAMP , #操作时间
   PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;























