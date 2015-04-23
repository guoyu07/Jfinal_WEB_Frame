package com.twosnail.basic.model;

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
	 * 保存角色权限信息
	 * @param role
	 */
	public void save( SysRolePermission rolePermission ) throws BusiException {
		if( !rolePermission.save() ) {
			throw new BusiException( "保存角色权限信息失败" );
		}
	}
	
}
