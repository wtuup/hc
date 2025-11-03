/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : homework_manager

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 17/02/2024 16:22:22
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student_file
-- ----------------------------
DROP TABLE IF EXISTS `student_file`;
CREATE TABLE `student_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `student_id` int(0) NULL DEFAULT NULL COMMENT '学生id',
  `dwc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师姓名',
  `user_file_id` int(0) NULL DEFAULT NULL COMMENT '作业id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作业标题',
  `an1` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作答内容',
  `flag` tinyint(1) NULL DEFAULT 0 COMMENT '0未审核，1通过，2不通过',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件链接',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作业内容',
  `score` int(0) NULL DEFAULT NULL COMMENT '分数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生作答' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_file
-- ----------------------------
INSERT INTO `student_file` VALUES (9, '陈小明', 3, '张三', 81, 'Java基础作业一', 'public class EvenNumbers {\r\n    public static void main(String[] args) {\r\n        // 循环遍历1到100的所有数字\r\n        for (int i = 1; i <= 100; i++) {\r\n            // 使用 if 语句判断是否为偶数，如果是偶数则输出\r\n            if (i % 2 == 0) {\r\n                System.out.println(i);\r\n            }\r\n        }\r\n    }\r\n}', 1, '/files/947d70c520354d53ada4ea5af6e06b75.txt', '70b20eba8337558a8564011273ad4914', '答案.txt', '题目：编写一个Java程序，输出从1到100的所有偶数。\r\n\r\n提示：使用循环结构和条件判断来实现输出符合要求的数字。您可以选择使用for循环或者while循环。祝您顺利完成！', 95);
INSERT INTO `student_file` VALUES (10, '陈小明', 3, '张三', 82, 'Java基础作业二', 'public class EvenNumbers {\r\n    public static void main(String[] args) {\r\n        // 循环遍历1到100的所有数字\r\n        for (int i = 1; i <= 100; i++) {\r\n            // 使用 if 语句判断是否为偶数，如果是偶数则输出\r\n            if (i % 2 == 0) {\r\n                System.out.println(i);\r\n            }\r\n        }\r\n    }\r\n}', 0, '/files/947d70c520354d53ada4ea5af6e06b75.txt', '70b20eba8337558a8564011273ad4914', '答案.txt', '题目：编写一个 Java 程序，计算斐波那契数列的前 10 个数并将它们输出。\r\n\r\n提示：斐波那契数列是一个数列，该数列的第一个和第二个数字为 1，从第三个数字开始，每个数字都是前两个数字的和。因此，斐波那契数列的前几个数字依次为：1, 1, 2, 3, 5, 8, 13, 21, 34, 55...', NULL);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 307 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (281, 'home', 'iconfont icon-r-home', 'icon');
INSERT INTO `sys_dict` VALUES (282, 'edit', 'iconfont icon-r-edit', 'icon');
INSERT INTO `sys_dict` VALUES (283, 'add', 'iconfont icon-r-add', 'icon');
INSERT INTO `sys_dict` VALUES (284, 'delete', 'iconfont icon-r-delete', 'icon');
INSERT INTO `sys_dict` VALUES (285, 'find', 'iconfont icon-r-find', 'icon');
INSERT INTO `sys_dict` VALUES (286, 'setting', 'iconfont icon-r-setting', 'icon');
INSERT INTO `sys_dict` VALUES (287, 'building', 'iconfont icon-r-building', 'icon');
INSERT INTO `sys_dict` VALUES (288, 'paper', 'iconfont icon-r-paper', 'icon');
INSERT INTO `sys_dict` VALUES (289, 'list', 'iconfont icon-r-list', 'icon');
INSERT INTO `sys_dict` VALUES (290, 'user1', 'iconfont icon-r-user1', 'icon');
INSERT INTO `sys_dict` VALUES (291, 'user2', 'iconfont icon-r-user2', 'icon');
INSERT INTO `sys_dict` VALUES (292, 'user3', 'iconfont icon-r-user3', 'icon');
INSERT INTO `sys_dict` VALUES (293, 'shield', 'iconfont icon-r-shield', 'icon');
INSERT INTO `sys_dict` VALUES (294, 'mark1', 'iconfont icon-r-mark1', 'icon');
INSERT INTO `sys_dict` VALUES (295, 'mark2', 'iconfont icon-r-mark2', 'icon');
INSERT INTO `sys_dict` VALUES (296, 'mark3', 'iconfont icon-r-mark3', 'icon');
INSERT INTO `sys_dict` VALUES (297, 'love', 'iconfont icon-r-love', 'icon');
INSERT INTO `sys_dict` VALUES (298, 'refresh', 'iconfont icon-r-refresh', 'icon');
INSERT INTO `sys_dict` VALUES (299, 'yes', 'iconfont icon-r-yes', 'icon');
INSERT INTO `sys_dict` VALUES (300, 'no', 'iconfont icon-r-no', 'icon');
INSERT INTO `sys_dict` VALUES (301, 'top', 'iconfont icon-r-top', 'icon');
INSERT INTO `sys_dict` VALUES (302, 'bottom', 'iconfont icon-r-bottom', 'icon');
INSERT INTO `sys_dict` VALUES (303, 'left', 'iconfont icon-r-left', 'icon');
INSERT INTO `sys_dict` VALUES (304, 'right', 'iconfont icon-r-right', 'icon');
INSERT INTO `sys_dict` VALUES (305, 'lock', 'iconfont icon-r-lock', 'icon');
INSERT INTO `sys_dict` VALUES (306, 'team', 'iconfont icon-r-team', 'icon');
INSERT INTO `sys_dict` VALUES (307, 'mark4', 'iconfont icon-r-mark4', 'icon');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '文件大小(kb)',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载链接',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件md5',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用链接',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (15, '07.jpg', 'jpg', 175, '/files/a232fc5cf9d240a5865197806491eb18.jpg', '507704f05fbca53793bce9970b40e6c8', 0, 1);
INSERT INTO `sys_file` VALUES (16, '01.jpg', 'jpg', 146, '/files/4e0f52287cba4425949ca589c8d7175f.jpg', '5c072037e4e9662831fe448e28795770', 0, 1);
INSERT INTO `sys_file` VALUES (17, '04.jpg', 'jpg', 81, '/files/a1c580b87fc9408eac017e6bd3222487.jpg', '6e850237334b9dbc9e869e2fe4dd8822', 0, 1);
INSERT INTO `sys_file` VALUES (18, 'logo2.jpg', 'jpg', 635, '/files/c23815730ec14131b98a3c62c2c794cb.jpg', '96564de9ed610acff96bc60b5d98e375', 0, 1);
INSERT INTO `sys_file` VALUES (19, 'logo3.jpg', 'jpg', 628, '/files/94894dc46bf64a5081dfd1bb5d5916df.jpg', '8df2bb08ffb7a04d1cb8c0bbe91b3022', 0, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `pid` int(0) NULL DEFAULT NULL COMMENT '父级id',
  `page_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面路径',
  `sort_num` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (2, '数据报表', '/dashbord', 'iconfont icon-r-paper', '11', NULL, 'Dashbord', 100);
INSERT INTO `sys_menu` VALUES (4, '系统管理', NULL, 'iconfont icon-r-setting', NULL, NULL, NULL, 9999);
INSERT INTO `sys_menu` VALUES (5, '用户管理', '/user', 'iconfont icon-r-user2', NULL, 4, 'User', 301);
INSERT INTO `sys_menu` VALUES (6, '角色管理', '/role', 'iconfont icon-r-user3', NULL, 4, 'Role', 302);
INSERT INTO `sys_menu` VALUES (7, '菜单管理', '/menu', 'iconfont icon-r-list', NULL, 4, 'Menu', 303);
INSERT INTO `sys_menu` VALUES (8, '文件管理', '/file', 'iconfont icon-r-paper', NULL, 4, 'File', 304);
INSERT INTO `sys_menu` VALUES (10, '主页', '/home', 'iconfont icon-r-home', NULL, NULL, 'Home', 0);
INSERT INTO `sys_menu` VALUES (14, '作业管理', NULL, 'iconfont icon-r-edit', NULL, NULL, NULL, 8888);
INSERT INTO `sys_menu` VALUES (15, '提交作业', '/put', 'iconfont icon-r-top', NULL, 14, 'Put', 1);
INSERT INTO `sys_menu` VALUES (16, '查看作业', '/look', 'iconfont icon-r-find', NULL, 14, 'Look', 2);
INSERT INTO `sys_menu` VALUES (17, '发布作业', '/publish', 'iconfont icon-r-add', NULL, 14, 'Publish', 3);
INSERT INTO `sys_menu` VALUES (18, '批改作业', '/check', 'iconfont icon-r-yes', NULL, 14, 'Check', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `flag` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES (2, '普通用户', '普通用户', 'ROLE_USER');
INSERT INTO `sys_role` VALUES (3, '老师', '老师', 'ROLE_TEACHER');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `menu_id` int(0) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (3, 10);
INSERT INTO `sys_role_menu` VALUES (3, 14);
INSERT INTO `sys_role_menu` VALUES (3, 16);
INSERT INTO `sys_role_menu` VALUES (3, 17);
INSERT INTO `sys_role_menu` VALUES (3, 18);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin@qq.com', '13544444444', '北京', '2024-01-22 21:10:27', '/files/94894dc46bf64a5081dfd1bb5d5916df.jpg', 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (2, 't1', 'e10adc3949ba59abbe56e057f20f883e', '张三', 't1@qq.com', '18977777777', '北京', '2024-02-26 22:10:14', '/files/4e0f52287cba4425949ca589c8d7175f.jpg', 'ROLE_TEACHER');
INSERT INTO `sys_user` VALUES (3, 's1', 'e10adc3949ba59abbe56e057f20f883e', '陈小明', 's1@qq.com', '13644445555', '北京', '2024-02-26 22:10:18', '/files/a1c580b87fc9408eac017e6bd3222487.jpg', 'ROLE_USER');

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dwc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` bigint(0) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '作业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_file
-- ----------------------------
INSERT INTO `user_file` VALUES (81, 'java基础练习1.txt', '张三', 'txt', 0, '/files/dd650039974a4c8fb94e669f46ae2a7e.txt', '677f6ea273537e7af0a9bdf3947162f8', 'Java基础作业一', '题目：编写一个Java程序，输出从1到100的所有偶数。\r\n\r\n提示：使用循环结构和条件判断来实现输出符合要求的数字。您可以选择使用for循环或者while循环。祝您顺利完成！');
INSERT INTO `user_file` VALUES (82, 'java基础练习2.txt', '张三', 'txt', 0, '/files/ac9958ac8fad4db89d7bf62c0ac9c19b.txt', '6bc30573e025204bbb36b2b96198fff1', 'Java基础作业二', '题目：编写一个 Java 程序，计算斐波那契数列的前 10 个数并将它们输出。\r\n\r\n提示：斐波那契数列是一个数列，该数列的第一个和第二个数字为 1，从第三个数字开始，每个数字都是前两个数字的和。因此，斐波那契数列的前几个数字依次为：1, 1, 2, 3, 5, 8, 13, 21, 34, 55...');

SET FOREIGN_KEY_CHECKS = 1;
