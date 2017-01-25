/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50153
Source Host           : localhost:3306
Source Database       : resourcemanagementsystem

Target Server Type    : MYSQL
Target Server Version : 50153
File Encoding         : 65001

Date: 2016-12-04 23:28:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `anum` int(11) DEFAULT NULL,
  `aname` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `rnum` int(11) DEFAULT NULL,
  `rname` varchar(255) DEFAULT NULL,
  `rdes` varchar(255) DEFAULT NULL,
  `rlink` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `snum` int(11) DEFAULT NULL,
  `sname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `tasknum` int(11) DEFAULT NULL,
  `taskname` varchar(255) DEFAULT NULL,
  `taskdes` varchar(255) DEFAULT NULL,
  `taskdeadline` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `tnum` int(11) DEFAULT NULL,
  `tname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
