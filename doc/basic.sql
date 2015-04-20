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

-- 导出 twosnail_basic 的数据库结构
DROP DATABASE IF EXISTS `twosnail_basic`;
CREATE DATABASE IF NOT EXISTS `twosnail_basic` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `twosnail_basic`;


-- 导出  表 twosnail_basic.sysbuttonloghis 结构
CREATE TABLE IF NOT EXISTS `sysbuttonloghis` (
  `logId` bigint(13) NOT NULL AUTO_INCREMENT COMMENT '操作日志ID',
  `userId` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `methodClass` varchar(255) DEFAULT NULL COMMENT '操作类',
  `methodName` varchar(128) DEFAULT NULL COMMENT '操作方法名称',
  `methodPath` varchar(511) DEFAULT NULL COMMENT '请求方法地址',
  `methodParam` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `operaTime` bigint(13) NOT NULL COMMENT '操作时间',
  `logIp` varchar(31) DEFAULT NULL COMMENT '操作Ip',
  `logDesc` varchar(1000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志历史表';

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.sysinfoparameter 结构
CREATE TABLE IF NOT EXISTS `sysinfoparameter` (
  `parameterId` int(4) NOT NULL AUTO_INCREMENT COMMENT '系统参数ID',
  `paraName` varchar(16) NOT NULL COMMENT '参数名称',
  `paraDesc` varchar(16) DEFAULT NULL COMMENT '参数描述',
  `paraValue` varchar(32) NOT NULL COMMENT '参数值',
  `createTime` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效0无效，1有效',
  PRIMARY KEY (`parameterId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.syslogloghis 结构
CREATE TABLE IF NOT EXISTS `syslogloghis` (
  `logId` bigint(13) NOT NULL COMMENT '日志ID',
  `userId` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `loginTime` bigint(13) DEFAULT NULL COMMENT '登录时间',
  `logoutTime` bigint(13) DEFAULT NULL COMMENT '退出时间',
  `logIp` varchar(31) DEFAULT NULL COMMENT '登录Ip',
  `lastlogTime` bigint(13) DEFAULT NULL COMMENT '上次登录时间',
  `status` int(1) DEFAULT NULL COMMENT '状态;1在线0下线',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志历史表';

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.sys_button_log 结构
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

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.sys_login_log 结构
CREATE TABLE IF NOT EXISTS `sys_login_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `userId` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `loginTime` bigint(13) DEFAULT NULL COMMENT '登录时间',
  `logoutTime` bigint(13) DEFAULT NULL COMMENT '退出时间',
  `logIp` varchar(31) DEFAULT NULL COMMENT '登录Ip',
  `lastlogTime` bigint(13) DEFAULT NULL COMMENT '上次登录时间',
  `status` int(1) DEFAULT NULL COMMENT '状态;1在线0下线',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志表';

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.sys_menu 结构
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
  `permission` varchar(64) DEFAULT NULL COMMENT '权限类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.sys_privilege 结构
CREATE TABLE IF NOT EXISTS `sys_privilege` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '权限管理表ID',
  `roleId` bigint(11) DEFAULT NULL COMMENT '角色ID',
  `urlId` int(8) DEFAULT NULL COMMENT '菜单ID',
  `permValue` int(4) DEFAULT NULL COMMENT '权限值',
  `Permission` varchar(64) DEFAULT NULL COMMENT '权限类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理表';

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleCode` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `roleName` varchar(64) NOT NULL COMMENT '角色名称',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `createTime` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `parentId` int(4) NOT NULL COMMENT '父级角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 数据导出被取消选择。


-- 导出  表 twosnail_basic.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
