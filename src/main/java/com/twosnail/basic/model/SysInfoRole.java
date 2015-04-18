package com.twosnail.basic.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.tree.TreeList;
import com.twosnail.basic.util.tree.TreeNode;

/**   
 * @Title: SysInfoUser.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysInfoRole extends Model<SysInfoRole>{
	public static final SysInfoRole me = new SysInfoRole() ; 
	
	/**
     * 获取所有角色
     */
    public List<SysInfoRole> getSysRoles(){
    	return me.find( "SELECT a.* FROM sysinforole a " ) ;
    }
    
    /**
     * 获取角色列表信息
     * @return
     */
    public List<TreeNode<SysInfoRole>> getSysInfoRoleList(){
    	List<SysInfoRole> infoRole = me.find( "SELECT * FROM sysinforole " ) ;
    	List<TreeNode<SysInfoRole>> list =  TreeList.sort( infoRole, new TreeList.SortHandler<SysInfoRole>() {
			public int getId(SysInfoRole t){
				return t.getInt("roleId");
			}
			public int getParentId(SysInfoRole t){
				return t.getInt("parentId");
			}
    	} );
    	return list ;
    }
    
    /**
	 * 通过id查询角色信息
	 * @param roleId
	 * @return
	 */
	public SysInfoRole getRoleInfoById( int roleId ){
		return me.findById( roleId ) ;
	}
	
	public String getRoleName(  int roleId  ) {
		return this.getRoleInfoById(roleId).getStr("roleName") ;
	}
	
	/**
	 * 添加角色信息
	 * @param infoRole
	 * @throws BusiException
	 */
	public void addRoleInfo( SysInfoRole infoRole) throws BusiException {
		me.setAttrs(infoRole) ;
		if( !me.save() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
    
	/**
	 * 获取角色信息
	 * @param keyWord
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<SysInfoRole> getRoleInfo(String keyWord , int pageNumber , int pageSize ) {
		StringBuffer sb = new StringBuffer(" FROM sysinforole a WHERE 1=1  ");
		if( keyWord != null && "".equals( keyWord = keyWord.trim() ) ) {
			sb.append( " AND (a.roleName LIKE '%"+keyWord+"%' )" ) ;
		}
		return paginate( pageNumber, pageSize, "SELECT a.* " , sb.toString() );
    }
	
	/**
	 * 修改角色信息
	 * @param infoRole
	 * @throws BusiException
	 */
	public void updateSysInfoRole( SysInfoRole infoRole ) throws BusiException {
		me.setAttrs(infoRole) ;
		if( !me.save() ) {
            throw new BusiException( "修改角色信息失败!" );
        }
	}
	
	/**
     * 修改角色信息状态
     * @param SysInfoRole
     * @throws BusiException
     */
    public void updateSysInfoRoleStasus( SysInfoRole infoRole ) throws BusiException {
    	me.setAttrs( infoRole ) ;
    	if(  !me.update() ) {
            throw new BusiException( "修改信息失败" );
        }
    }   
    
    /**
     * 删除 角色
     * @param roleId
     * @throws BusiException
     */
    public void deleteSysInfoRoleTx( long roleId ) throws BusiException{
       if( me.deleteById( roleId ) ) {
           throw new BusiException( "修改信息失败" );
       }
    }
    
    /**
     * 通过角色ID获取用户信息
     * @param roleId
     * @return
     */
   /* public String getUserInfoByroleId( int roleId ){
    	List<String> infoUser = this.defaultDao.findAll( "SysInfoUser.getUserInfoByroleId" , roleId, String.class) ;
    	StringBuffer strBuffer = new StringBuffer() ;
    	for (String string : infoUser){
    		strBuffer.append(string + ", ") ;
		}    	
    	return strBuffer.toString() ;
    }*/
    
    /**
     * 删除该角色所有权限
     * @param roleId
     */
    public void deleteSysPrivilegeByroleId( long roleId ) throws BusiException{
    	if( !me.deleteById(roleId)   ) {
            throw new BusiException( "修改信息失败" );
        }
    }
    
    /**
     * 添加权限信息
     * @param roleId
     * @param privilege
     * @throws BusiException
     */
    public void addSysPrivilegeByroleId( long roleId ,List<SysPrivilege> privilege) throws BusiException{
    	for (SysPrivilege sysPrivilege : privilege) {
    		sysPrivilege.set( "roleId" , roleId ) ;
    		if( !me.save() ) {
                throw new BusiException( "添加权限信息失败" );
            }
		}
    }
    
    
}
