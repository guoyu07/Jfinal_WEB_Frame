package com.twosnail.basic.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**   
 * @Title: SysInfoUser.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysPrivilege extends Model<SysPrivilege>{
	public static final SysPrivilege me = new SysPrivilege() ; 
	
	
	public List<SysPrivilege> getSysPrivilegeByid( long id ) {
		return me.find( 
				"SELECT a.* FROM sysprivilege a  LEFT JOIN sysinfouser b  ON a.roleId = b.roleId WHERE b.id = ?" , id ) ;
	}
	
	/**
     * 获取角色授权信息
     * @param roleId
     */
    public List<SysPrivilege> getSysPrivilegeById( long roleId ){
    	return  me.find( "SELECT a.* FROM sysprivilege a WHERE a.roleId = ?" , roleId ) ;
    }
	
}
