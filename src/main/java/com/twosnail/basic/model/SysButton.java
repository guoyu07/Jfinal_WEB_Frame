package com.twosnail.basic.model;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.twosnail.basic.util.exception.BusiException;

/**   
 * @Title: SysButton.java
 * @Description: TODO 
 * @author 两只蜗牛   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysButton extends Model<SysButton>{
	
	public static final SysButton me = new SysButton() ; 
	private Logger logger = Logger.getLogger( SysButton.class ) ;
	
	/**
	 * 通过菜单权限值添加功能按钮
	 * @param menuId
	 * @param permissionMethod
	 * @throws BusiException
	 */
	public void addSysButtons( int menuId ,String permissionMethod ) throws BusiException {
		//通过反射，找到所需节点名称，值
		if(permissionMethod != null && !"".equals( permissionMethod )){
			Class<?> clz;
			SysButton button = null ;
			try {
				clz = Class.forName( "com.twosnail.basic.controller." + permissionMethod);
				Method[] methods = clz.getMethods() ;
				for (Method method : methods) {
					button = new SysButton() ;
					RequiresPermissions permission = method.getAnnotation( RequiresPermissions.class ) ;
					if( permission != null ){
						String value = permission.value()[0] ;
						if( value != null && !permissionMethod.equals( value ) ){
							button.set( "menuId" , menuId ) ;
							button.set( "value" , permissionMethod+"."+value ) ;
							if( value.startsWith( "add" ) )
								button.set( "name" , "添加" ) ;
							else if( ( value.startsWith( "edit" ) || value.startsWith("upd") ) && 
									!(value.contains( "Status" ) || value.contains( "status" )) )
								button.set( "name" , "修改" ) ;
							else if( (value.contains( "Status" ) || value.contains( "status" )) )
								button.set( "name" , "修改状态" ) ;
							else if( (value.startsWith( "del" ) ) )
								button.set( "name" , "删除" ) ;
							else button.set( "name" , value ) ;
							addButton( button );
						}
					}
				}
			} catch (ClassNotFoundException e){
				logger.debug("不存在该类:"+e.getMessage()) ;
			}
		}
	    	
	}
	 
	/**
	 * 获取功能按钮信息
	 * @param menuId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<SysButton> getButton( int menuId , int pageNumber, int pageSize) {
		return me.paginate(pageNumber, pageSize, 
				"SELECT a.* " , "FROM sys_button a WHERE menuId=? " , menuId );
			
	}
	
	/**
	 * 通过菜单Id查找功能信息
	 * @param menuId
	 * @return
	 */
	public List<SysButton> getButton( int menuId ) {
		return me.find( "SELECT a.*  FROM sys_button a WHERE menuId=? ", menuId );
			
	}

	/**
	 * 判断功能按钮名是否存在
	 * @param userName
	 * @return  true-->存在
	 */
	public boolean checkButtonName(String userName){
		return me.findFirst( "SELECT id,userName FROM sys_user WHERE userName=?" , userName) != null ;
	}
	
	/**
	 * 检查功能按钮名的个数
	 * @param userName
	 */
	public int checkButtonNameCount( String userName ){
		return Db.queryInt("SELECT COUNT(1) FROM sys_user a WHERE a.userName = ?" , userName );
	}
	
	/**
     * 查看该角色下是否存在功能按钮
     * @param id
     * @return true 不存在
     * @throws BusiException
     */
    public boolean checkButtonById( int id ){
    	return Db.queryColumn("SELECT id FROM sys_user a WHERE a.roleId = ?" , id ) == null;
    }
    
	
	/**
	 * 添加功能按钮信息
	 * @param sysButton
	 * @throws BusiException
	 */
	public void addButton( SysButton sysButton ) throws BusiException{
		if( !sysButton.save() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
	
	/**
	 * 通过id查询功能按钮信息
	 * @param id
	 * @return
	 */
	public SysButton getButtonById( long id){
		return me.findById(id) ;
	}
	
	/**
	 * 修改功能按钮信息
	 * @param user
	 * @param request
	 * @throws BusiException
	 */
	public void updButton( 
			SysButton user , HttpServletRequest request ) throws BusiException{
		if( !user.update() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
	
	
    
    /**
     * 删除功能按钮
     * @param id
     * @throws BusiException
     */
    public void delButtonTx( String[] ids ) throws BusiException{
		for (String id : ids) {
			if( !me.deleteById( id ) ) {
	            throw new BusiException( "删除功能按钮失败!" );
	        }
		}
    }
    
    public void delButtonByMenuId( int menuId ) throws BusiException{
    	Db.update( "DELETE FROM sys_button WHERE menuId = ? ", menuId ) ;
    }
}