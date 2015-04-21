package com.twosnail.basic.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.util.RequestHandler;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.user.UserInfo;

/**   
 * @Title: SysUser.java
 * @Description: TODO 
 * @author 两只蜗牛   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysUser extends Model<SysUser>{
	
	public static final SysUser me = new SysUser() ; 
	public static final int STATUS_NOMAL = 1;    
	public static final int STATUS_FREEZE = 0;
	
	/**
	 * 处理用户登录
	 * @param loginName
	 * @param password
	 * @param session
	 * @throws BusiException  
	 */
	public void userLogin( 
			String userName, String passWord,String code, HttpServletRequest request) 
			throws BusiException {
		
		HttpSession session =  request.getSession() ;
		Object obj = session.getAttribute("code");
		if( !code.equals(obj) ){ 
			throw new BusiException( "验证码错误！" ); 
		}
		
		//登录前先清空session
		UserInfo.destory( session );
		
		List<String> param = new ArrayList<String>(2) ;
		param.add( userName ) ;
		param.add( passWord ) ;
		Record user = Db.findFirst( 
				"SELECT a.id,a.roleId,a.userName,a.createTime,a.createIp,a.isUsed,a.sortNo,b.roleCode,b.roleName  "
				+ "FROM sys_user a left join sys_role b on a.id = b.id WHERE a.userName=? AND a.passWord=? " , userName , passWord ) ;
		
		if( user == null ) {
			//没有该用户
			throw new BusiException( "账号或密码错误！" );
		} else if( user.getInt( "isUsed" ) != SysUser.STATUS_NOMAL ) {
			//管理员被冻结
			throw new BusiException( "用户被冻结！" );
		} else {
			//正常登陆 获取用户所有角色，所有权限
			List<SysPrivilege> userPrivilege = SysPrivilege.me.getPrivilegeByUserId( user.getLong( "id" ) ) ;
			
			//设置session
			UserInfo.setUserSession( session, user, userPrivilege );
		}
		
	}
	
	/**
	 * 获取用户信息
	 * @param id
	 * @param keyWord
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Record> getUser( int roleId ,String keyWord , int pageNumber, int pageSize) {
		StringBuffer sb = new StringBuffer(" FROM sys_user a WHERE 1=1 ");
		if( keyWord != null && "".equals( keyWord = keyWord.trim() ) ) {
			sb.append( " AND (a.userName LIKE '%"+keyWord+"%' or a.id LIKE '%"+keyWord+"%')" ) ;
		}
		if( roleId != -1 ) {
			sb.append( " AND a.roleId = " + roleId ) ;
		}
		return Db.paginate(pageNumber, pageSize, 
				"SELECT a.*,(select a1.roleName from sys_role a1 WHERE a1.id = a.roleId) roleName" , sb.toString() );
			
	}

	/**
	 * 判断用户名是否存在
	 * @param userName
	 * @return  true-->存在
	 */
	public boolean checkUserName(String userName){
		return me.findFirst( "SELECT id,userName FROM sys_user WHERE userName=?" , userName) != null ;
	}
	
	/**
	 * 检查用户名的个数
	 * @param userName
	 */
	public int checkUserNameCount( String userName ){
		return Db.queryInt("SELECT COUNT(1) FROM sys_user a WHERE a.userName = ?" , userName );
	}
	
	/**
     * 查看该角色下是否存在用户
     * @param id
     * @return true 不存在
     * @throws BusiException
     */
    public boolean checkUserById( int id ){
    	return Db.queryColumn("SELECT id FROM sys_user a WHERE a.roleId = ?" , id ) == null;
    }
    
	
	/**
	 * 添加用户信息
	 * @param sysUser
	 * @throws BusiException
	 */
	public void addUser( SysUser sysUser , HttpServletRequest request ) throws BusiException{
		sysUser.set( "createTime" ,System.currentTimeMillis() );
        sysUser.set( "createId" ,UserInfo.getId( request ) ) ;
        sysUser.set( "createIp" ,RequestHandler.getIpAddr(request)) ;
		me.setAttrs(sysUser) ;
		if( !me.save() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
	
	/**
	 * 通过id查询用户信息
	 * @param id
	 * @return
	 */
	public SysUser getUserById( long id){
		return me.findById(id) ;
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 * @param request
	 * @throws BusiException
	 */
	public void updUser( 
			SysUser user , HttpServletRequest request ) throws BusiException{
		user.set( "operateId" , UserInfo.getId(request) ) ;
		user.set( "opetateTime" , System.currentTimeMillis() ) ;
		me.setAttrs(user) ;
		if( !me.update() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
	
	/**
	 * 修改用户状态
	 * @param id
	 * @param isUsed
	 * @throws BusiException
	 */
    public void updUserStasus( long id , int isUsed ) throws BusiException{
    	me.set( "id", id );
    	me.set( "isUsed", isUsed );
		if( !me.update() ) {
            throw new BusiException( "修改用户状态失败!" );
        };
    }
    
    /**
     * 删除用户
     * @param id
     * @throws BusiException
     */
    public void delUserTx( String[] ids ) throws BusiException{
		for (String id : ids) {
			if( !me.deleteById( id ) ) {
	            throw new BusiException( "删除用户失败!" );
	        }
		}
    }
    
}
