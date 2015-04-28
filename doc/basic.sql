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


-- 导出  表 basic.sys_button 结构
DROP TABLE IF EXISTS `sys_button`;
CREATE TABLE IF NOT EXISTS `sys_button` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '功能Id',
  `menuId` int(3) NOT NULL COMMENT '菜单Id',
  `name` varchar(32) NOT NULL COMMENT '显示名称',
  `value` varchar(32) NOT NULL COMMENT '功能值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='功能按钮';

-- 正在导出表  basic.sys_button 的数据：~5 rows (大约)
DELETE FROM `sys_button`;
/*!40000 ALTER TABLE `sys_button` DISABLE KEYS */;
INSERT INTO `sys_button` (`id`, `menuId`, `name`, `value`) VALUES
	(20, 14, '添加', 'MenuController.addview'),
	(21, 14, '修改', 'MenuController.editview'),
	(22, 14, '修改状态', 'MenuController.editstatus'),
	(23, 14, '删除', 'MenuController.delview'),
	(24, 13, '删除', 'UserController.delete'),
	(25, 13, '添加', 'UserController.addview'),
	(26, 13, '修改', 'UserController.editview'),
	(27, 13, '修改状态', 'UserController.upstatus'),
	(28, 12, 'RoleController.permissionview', 'RoleController.permissionview'),
	(29, 12, '添加', 'RoleController.addview'),
	(30, 12, '修改', 'RoleController.editview'),
	(31, 12, '修改状态', 'RoleController.editstatus'),
	(32, 12, '删除', 'RoleController.delview');
/*!40000 ALTER TABLE `sys_button` ENABLE KEYS */;


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
  `userId` bigint(11) NOT NULL COMMENT '用户ID',
  `loginTime` bigint(13) NOT NULL COMMENT '登录时间',
  `logoutTime` bigint(13) DEFAULT NULL COMMENT '退出时间',
  `logIp` varchar(32) NOT NULL COMMENT '登录Ip',
  `lastlogTime` bigint(13) DEFAULT NULL COMMENT '上次登录时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态;1在线0下线',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

-- 正在导出表  basic.sys_login_log 的数据：~13 rows (大约)
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
	(15, -1, 1430105249463, NULL, '0:0:0:0:0:0:0:1', 1429784229250, 1),
	(16, -1, 1430191392739, NULL, '0:0:0:0:0:0:0:1', 1430105249463, 1),
	(17, -1, 1430191634606, NULL, '0:0:0:0:0:0:0:1', 1430191392739, 1),
	(18, -1, 1430192084427, NULL, '0:0:0:0:0:0:0:1', 1430191634606, 1),
	(19, -1, 1430196863494, NULL, '0:0:0:0:0:0:0:1', 1430192084427, 1),
	(20, -1, 1430196920733, NULL, '0:0:0:0:0:0:0:1', 1430196863494, 1),
	(21, -1, 1430198433233, NULL, '0:0:0:0:0:0:0:1', 1430196920733, 1),
	(22, -1, 1430198570384, NULL, '0:0:0:0:0:0:0:1', 1430198433233, 1),
	(23, -1, 1430198800545, NULL, '0:0:0:0:0:0:0:1', 1430198570384, 1),
	(24, -1, 1430198896866, NULL, '0:0:0:0:0:0:0:1', 1430198800545, 1),
	(25, -1, 1430199285822, NULL, '0:0:0:0:0:0:0:1', 1430198896866, 1),
	(26, -1, 1430199423581, NULL, '0:0:0:0:0:0:0:1', 1430199285822, 1),
	(27, -1, 1430199841740, NULL, '0:0:0:0:0:0:0:1', 1430199423581, 1),
	(28, -1, 1430199895707, NULL, '0:0:0:0:0:0:0:1', 1430199841740, 1);
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;


