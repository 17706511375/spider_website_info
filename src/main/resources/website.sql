/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2019-09-16 16:45:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for website
-- ----------------------------
DROP TABLE IF EXISTS `website`;
CREATE TABLE `website` (
  `website_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '网站名',
  `website_url` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '网站地址',
  `website_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '网站类型 根据站长之家黄页的分类分的',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `website_weight` int(2) NOT NULL DEFAULT '1' COMMENT '权重',
  `operator` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作人',
  `classfication` int(1) NOT NULL COMMENT '网站的分类：\\n      分为2类：1表示由系统爬虫抓取的 2表示用户管理员自己添加的。\\n',
  `website_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`website_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61341 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='爬取站长之家的网站信息		';
