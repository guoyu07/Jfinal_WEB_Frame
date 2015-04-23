package com.twosnail.basic.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.util.PermissionName;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.tree.TreeList;
import com.twosnail.basic.util.tree.TreeNode;

/**   
 * @Title: SysMenu.java
 * @Description: 菜单 
 * @author 两只蜗牛   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */
@SuppressWarnings("serial")
public class SysMenu extends Model<SysMenu> {
	private Logger logger = Logger.getLogger( SysMenu.class );
	public static final SysMenu me = new SysMenu() ; 
	
	/**
     * 获取菜单列表信息Tree
     * @return
     */
    public List<TreeNode<SysMenu>> getMenuTree(){
    	List<SysMenu> menu = this.getMenuList() ;
    	List<TreeNode<SysMenu>> tree =  TreeList.sort( menu, new TreeList.SortHandler<SysMenu>() {
			public int getId(SysMenu t){
				return t.getInt("id");
			}
			public int getParentId(SysMenu t){
				return t.getInt("parentId");
			}
    	} );
    	return tree ;
    }
    
    /**
     * 获取菜单列表信息List
     * @return
     */
    public List<SysMenu> getMenuList(){
    	return me.find( "SELECT * FROM sys_menu " ) ;
    }
    
    /**
	 * 通过id查询菜单信息
	 * @param id
	 * @return
	 */
	public SysMenu getMenuById( int id ){
		return me.findById( id ) ;
	}
	
	public String getMenuName(  int id  ) {
		if( id == -1 ) {
			return null ;
		}
		return this.getMenuById(id).getStr("name") ;
	}
	
	/**
	 * 添加菜单信息
	 * @param SysMenu
	 * @throws BusiException
	 */
	public void addMenu( SysMenu menu) throws BusiException {
		menu.set( "createTime" , System.currentTimeMillis() );
		if( !menu.save() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
    
	/**
	 * 修改菜单信息
	 * @param SysMenu
	 * @throws BusiException
	 */
	public void updMenu( SysMenu menu ) throws BusiException {
		if( !menu.update() ) {
            throw new BusiException( "修改菜单信息失败!" );
        }
	}
	
	/**
     * 修改菜单信息状态
     * @param SysMenu
     * @throws BusiException
     */
    public void updMenuStasus( SysMenu menu ) throws BusiException {
    	if(  !menu.update() ) {
            throw new BusiException( "修改信息失败" );
        }
    }   
    
    /**
     * 删除 菜单
     * @param id
     * @throws BusiException
     */
    public void delMenuTx( int id ) throws BusiException{
       if( !me.deleteById( id ) ) {
           throw new BusiException( "修改信息失败" );
       }
    }
	
	/**
     * 获取授权列表
     * @return
     * @throws Exception
     */
    public List<Record> getsysMenuByMenuId(long menuId){
    	//获取菜单
    	List<Record> infoUrl = Db.find( 
    			"SELECT a.*,IFNULL((select a1.permValue from sys_privilege a1 where a.urlId = a1.urlId and a1.menuId=? ),-1) permValue "
    			+ "FROM sys_menu a ORDER BY a.sortNo" , menuId ) ;
    	List<SysRolePermission> buttonList = new ArrayList<SysRolePermission>() ;
    	for (Record sys_menu : infoUrl){
    		
    		buttonList = new ArrayList<SysRolePermission>() ;
    		String permissionMethod = sys_menu.getStr( "permission" ) ;
    		//通过反射，找到所需节点名称，值
    		if(permissionMethod != null && !"".equals( permissionMethod )){
    			Class<?> clz;
				try
				{
					clz = Class.forName(permissionMethod);
					// 获取当前实体类的所有属性，返回Field数组 
	    			Field[] fields = clz.getDeclaredFields(); 
	    			// 获取父类的所有属性，返回Field数组
	    			Field[] fieldsSup = clz.getSuperclass().getDeclaredFields();
	    			
	    			SysRolePermission button = null ;
	    			for(int i=0; i< fieldsSup.length; i++)
	    	        {
	    				button = new SysRolePermission() ;
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
	    	            if( -1 == sys_menu.getInt( "permValue" ) )
	    	            	button.set( "hasPermission" , false ) ;
	    	            else
	    	            	button.set( "hasPermission" , (sys_menu.getInt("permValue")&permValue)==permValue ) ;
	    	            
	    	            buttonList.add(button) ;
	    	        }
	    	            
	    			for(int i=0; i< fields.length; i++)
	    	        {
	    				button = new SysRolePermission() ;
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
	    	            if( -1 == sys_menu.getInt( "permValue" ) )
	    	            	button.set( "hasPermission" , false ) ;
	    	            else
	    	            	button.set( "hasPermission" , (sys_menu.getInt("permValue")&permValue)==permValue ) ;
	    	            
	    	            buttonList.add(button) ;
	    	        }
				} catch (ClassNotFoundException e)
				{
					logger.debug("不存在该类:"+e.getMessage()) ;
				}
				//sys_menu.set( "sysButton" buttonList) ;
    		}
		}
    	return infoUrl ;		
    	
    	
    }
}
