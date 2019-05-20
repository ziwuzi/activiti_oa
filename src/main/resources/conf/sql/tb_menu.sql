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

 Date: 20/05/2019 23:02:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, 0, '系统管理', '', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (2, 1, '用户管理', 'useradmin', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (3, 1, '角色管理', 'roleadmin', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (4, 1, '权限管理', 'permissionadmin', 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (5, 0, '人事管理', '', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (6, 5, '我的申请', NULL, 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (7, 5, '申请审核', NULL, 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (8, 5, '申请明细', NULL, 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (9, 5, '统计图表', NULL, 4, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (10, 0, '日报管理', '', 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (11, 10, '我的日报', 'daily/my_daily', 1, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (12, 10, '新增日报', 'daily/to_add_daily', 2, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (13, 10, '员工日报', 'daily/all_daily', 3, 0, '201905202250', '201905202250');
INSERT INTO `tb_menu` VALUES (14, 10, '统计图表', NULL, 4, 0, '201905202250', '201905202250');

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '权限id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
