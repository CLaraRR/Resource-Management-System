/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50153
Source Host           : localhost:3306
Source Database       : resourcemanagementsystem

Target Server Type    : MYSQL
Target Server Version : 50153
File Encoding         : 65001

Date: 2017-02-20 23:41:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for table_report
-- ----------------------------
DROP TABLE IF EXISTS `table_report`;
CREATE TABLE `table_report` (
  `reportnum` int(11) NOT NULL AUTO_INCREMENT,
  `rnum` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `reporter` varchar(255) DEFAULT NULL,
  `reportdate` datetime DEFAULT NULL,
  `pass` int(1) DEFAULT NULL,
  PRIMARY KEY (`reportnum`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_report
-- ----------------------------
INSERT INTO `table_report` VALUES ('1', '2', '不适用', '20142602', '2016-12-25 00:00:00', '0');
INSERT INTO `table_report` VALUES ('2', '8', '不好用', '20142602', '2016-12-25 00:00:00', '1');
INSERT INTO `table_report` VALUES ('3', '1', '不好用', '20142602', '2016-12-25 00:00:00', '1');

-- ----------------------------
-- Table structure for table_resource
-- ----------------------------
DROP TABLE IF EXISTS `table_resource`;
CREATE TABLE `table_resource` (
  `rnum` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(255) DEFAULT NULL,
  `rdes` varchar(255) DEFAULT NULL,
  `rdate` datetime DEFAULT NULL,
  `rauthor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rnum`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_resource
-- ----------------------------
INSERT INTO `table_resource` VALUES ('1', 'Ubuntu15.1', 'Linux操作系统', '2016-12-17 00:00:00', '20140003');
INSERT INTO `table_resource` VALUES ('2', 'MySQL', '关系型数据库', '2016-12-01 00:00:00', '20110002');
INSERT INTO `table_resource` VALUES ('7', 'MAYA', '3D模型编辑器', '2016-12-17 00:00:00', '20142602');
INSERT INTO `table_resource` VALUES ('8', 'eclipse', 'IDE', '2016-12-22 00:00:00', '20110003');
INSERT INTO `table_resource` VALUES ('18', 'notepad', '文本编辑器', '2016-12-12 00:00:00', '20142602');
INSERT INTO `table_resource` VALUES ('27', 'navicat', 'shujukuguanligongjju', '2016-12-29 17:42:14', '20142602');

-- ----------------------------
-- Table structure for table_task
-- ----------------------------
DROP TABLE IF EXISTS `table_task`;
CREATE TABLE `table_task` (
  `tasknum` int(11) NOT NULL AUTO_INCREMENT,
  `taskname` varchar(255) DEFAULT NULL,
  `taskdes` varchar(255) DEFAULT NULL,
  `taskdeadline` varchar(255) DEFAULT NULL,
  `taskauthor` varchar(255) DEFAULT NULL,
  `completenum` int(11) DEFAULT NULL,
  PRIMARY KEY (`tasknum`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_task
-- ----------------------------
INSERT INTO `table_task` VALUES ('26', '操作系统作业2', '书本50页习题', '2016.12.25', '20110002', null);
INSERT INTO `table_task` VALUES ('25', '计图实验三', '三维旋转', '2016.12.1', '20110003', null);
INSERT INTO `table_task` VALUES ('22', '操作系统实验二', '多线程与多进程', '2016.11.12', '20110002', null);
INSERT INTO `table_task` VALUES ('21', '计图实验2', 'bresenham算法实现', '2016.12.12', '20110003', null);

-- ----------------------------
-- Table structure for table_user
-- ----------------------------
DROP TABLE IF EXISTS `table_user`;
CREATE TABLE `table_user` (
  `username` varchar(255) DEFAULT NULL,
  `usernum` varchar(11) NOT NULL,
  `userType` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  PRIMARY KEY (`usernum`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_user
-- ----------------------------
INSERT INTO `table_user` VALUES ('admin', '20160001', '2', '123456', 'lihua', '0');
INSERT INTO `table_user` VALUES ('sss', '20142602', '0', '111111', 'ningrun', '0');
INSERT INTO `table_user` VALUES ('tttww', '20110002', '1', '222222', 'xiaoagungyi', '0');
INSERT INTO `table_user` VALUES ('ss33', '20142603', '0', '888888', 'ssreal', '1');
INSERT INTO `table_user` VALUES ('radmin', '20160002', '2', '888888', 'rrr', '0');
INSERT INTO `table_user` VALUES ('小可', '20152204', '0', '888888', '蒋晓可', '0');
INSERT INTO `table_user` VALUES ('ttt2', '20110003', '1', '333333', 'ttreal', '0');
INSERT INTO `table_user` VALUES ('润润', '20132608', '0', '888888', '宁润', '0');
INSERT INTO `table_user` VALUES ('admin12', '20140003', '2', '888888', 'admin12real', '1');
