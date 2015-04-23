package com.twosnail.basic.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**   
 * @Title: SysInfoUser.java
 * @Description: TODO 
 * @author 两只蜗牛   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysPermission extends Model<SysPermission>{
	public static final SysPermission me = new SysPermission() ; 
	
	/**
	 * 获取用户权限
	 * @param id
	 * @return
	 */
	public List<SysPermission> getPermissionByUserId( int id ) {
		return me.find( "SELECT p.* FROM sys_permission AS p LEFT JOIN sys_role_permission AS rp ON p.id = rp.permissionId "
				+ " LEFT JOIN sys_role s ON rp.roleId = s.id AND s.id = (SELECT roleId FROM sys_user WHERE id = ? ) " , id ) ;
	}
	
	/**
     * 获取角色权限
     * @param roleId
     */
    public List<SysPermission> getPermissionByRoleId( int roleId ){
    	return  me.find( "SELECT p.* FROM sys_permission AS p LEFT JOIN sys_role_permission AS rp ON p.id = rp.permissionId "
				+ " LEFT JOIN sys_role s ON rp.roleId = s.id AND s.id = ? " , roleId ) ;
    }
	
}
