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
 * @Title: SysInfoUser.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysInfoUser extends Model<SysInfoUser>{
	
	public static final SysInfoUser me = new SysInfoUser() ; 
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
		SysInfoUser infoUser = me.findFirst( "SELECT a.userId,a.roleId,a.userName,a.createTime,a.createIp,a.isUsed,a.sortNo , "
				+ "b.roleCode,b.roleName  "
				+ "FROM sysinfouser a "
				+ "left join sysinforole b on a.roleId = b.roleId "
				+ "WHERE a.userName=? AND a.passWord=? " , userName , passWord ) ;
		
		if( infoUser == null ) {
			//没有该用户
			throw new BusiException( "账号或密码错误！" );
		} else if( infoUser.getInt( "isUsed" ) != SysInfoUser.STATUS_NOMAL ) {
			//管理员被冻结
			throw new BusiException( "用户被冻结！" );
		} else {
			//正常登陆 获取用户所有角色，所有权限
			List<SysPrivilege> userPrivilege = SysPrivilege.me.getSysPrivilegeByUserId( infoUser.getLong( "userId" ) ) ;
			
			//设置session
			UserInfo.setUserSession( session, infoUser, userPrivilege );
		}
		
	}
	
	/**
	 * 获取用户信息
	 * @param roleId
	 * @param keyWord
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Record> getUserInfo( int roleId ,String keyWord , int pageNumber, int pageSize) {
		StringBuffer sb = new StringBuffer(" FROM sysinfouser a WHERE 1=1 ");
		if( keyWord != null && "".equals( keyWord = keyWord.trim() ) ) {
			sb.append( " AND (a.userName LIKE '%"+keyWord+"%' or a.userId LIKE '%"+keyWord+"%')" ) ;
		}
		if( roleId != -1 ) {
			sb.append( " AND a.roleId = " + roleId ) ;
		}
		return Db.paginate(pageNumber, pageSize, 
				"SELECT a.*,(select a1.roleName from sysinforole a1 WHERE a1.roleId = a.roleId) roleName" , sb.toString() );
			
	}

	/**
	 * 判断用户名是否存在
	 * @param userName
	 * @return  true-->存在
	 */
	public boolean checkUserName(String userName){
		return me.findFirst( "SELECT userId,userName FROM sysinfouser WHERE userName=?" , userName) != null ;
	}
	
	/**
	 * 检查用户名的个数
	 * @param userName
	 */
	public int checkUserNameCount( String userName ){
		return Db.queryInt("SELECT COUNT(1) FROM sysinfouser a WHERE a.userName = ?" , userName );
	}
	
	/**
     * 查看该角色下是否存在用户
     * @param roleId
     * @return true 不存在
     * @throws BusiException
     */
    public boolean checkUserByRoleId( int roleId ){
    	return Db.queryInt("SELECT COUNT(1) FROM sysinfouser a WHERE a.roleId = ?" , roleId ) == 1;
    }
    
	
	/**
	 * 添加用户信息
	 * @param infoUser
	 * @throws BusiException
	 */
	public void addUserInfo( SysInfoUser infoUser , HttpServletRequest request ) throws BusiException{
		infoUser.set( "createTime" ,System.currentTimeMillis() );
        infoUser.set( "createUserId" ,UserInfo.getUserId( request ) ) ;
        infoUser.set( "createIp" ,RequestHandler.getIpAddr(request)) ;
		me.setAttrs(infoUser) ;
		if( !me.save() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
	
	/**
	 * 通过id查询用户信息
	 * @param userId
	 * @return
	 */
	public SysInfoUser getUserInfoById( long userId){
		return me.findById(userId) ;
	}
	
	/**
	 * 修改用户信息
	 * @param infoUser
	 * @throws BusiException
	 */
	public void updateSysInfoUser( 
			SysInfoUser infoUser , HttpServletRequest request ) throws BusiException{
		infoUser.set( "operateUserId" , UserInfo.getUserId(request) ) ;
		infoUser.set( "opetateTime" , System.currentTimeMillis() ) ;
		me.setAttrs(infoUser) ;
		if( !me.update() ) {
            throw new BusiException( "添加信息失败!" );
        }
	}
	
	/**
	 * 修改用户状态
	 * @param userId
	 * @param isUsed
	 * @return
	 * @throws BusiException
	 */
    public void updateSysInfoUserStasus( long userId , int isUsed ) throws BusiException{
    	me.set( "userId", userId );
    	me.set( "isUsed", isUsed );
		if( !me.update() ) {
            throw new BusiException( "修改用户状态失败!" );
        };
    }
    
    /**
     * 删除用户
     * @param userId
     * @return
     * @throws BusiException
     */
    public void deleteSysInfoUserTx( String[] ids ) throws BusiException{
		for (String id : ids) {
			if( !me.deleteById(id) ) {
	            throw new BusiException( "删除用户失败!" );
	        }
		}
    }
    
}
