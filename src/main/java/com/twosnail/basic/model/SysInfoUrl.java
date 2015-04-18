package com.twosnail.basic.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.util.PermissionName;

/**   
 * @Title: SysInfoUser.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysInfoUrl extends Model<SysInfoUrl>{
	private Logger logger = Logger.getLogger( SysInfoUrl.class );
	public static final SysInfoUrl me = new SysInfoUrl() ; 
	
	/**
     * 获取授权列表
     * @return
     * @throws Exception
     */
    public List<Record> getSysInfoUrlByRoleId(long roleId){
    	//获取菜单
    	List<Record> infoUrl = Db.find( 
    			"SELECT a.*,IFNULL((select a1.permValue from sysprivilege a1 where a.urlId = a1.urlId and a1.roleId=? ),-1) permValue "
    			+ "FROM sysinfourl a ORDER BY a.sortNo" , roleId ) ;
    	List<SysButton> buttonList = new ArrayList<SysButton>() ;
    	for (Record sysInfoUrl : infoUrl){
    		
    		buttonList = new ArrayList<SysButton>() ;
    		String permissionMethod = sysInfoUrl.getStr( "permission" ) ;
    		//通过反射，找到所需节点名称，值
    		if(permissionMethod != null && !"".equals( permissionMethod ))
    		{
    			Class<?> clz;
				try
				{
					clz = Class.forName(permissionMethod);
					// 获取当前实体类的所有属性，返回Field数组 
	    			Field[] fields = clz.getDeclaredFields(); 
	    			// 获取父类的所有属性，返回Field数组
	    			Field[] fieldsSup = clz.getSuperclass().getDeclaredFields();
	    			
	    			SysButton button = null ;
	    			for(int i=0; i< fieldsSup.length; i++)
	    	        {
	    				button = new SysButton() ;
	    				Field f = fieldsSup[i];	            
	    	            PermissionName permission = f.getAnnotation(PermissionName.class); 
	    	            button.set( "btnName" , permission.value() ) ;
	    	            int permValue = -1 ;
	    	            if (fieldsSup[i].getType().getName().equals(   java.lang.Integer.class.getName())   || fieldsSup[i].getType().getName().equals("int"))
							try
							{
								permValue = fieldsSup[i].getInt(f);
							} catch (IllegalArgumentException
									| IllegalAccessException e)
							{
								logger.debug("非法参数或安全权限异常:" + e.getMessage() ) ;
							}  
	    	            button.set( "permValue" , permValue ) ;	
	    	            //设置是否存在权限
	    	            if( -1 == sysInfoUrl.getInt( "permValue" ) )
	    	            	button.set( "hasPermission" , false ) ;
	    	            else
	    	            	button.set( "hasPermission" , (sysInfoUrl.getInt("permValue")&permValue)==permValue ) ;
	    	            
	    	            buttonList.add(button) ;
	    	        }
	    	            
	    			for(int i=0; i< fields.length; i++)
	    	        {
	    				button = new SysButton() ;
	    				Field f = fields[i];	            
	    	            PermissionName permission = f.getAnnotation(PermissionName.class); 
	    	            button.set( "btnName" ,permission.value()) ;
	    	            int permValue = -1 ;
	    	            if (fields[i].getType().getName().equals(   java.lang.Integer.class.getName())   || fields[i].getType().getName().equals("int"))
							try
							{
								permValue = fields[i].getInt(f);
							} catch (IllegalArgumentException
									| IllegalAccessException e)
							{
								logger.debug("非法参数或安全权限异常:" + e.getMessage() ) ;
							}  
	    	            button.set( "permValue" ,permValue ) ;
	    	            //设置是否存在权限
	    	            if( -1 == sysInfoUrl.getInt( "permValue" ) )
	    	            	button.set( "hasPermission" , false ) ;
	    	            else
	    	            	button.set( "hasPermission" , (sysInfoUrl.getInt("permValue")&permValue)==permValue ) ;
	    	            
	    	            buttonList.add(button) ;
	    	        }
				} catch (ClassNotFoundException e)
				{
					logger.debug("不存在该类:"+e.getMessage()) ;
				}
				//sysInfoUrl.set( "sysButton" buttonList) ;
    		}
		}
    	return infoUrl ;		
    }
}
