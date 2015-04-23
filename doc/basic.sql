-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.25 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 basic 的数据库结构
DROP DATABASE IF EXISTS `basic`;
CREATE DATABASE IF NOT EXISTS `basic` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `basic`;


-- 导出  表 basic.sys_button_log 结构
DROP TABLE IF EXISTS `sys_button_log`;
CREATE TABLE IF NOT EXISTS `sys_button_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '操作日志ID',
  `userId` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `methodClass` varchar(255) DEFAULT NULL COMMENT '操作类',
  `methodName` varchar(128) DEFAULT NULL COMMENT '操作方法名称',
  `methodPath` varchar(511) DEFAULT NULL COMMENT '请求方法地址',
  `methodParam` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `operaTime` bigint(13) NOT NULL COMMENT '操作时间',
  `logIp` varchar(31) DEFAULT NULL COMMENT '操作Ip',
  `logDesc` varchar(1000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- 正在导出表  basic.sys_button_log 的数据：~0 rows (大约)
DELETE FROM `sys_button_log`;
/*!40000 ALTER TABLE `sys_button_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_button_log` ENABLE KEYS */;


-- 导出  表 basic.sys_login_log 结构
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE IF NOT EXISTS `sys_login_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `userId` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `loginTime` bigint(13) DEFAULT NULL COMMENT '登录时间',
  `logoutTime` bigint(13) DEFAULT NULL COMMENT '退出时间',
  `logIp` varchar(31) DEFAULT NULL COMMENT '登录Ip',
  `lastlogTime` bigint(13) DEFAULT NULL COMMENT '上次登录时间',
  `status` int(1) DEFAULT NULL COMMENT '状态;1在线0下线',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

-- 正在导出表  basic.sys_login_log 的数据：~9 rows (大约)
DELETE FROM `sys_login_log`;
/*!40000 ALTER TABLE `sys_login_log` DISABLE KEYS */;
INSERT INTO `sys_login_log` (`id`, `userId`, `loginTime`, `logoutTime`, `logIp`, `lastlogTime`, `status`) VALUES
	(7, -1, 1429679906717, NULL, '0:0:0:0:0:0:0:1', 0, 1),
	(9, -1, 1429681428979, NULL, '0:0:0:0:0:0:0:1', 1429679906717, 1),
	(10, -1, 1429683799592, NULL, '0:0:0:0:0:0:0:1', 1429681428979, 1),
	(11, -1, 1429758893362, NULL, '0:0:0:0:0:0:0:1', 1429683799592, 1),
	(12, -1, 1429769367034, NULL, '0:0:0:0:0:0:0:1', 1429758893362, 1),
	(13, -1, 1429777412607, NULL, '0:0:0:0:0:0:0:1', 1429769367034, 1),
	(14, -1, 1429784229250, NULL, '0:0:0:0:0:0:0:1', 1429777412607, 1),
	(15, -1, 1429793543573, NULL, '0:0:0:0:0:0:0:1', 1429784229250, 1),
	(16, -1, 1429795257817, NULL, '0:0:0:0:0:0:0:1', 1429793543573, 1),
	(17, -1, 1429795496867, NULL, '0:0:0:0:0:0:0:1', 1429795257817, 1),
	(18, -1, 1429795511540, NULL, '0:0:0:0:0:0:0:1', 1429795496867, 1),
	(19, -1, 1429795526930, NULL, '0:0:0:0:0:0:0:1', 1429795511540, 1),
	(20, -1, 1429795568699, NULL, '0:0:0:0:0:0:0:1', 1429795526930, 1),
	(21, -1, 1429795761712, NULL, '0:0:0:0:0:0:0:1', 1429795568699, 1);
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;