-- 导出  表 basic.sys_menu 结构
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `type` int(4) DEFAULT NULL COMMENT '类型',
  `href` varchar(64) DEFAULT NULL COMMENT 'url地址',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `target` varchar(20) DEFAULT NULL COMMENT '目标（mainFrame、 _blank、_self、_parent、_top）',
  `rel` varchar(128) DEFAULT NULL COMMENT 'rel',
  `parentId` int(3) NOT NULL DEFAULT '-1' COMMENT '父级',
  `createTime` bigint(13) NOT NULL COMMENT '创建时间',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `sortNo` int(8) DEFAULT NULL COMMENT '排序号',
  `permission` varchar(64) DEFAULT NULL COMMENT '菜单权限值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 正在导出表  basic.sys_menu 的数据：~8 rows (大约)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `name`, `type`, `href`, `icon`, `description`, `target`, `rel`, `parentId`, `createTime`, `isUsed`, `sortNo`, `permission`) VALUES
	(11, '系统管理', 0, '', '', '', '', '', -1, 1407140785011, 1, 99, '1'),
	(12, '角色列表', 0, '/sys/role/', 'am-icon-file        ', '角色列表', 'navTab', 'user_role_listview', 11, 1407141914856, 0, 2, 'RoleController'),
	(13, '用户列表', 0, '/sys/user/', 'am-icon-hand-o-up    ', '用户列表', 'navTab', 'user_info_listview', 11, 1407141964456, 1, 1, 'UserController'),
	(14, '菜单列表', 0, '/sys/menu/', 'am-icon-file ', '菜单列表', 'navTab', 'order_info_listview', 11, 1407142023060, 1, 3, 'MenuController'),
	(15, '日志管理', 0, '', '', '', '', '', -1, 1409642077554, 1, 100, '1'),
	(16, '操作日志', 0, '/sys/log/button', ' ', NULL, 'navTab', 'log_node_listview', 15, 1409642132551, 1, 1, 'add_edit_delete'),
	(17, '登录日志', NULL, '/sys/log/login', ' ', '登录日志', 'navTab', 'log_login_listview', 15, 1409648279391, 1, 1, 'add_edit_delete_save'),
	(18, '123', NULL, '123', 'am-icon-file ', '123', NULL, NULL, -1, 1429772404333, 1, 132, NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;


-- 导出  表 basic.sys_role 结构
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleCode` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `createTime` bigint(13) NOT NULL COMMENT '创建时间',
  `parentId` int(4) NOT NULL COMMENT '父级角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  basic.sys_role 的数据：~7 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `roleCode`, `roleName`, `isUsed`, `createTime`, `parentId`) VALUES
	(1, 'root', 'root', 1, 1429680662074, -1),
	(2, 'admin', 'admin', 0, 1429680662074, 1),
	(4, '13', '13', 1, 1429681603916, -1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 basic.sys_role_permission 结构
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` int(4) NOT NULL COMMENT '角色Id',
  `permission` varchar(64) NOT NULL COMMENT '权限值（菜单和功能）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- 正在导出表  basic.sys_role_permission 的数据：~8 rows (大约)
DELETE FROM `sys_role_permission`;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` (`id`, `roleId`, `permission`) VALUES
	(16, 1, '1'),
	(17, 1, 'RoleController'),
	(18, 1, 'RoleController.permissionview'),
	(19, 1, 'RoleController.addview'),
	(20, 1, 'RoleController.editview'),
	(21, 1, 'RoleController.editstatus'),
	(22, 1, 'RoleController.delview'),
	(23, 1, 'UserController'),
	(24, 1, 'UserController.delete'),
	(25, 1, 'UserController.addview'),
	(26, 1, 'UserController.editview'),
	(27, 1, 'UserController.upstatus'),
	(28, 1, 'MenuController'),
	(29, 1, 'MenuController.addview'),
	(30, 1, 'MenuController.editview'),
	(31, 1, 'MenuController.editstatus'),
	(32, 1, 'MenuController.delview');
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
	(12, 1, 'user03', '123456', 1428396925659, 1, NULL, '', 15826008617, NULL, '', '', 9, '0:0:0:0:0:0:0:1', 9, 1428397051036, 1, 1, 1),
	(36, 4, '45', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(37, 4, '46', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(38, 4, '47', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
