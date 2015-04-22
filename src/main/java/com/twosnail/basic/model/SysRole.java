package com.twosnail.basic.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.twosnail.basic.config.SysConfig;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.tree.TreeList;
import com.twosnail.basic.util.tree.TreeNode;

/**   
 * @Title: SysRole.java
 * @Description: TODO 
 * @author 两只蜗牛   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysRole extends Model<SysRole>{
	public static final SysRole me = new SysRole() ; 
	
	/**
     * 获取所有角色
     */
    public List<SysRole> getSysRoles(){
    	return me.find( "SELECT a.* FROM sys_role a " ) ;
    }
    
    /**
     * 获取角色列表信息（树结构）
     * @return
     */
    public List<TreeNode<SysRole>> getRoleList(){
    	List<SysRole> sysRole = me.find( "SELECT * FROM sys_role " ) ;
    	List<TreeNode<SysRole>> list =  TreeList.sort( sysRole, new TreeList.SortHandler<SysRole>() {
			public int getId(SysRole t){
				return t.getInt("id");
			}
			public int getParentId(SysRole t){
				return t.getInt("parentId");
			}
    	} );
    	return list ;
    }
    
    /**
	 * 通过id查询角色信息
	 * @param id
	 * @return
	 */
	public SysRole getRoleById( int id ){
		return me.findById( id ) ;
	}
	
	public String getRoleName(  int id  ) {
		return this.getRoleById(id).getStr("roleName") ;
	}
	
	/**
	 * 添加角色信息
	 * @param sysRole
	 * @throws BusiException
	 */
	public void addRole( SysRole sysRole) throws BusiException {
		sysRole.set( "createTime" , System.currentTimeMillis() );
		if( !sysRole.save() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
	
	/**
	 * 修改角色信息
	 * @param sysRole
	 * @throws BusiException
	 */
	public void updRole( SysRole sysRole ) throws BusiException {
		if( !sysRole.update() ) {
            throw new BusiException( "修改角色信息失败!" );
        }
	}
	
	/**
     * 修改角色信息状态
     * @param SysRole
     * @throws BusiException
     */
    public void updRoleStasus( SysRole sysRole ) throws BusiException {
    	if(  !sysRole.update() ) {
            throw new BusiException( "修改信息失败" );
        }
    }   
    
    /**
     * 删除 角色
     * @param id
     * @throws BusiException
     */
    public void delRoleTx( int id ) throws BusiException{
       if( !me.deleteById( id ) ) {
           throw new BusiException( "修改信息失败" );
       }
    }
    
    /**
     * 删除该角色所有权限
     * @param id
     */
    public void delPrivilegeByid( long id ) throws BusiException{
    	if( !me.deleteById(id)   ) {
            throw new BusiException( "修改信息失败" );
        }
    }
    
    /**
     * 获取权限
     * @return
     */
    public List<TreeNode<SysMenu>> getPrimession(){
    	List<SysMenu> list = SysMenu.me.getMenuList() ;
    	for ( SysMenu menu : list ) {
			menu.set( "permission" , SysConfig.me.getProperty( menu.getStr("id") ) ) ; 
		}
    	List<TreeNode<SysMenu>> tree =  TreeList.sort( list, new TreeList.SortHandler<SysMenu>() {
			public int getId(SysMenu t){
				return t.getInt("id");
			}
			public int getParentId(SysMenu t){
				return t.getInt("parentId");
			}
    	});
    	return tree ;
    }
    
    /**
     * 添加权限信息
     * @param id
     * @param privilege
     * @throws BusiException
     */
    public void addPrivilegeById( long id ,List<SysPermission> privilege) throws BusiException{
    	for (SysPermission sysPrivilege : privilege) {
    		sysPrivilege.set( "id" , id ) ;
    		if( !me.save() ) {
                throw new BusiException( "添加权限信息失败" );
            }
		}
    }
    
    
}
