package com.twosnail.basic.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.twosnail.basic.util.exception.BusiException;

/**   
 * @Title: SysRolePermission.java
 * @Description: TODO 
 * @author 两只蜗牛  
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysRolePermission extends Model<SysRolePermission>{
	public static final SysRolePermission me = new SysRolePermission() ; 
	
	
	/**
	 * 获取用户权限
	 * @param id
	 * @return
	 */
	public List<SysRolePermission> getPermissionByUserId( int id ) {
		return me.find( "SELECT rp.* FROM sys_role_permission AS rp LEFT JOIN sys_role s ON rp.roleId = s.id AND s.id = (SELECT roleId FROM sys_user WHERE id = ? ) " , id ) ;
	}
	
	/**
     * 获取角色权限
     * @param roleId
     */
    public List<SysRolePermission> getPermissionByRoleId( int roleId ){
    	return  me.find( "SELECT p.* FROM sys_role_permission AS rp LEFT JOIN sys_role s ON rp.roleId = s.id AND s.id = ? " , roleId ) ;
    }
    
    /**
     * 添加权限信息
     * @param roleId
     * @param permis
     * @throws BusiException
     */
    public void addPermissions( int roleId , String[] permis ) throws BusiException{
    	//清空之前数据
    	Db.update( "DELETE FROM sys_role_permission WHERE roleId=?" , roleId ) ;
    	SysRolePermission rolePermission = null ;
    	for (String string : permis) {
    		rolePermission = new SysRolePermission() ;
    		rolePermission.set( "roleId" , roleId ) ;
    		rolePermission.set( "permission" , string ) ;
    		addPermission(rolePermission);
		}
    }
    
    
	/**
	 * 保存角色权限信息
	 * @param rolePermission
	 */
	public void addPermission( SysRolePermission rolePermission ) throws BusiException {
		if( !rolePermission.save() ) {
			throw new BusiException( "保存角色权限信息失败" );
		}
	}
	
	
	
}
