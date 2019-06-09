/*
 Navicat Premium Data Transfer

 Source Server         : zwz
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 27/05/2019 07:21:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leaveapply
-- ----------------------------
DROP TABLE IF EXISTS `leaveapply`;
CREATE TABLE `leaveapply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_instance_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `start_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `end_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `leave_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reason` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `apply_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reality_start_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reality_end_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leaveapply
-- ----------------------------
INSERT INTO `leaveapply` VALUES (1, '5001', 'WANG', '2019-05-23', '2019-05-23', '事假', '1', 'Thu May 23 14:04:41 CST 2019', NULL, NULL);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  UNIQUE INDEX `permissionname_UNIQUE`(`permissionname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (2, '人事审批');
INSERT INTO `permission` VALUES (9, '出纳付款');
INSERT INTO `permission` VALUES (8, '总经理审批');
INSERT INTO `permission` VALUES (3, '财务审批');
INSERT INTO `permission` VALUES (1, '部门领导审批');
INSERT INTO `permission` VALUES (15, '采购审批');

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemlist` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total` float NOT NULL,
  `applytime` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `applyer` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase
-- ----------------------------
INSERT INTO `purchase` VALUES (1, '1							', 1, '2019-05-23 16:01:54.332', 'WANG');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  UNIQUE INDEX `rolename_UNIQUE`(`rolename`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (9, '人事');
INSERT INTO `role` VALUES (10, '出纳员');
INSERT INTO `role` VALUES (11, '总经理');
INSERT INTO `role` VALUES (16, '财务管理员');
INSERT INTO `role` VALUES (1, '超级管理员');
INSERT INTO `role` VALUES (2, '部门经理');
INSERT INTO `role` VALUES (13, '采购经理');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `rpid` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `permissionid` int(11) NOT NULL,
  PRIMARY KEY (`rpid`) USING BTREE,
  INDEX `a_idx`(`roleid`) USING BTREE,
  INDEX `b_idx`(`permissionid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (28, 10, 9);
INSERT INTO `role_permission` VALUES (34, 11, 2);
INSERT INTO `role_permission` VALUES (35, 11, 8);
INSERT INTO `role_permission` VALUES (36, 11, 1);
INSERT INTO `role_permission` VALUES (37, 13, 15);
INSERT INTO `role_permission` VALUES (39, 16, 3);
INSERT INTO `role_permission` VALUES (49, 9, 2);
INSERT INTO `role_permission` VALUES (50, 2, 1);
INSERT INTO `role_permission` VALUES (51, 1, 2);
INSERT INTO `role_permission` VALUES (52, 1, 9);
INSERT INTO `role_permission` VALUES (53, 1, 8);
INSERT INTO `role_permission` VALUES (54, 1, 3);
INSERT INTO `role_permission` VALUES (55, 1, 1);
INSERT INTO `role_permission` VALUES (56, 1, 15);

-- ----------------------------
-- Table structure for tb_daily
-- ----------------------------
DROP TABLE IF EXISTS `tb_daily`;
CREATE TABLE `tb_daily`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `date` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日期',
  `daily_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '工作内容',
  `plan` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '明日计划',
  `feedback` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '反馈',
  `create_time` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `modify_time` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_daily
-- ----------------------------
INSERT INTO `tb_daily` VALUES (1, 35, '2019-05-19', 'test', 'test', 'test', '20190519211535', '20190519211535');
INSERT INTO `tb_daily` VALUES (2, 35, '2019-05-18', 'e', 'e', 'e', '20190519215742', '20190519215742');
INSERT INTO `tb_daily` VALUES (3, 35, '2019-05-17', '1', '1', '1', '20190519220142', '20190519220142');
INSERT INTO `tb_daily` VALUES (4, 35, '2019-05-16', '2', '2', '2', '20190519220152', '20190519220152');
INSERT INTO `tb_daily` VALUES (5, 35, '2019-05-15', '3', '3', '3', '20190519220159', '20190519220159');
INSERT INTO `tb_daily` VALUES (6, 35, '2019-05-14', '4', '4', '4', '20190519220212', '20190519220212');
INSERT INTO `tb_daily` VALUES (7, 35, '2019-05-13', '5', '5', '5', '20190519220223', '20190519220223');
INSERT INTO `tb_daily` VALUES (8, 35, '2019-05-12', '6', '6', '6', '20190519220234', '20190519220234');
INSERT INTO `tb_daily` VALUES (9, 35, '2019-05-11', '6', '6', '6', '20190519220243', '20190519220243');
INSERT INTO `tb_daily` VALUES (10, 35, '2019-05-10', '7', '7', '7', '20190519220248', '20190519220248');
INSERT INTO `tb_daily` VALUES (11, 35, '2019-05-09', '8', '8', '8', '20190519220256', '20190519220256');

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id（0为顶级菜单）',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单地址',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `state` int(2) DEFAULT NULL COMMENT '状态（0，正常；1，禁用）',
  `create_time` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `modify_time` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, 0, '系统管理', '', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (2, 1, '用户管理', 'useradmin', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (3, 1, '角色管理', 'roleadmin', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (4, 1, '审批管理', 'permissionadmin', 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (5, 0, '人事管理', '', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (6, 5, '我的申请', 'leave/my_leave', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (7, 5, '申请审核', 'leave/to_my_audit', 2, 1, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (8, 5, '申请明细', NULL, 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (9, 5, '统计图表', NULL, 4, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (10, 0, '日报管理', '', 4, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (11, 10, '我的日报', 'daily/my_daily', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (12, 10, '新增日报', 'daily/to_add_daily', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (13, 10, '员工日报', 'daily/all_daily', 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (14, 10, '统计图表', NULL, 4, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (15, 5, '部门审核', 'deptleaderaudit', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (16, 5, '人事审核', 'hraudit', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (17, 0, '采购管理', '', 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (18, 17, '我的申请', 'mypurchase', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (19, 17, '采购经理审核', 'purchasemanager', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (20, 17, '财务审核', 'finance', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (21, 17, '总经理审核', 'manager', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (22, 17, '出纳付款', 'pay', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (23, 17, '收货确认', 'receiveitem', 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (24, 1, '菜单管理', 'system/to_menu_mange', 4, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (25, 0, '考勤管理', NULL, 5, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (26, 0, '合同管理', NULL, 6, 0, '201905202250', '201905202250');

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '权限id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
INSERT INTO `tb_role_menu` VALUES (27, 9, 1);
INSERT INTO `tb_role_menu` VALUES (28, 9, 2);
INSERT INTO `tb_role_menu` VALUES (29, 9, 3);
INSERT INTO `tb_role_menu` VALUES (30, 9, 4);
INSERT INTO `tb_role_menu` VALUES (31, 9, 24);
INSERT INTO `tb_role_menu` VALUES (32, 1, 1);
INSERT INTO `tb_role_menu` VALUES (33, 1, 2);
INSERT INTO `tb_role_menu` VALUES (34, 1, 3);
INSERT INTO `tb_role_menu` VALUES (35, 1, 4);
INSERT INTO `tb_role_menu` VALUES (36, 1, 5);
INSERT INTO `tb_role_menu` VALUES (37, 1, 6);
INSERT INTO `tb_role_menu` VALUES (38, 1, 7);
INSERT INTO `tb_role_menu` VALUES (39, 1, 8);
INSERT INTO `tb_role_menu` VALUES (40, 1, 9);
INSERT INTO `tb_role_menu` VALUES (41, 1, 10);
INSERT INTO `tb_role_menu` VALUES (42, 1, 11);
INSERT INTO `tb_role_menu` VALUES (43, 1, 12);
INSERT INTO `tb_role_menu` VALUES (44, 1, 13);
INSERT INTO `tb_role_menu` VALUES (45, 1, 14);
INSERT INTO `tb_role_menu` VALUES (46, 1, 15);
INSERT INTO `tb_role_menu` VALUES (47, 1, 16);
INSERT INTO `tb_role_menu` VALUES (48, 1, 17);
INSERT INTO `tb_role_menu` VALUES (49, 1, 18);
INSERT INTO `tb_role_menu` VALUES (50, 1, 19);
INSERT INTO `tb_role_menu` VALUES (51, 1, 20);
INSERT INTO `tb_role_menu` VALUES (52, 1, 21);
INSERT INTO `tb_role_menu` VALUES (53, 1, 22);
INSERT INTO `tb_role_menu` VALUES (54, 1, 23);
INSERT INTO `tb_role_menu` VALUES (55, 1, 24);
INSERT INTO `tb_role_menu` VALUES (56, 1, 25);
INSERT INTO `tb_role_menu` VALUES (57, 1, 26);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `tel` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '1234', '1', 1);
INSERT INTO `user` VALUES (31, 'xiaomi', '1234', '110', 20);
INSERT INTO `user` VALUES (33, 'jon', '1234', '123', 23);
INSERT INTO `user` VALUES (34, 'xiaocai', '1234', '111', 32);
INSERT INTO `user` VALUES (35, 'WANG', '1234', '222', 33);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `urid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`urid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (48, 33, 2);
INSERT INTO `user_role` VALUES (82, 31, 10);
INSERT INTO `user_role` VALUES (83, 31, 16);
INSERT INTO `user_role` VALUES (84, 31, 2);
INSERT INTO `user_role` VALUES (85, 31, 13);
INSERT INTO `user_role` VALUES (86, 35, 11);
INSERT INTO `user_role` VALUES (87, 35, 2);
INSERT INTO `user_role` VALUES (92, 34, 2);

SET FOREIGN_KEY_CHECKS = 1;
