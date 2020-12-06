/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : invoicing

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2020-06-08 21:17:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for export
-- ----------------------------
DROP TABLE IF EXISTS `export`;
CREATE TABLE `export` (
  `id` char(19) NOT NULL COMMENT '出货id',
  `goodid` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品id',
  `count` int(10) unsigned NOT NULL COMMENT '进货量',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL COMMENT '更新时间',
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出货表';

-- ----------------------------
-- Records of export
-- ----------------------------
INSERT INTO `export` VALUES ('1263667602220126209', '1263663749101785090', '20', '2020-05-22 11:06:49', '2020-05-22 11:06:49', 'admin');
INSERT INTO `export` VALUES ('1265691135502749698', '1263663666658545666', '10', '2020-05-28 01:07:37', '2020-05-28 01:07:37', 'admin');
INSERT INTO `export` VALUES ('1269515917293850626', '1263663666658545666', '5', '2020-06-07 14:25:56', '2020-06-07 14:25:56', 'admin');

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good` (
  `id` char(19) NOT NULL COMMENT '商品id',
  `unitid` char(19) NOT NULL COMMENT '单位id',
  `name` varchar(50) NOT NULL COMMENT '商品名称',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES ('1263663666658545666', '1263338335754092545', '可乐', '0', '2020-05-22 10:51:11', '2020-05-25 17:27:58');
INSERT INTO `good` VALUES ('1263663749101785090', '1263356944991760385', '雪碧', '0', '2020-05-22 10:51:30', '2020-05-22 10:51:30');
INSERT INTO `good` VALUES ('1263663785277657089', '1263356944991760385', '芬达', '0', '2020-05-22 10:51:39', '2020-05-22 10:51:39');
INSERT INTO `good` VALUES ('1264888658939404289', '1263339639926784002', '棉花糖', '0', '2020-05-25 19:58:51', '2020-05-25 19:58:51');
INSERT INTO `good` VALUES ('1264896884426698754', '1264896883831107586', '十连券', '0', '2020-05-25 20:31:33', '2020-05-25 20:31:33');
INSERT INTO `good` VALUES ('1269531780772290561', '1263338271749013505', '棒棒糖', '0', '2020-06-07 15:28:58', '2020-06-07 15:28:58');

-- ----------------------------
-- Table structure for import
-- ----------------------------
DROP TABLE IF EXISTS `import`;
CREATE TABLE `import` (
  `id` char(19) NOT NULL COMMENT '进货id',
  `goodid` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品id',
  `count` int(10) unsigned NOT NULL COMMENT '进货量',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL COMMENT '更新时间',
  `username` varchar(50) NOT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='进货表';

-- ----------------------------
-- Records of import
-- ----------------------------
INSERT INTO `import` VALUES ('1269520217571135489', '1264888658939404289', '100', '2020-06-07 14:43:01', '2020-06-07 14:43:01', 'admin');
INSERT INTO `import` VALUES ('1269524731141316609', '1263663785277657089', '100', '2020-06-07 15:00:57', '2020-06-07 15:00:57', 'admin');
INSERT INTO `import` VALUES ('1269525107710124033', '1263663666658545666', '50', '2020-06-07 15:02:27', '2020-06-07 15:02:27', 'admin');
INSERT INTO `import` VALUES ('1269665872193077249', '1264896884426698754', '10', '2020-06-08 00:21:48', '2020-06-08 00:21:48', 'admin');

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` char(19) NOT NULL COMMENT '库存id',
  `goodid` char(19) NOT NULL COMMENT '商品id',
  `count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '库存数量',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存表';

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('1263666634518695937', '1263663666658545666', '135', '2020-05-22 11:02:58', '2020-06-07 15:02:27');
INSERT INTO `stock` VALUES ('1263666731570696194', '1263663749101785090', '80', '2020-05-22 11:03:21', '2020-05-22 11:06:49');
INSERT INTO `stock` VALUES ('1269520217776656386', '1264888658939404289', '100', '2020-06-07 14:43:01', '2020-06-07 14:43:01');
INSERT INTO `stock` VALUES ('1269524731887902721', '1263663785277657089', '100', '2020-06-07 15:00:57', '2020-06-07 15:00:57');
INSERT INTO `stock` VALUES ('1269665872625090562', '1264896884426698754', '10', '2020-06-08 00:21:48', '2020-06-08 00:21:48');

-- ----------------------------
-- Table structure for unit
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit` (
  `id` char(19) NOT NULL COMMENT '单位id',
  `name` varchar(50) NOT NULL COMMENT '单位名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='单位表';

-- ----------------------------
-- Records of unit
-- ----------------------------
INSERT INTO `unit` VALUES ('1263338271749013505', '个', '0', '2020-05-21 13:18:10', '2020-05-21 13:18:10');
INSERT INTO `unit` VALUES ('1263338335754092545', '组', '0', '2020-05-21 13:18:26', '2020-05-21 14:37:21');
INSERT INTO `unit` VALUES ('1263339639926784002', '包', '0', '2020-05-21 13:23:37', '2020-05-21 13:23:37');
INSERT INTO `unit` VALUES ('1263356944991760385', '箱', '0', '2020-05-21 14:32:22', '2020-05-21 14:32:22');
INSERT INTO `unit` VALUES ('1264842931521466369', '只', '0', '2020-05-25 16:57:09', '2020-05-25 16:57:09');
INSERT INTO `unit` VALUES ('1264896883831107586', '件', '0', '2020-05-25 20:31:32', '2020-05-25 20:31:32');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` char(19) NOT NULL COMMENT 'id',
  `passwd` varchar(255) DEFAULT NULL COMMENT '密码',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '111111', 'admin', '555555', '0', '2020-05-17 20:49:38', '2020-05-21 09:14:31');
INSERT INTO `user` VALUES ('1262779727609925633', '123456', 'lisi', '123456', '0', '2020-05-20 00:18:43', '2020-06-08 16:48:48');
INSERT INTO `user` VALUES ('1263272487827820545', null, '测试', '666666', '0', '2020-05-21 08:56:46', '2020-05-21 08:56:46');
INSERT INTO `user` VALUES ('1263274213771333634', null, '测试2', '456789', '1', '2020-05-21 09:03:38', '2020-05-21 09:03:38');
INSERT INTO `user` VALUES ('1263274489848811522', null, '测试添加', '854613', '0', '2020-05-21 09:04:44', '2020-05-21 09:04:44');
INSERT INTO `user` VALUES ('1263277187000193025', null, 'wangwu', '789456', '0', '2020-05-21 09:15:27', '2020-05-21 09:15:27');
INSERT INTO `user` VALUES ('1263277225130610689', null, 'zhaoliu', '486131', '0', '2020-05-21 09:15:36', '2020-05-21 09:15:36');
INSERT INTO `user` VALUES ('2', '111111', 'zhangsan', '013053', '0', '2020-05-20 00:18:15', '2020-05-20 00:18:15');