-- 导出  表 basic.sys_menu 结构
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `type` int(4) DEFAULT NULL COMMENT '类型',
  `href` varchar(64) DEFAULT NULL COMMENT 'url地址',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `target` varchar(20) DEFAULT NULL COMMENT '目标（mainFrame、 _blank、_self、_parent、_top）',
  `rel` varchar(128) DEFAULT NULL COMMENT 'rel',
  `parentId` int(3) DEFAULT NULL COMMENT '父级',
  `createTime` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `sortNo` int(8) DEFAULT NULL COMMENT '排序号',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 正在导出表  basic.sys_menu 的数据：~8 rows (大约)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `name`, `type`, `href`, `icon`, `description`, `target`, `rel`, `parentId`, `createTime`, `isUsed`, `sortNo`, `permission`) VALUES
	(11, '系统管理', 0, '', '', '', '', '', -1, 1407140785011, 1, 99, '1'),
	(12, '角色列表', 0, '/sys/role/', 'am-icon-file ', '角色列表', 'navTab', 'user_role_listview', 11, 1407141914856, 1, 1, 'add_edit_delete'),
	(13, '用户列表', 0, '/sys/user/', NULL, '用户列表', 'navTab', 'user_info_listview', 11, 1407141964456, 1, 2, 'add_edit_delete'),
	(14, '菜单列表', 0, '/sys/menu/', NULL, '菜单列表', 'navTab', 'order_info_listview', 11, 1407142023060, 1, 3, 'MenuController_MenuController.addview_MenuController.editview_MenuController.editstatus_MenuController.delview'),
	(15, '日志管理', 0, '', '', '', '', '', -1, 1409642077554, 1, 100, '1'),
	(16, '操作日志', 0, '/sys/log/button', ' ', NULL, 'navTab', 'log_node_listview', 15, 1409642132551, 1, 1, 'add_edit_delete'),
	(17, '登录日志', NULL, '/sys/log/login', ' ', '登录日志', 'navTab', 'log_login_listview', 15, 1409648279391, 1, 1, 'add_edit_delete_save'),
	(18, '123', NULL, '123', 'am-icon-file ', '123', NULL, NULL, -1, 1429772404333, 1, 132, NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;


-- 导出  表 basic.sys_permission 结构
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '权限管理表ID',
  `permission` varchar(64) DEFAULT NULL COMMENT '权限值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8 COMMENT='权限管理表';

-- 正在导出表  basic.sys_permission 的数据：~18 rows (大约)
DELETE FROM `sys_permission`;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` (`id`, `permission`) VALUES
	(19, 'add_edit_delete'),
	(20, 'add_edit_delete'),
	(22, 'add_edit_delete'),
	(23, 'add_edit_delete'),
	(132, NULL),
	(133, 'add_edit_delete'),
	(134, 'add_edit_delete'),
	(135, 'add_edit_delete'),
	(136, NULL),
	(137, 'com.twosnail.basic.constant.permethod.role.LogPermission'),
	(138, 'com.twosnail.basic.constant.permethod.role.LoginPermission'),
	(139, NULL),
	(140, 'addview'),
	(141, 'com.twosnail.basic.constant.permethod.role.UserPermission'),
	(142, 'com.twosnail.basic.constant.permethod.role.MenuPermission'),
	(143, NULL),
	(144, 'com.twosnail.basic.constant.permethod.role.LogPermission'),
	(145, 'com.twosnail.basic.constant.permethod.role.LoginPermission');
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;


-- 导出  表 basic.sys_role 结构
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleCode` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `roleName` varchar(64) NOT NULL COMMENT '角色名称',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `createTime` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `parentId` int(4) NOT NULL COMMENT '父级角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  basic.sys_role 的数据：~7 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `roleCode`, `roleName`, `isUsed`, `createTime`, `parentId`) VALUES
	(1, 'root', 'root', 1, NULL, -1),
	(2, 'admin', 'admin', 1, NULL, 1),
	(3, '12', '12', 1, 1429680662074, -1),
	(4, '13', '13', 1, 1429681603916, -1),
	(5, '14', '14', 1, 1429682907199, -1),
	(6, '5', '15', 1, 1429683810758, -1),
	(7, '16', '16', 1, 1429683814657, -1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 basic.sys_role_permission 结构
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `roleId` int(8) NOT NULL,
  `permission` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- 正在导出表  basic.sys_role_permission 的数据：~1 rows (大约)
DELETE FROM `sys_role_permission`;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` (`id`, `roleId`, `permission`) VALUES
	(1, 1, '140'),
	(3, 2, 'MenuController'),
	(4, 2, 'MenuController.addview'),
	(5, 2, 'MenuController.addview'),
	(6, 2, 'MenuController.addview'),
	(7, 2, 'MenuController.addview');
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;


-- 导出  表 basic.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `roleId` int(8) DEFAULT NULL,
  `userName` varchar(64) NOT NULL COMMENT '用户名称',
  `passWord` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `createTime` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `sex` int(1) DEFAULT '1' COMMENT '性别;0女1男',
  `birthday` varchar(16) DEFAULT NULL COMMENT '生日',
  `idCard` varchar(32) DEFAULT NULL COMMENT '身份证',
  `mobile` bigint(11) DEFAULT NULL COMMENT '手机',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮件',
  `addr` varchar(128) DEFAULT NULL COMMENT '地址',
  `createId` bigint(11) DEFAULT NULL COMMENT '创建者ID',
  `createIp` varchar(32) DEFAULT NULL COMMENT '创建IP',
  `operateId` bigint(11) DEFAULT NULL COMMENT '操作人',
  `opetateTime` bigint(13) DEFAULT NULL COMMENT '操作时间',
  `isDefPassWord` int(1) DEFAULT '0' COMMENT '是否默认0是1否',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `sortNo` int(8) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  basic.sys_user 的数据：~19 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `roleId`, `userName`, `passWord`, `createTime`, `sex`, `birthday`, `idCard`, `mobile`, `phone`, `email`, `addr`, `createId`, `createIp`, `operateId`, `opetateTime`, `isDefPassWord`, `isUsed`, `sortNo`) VALUES
	(9, 1, 'root', '14e1b600b1fd579f47433b88e8d85291', 1406691374696, 1, NULL, NULL, 0, NULL, NULL, NULL, -1, '0:0:0:0:0:0:0:1', 9, 1429494461796, 1, 1, 1),
	(10, 2, 'admin', '14e1b600b1fd579f47433b88e8d85291', 1406691444637, 1, NULL, '', 15826008618, NULL, '', '', -1, '0:0:0:0:0:0:0:1', 9, 1428396852183, 1, 1, 1),
	(11, 4, 'user01', '123456', 1428391501309, 0, '2015-01-02', '50023519941002927X', 15826008619, NULL, 'user01@qq.com', '重庆市云阳县', 9, '0:0:0:0:0:0:0:1', 9, 1428484824882, 1, 0, 15),
	(12, 1, 'user03', '123456', 1428396925659, 1, NULL, '', 15826008617, NULL, '', '', 9, '0:0:0:0:0:0:0:1', 9, 1428397051036, 1, 1, 1),
	(23, 4, '31', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(25, 4, '33', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(26, 4, '34', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(27, 4, '35', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(28, 4, '36', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(29, 4, '37', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(30, 4, '38', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(31, 4, '40', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(32, 4, '41', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(33, 4, '42', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(34, 4, '43', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(35, 4, '44', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(36, 4, '45', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(37, 4, '46', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(38, 4, '47', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
