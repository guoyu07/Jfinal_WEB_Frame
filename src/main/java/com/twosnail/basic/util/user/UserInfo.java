package com.twosnail.basic.util.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.twosnail.basic.model.SysUser;
import com.twosnail.basic.model.SysPrivilege;

/**   
 * @Title: UserInfo.java
 * @Description: TODO
 * @author: 两只蜗牛
 * @date: 2015年4月18日 下午1:10:38
 * @version: V1.0
 */
public class UserInfo {

	
	public static void setUserSession( 
			HttpSession session, 
			SysUser userInfo , List<SysPrivilege> userPrivilege ) {
		session.setAttribute( "userInfo", userInfo );
		session.setAttribute( "userPrivilege", userPrivilege );
	}
	
	public static SysUser getUserInfo( HttpSession session ) {
		return (SysUser) session.getAttribute( "userInfo" );
	}
	
	/**
	 * 获取当前用户ID
	 * @param request
	 * @return
	 */
	public static long getId( HttpServletRequest request ) {
		return getId( request.getSession() );
	}
	
	/**
	 * 获取当前用户ID
	 * @param session
	 * @return
	 */
	public static long getId( HttpSession session ) {
		SysUser userInfo = getUserInfo(session) ;
		return userInfo == null ? -1 : userInfo.getLong("id") ;
	}
	
	/**
	 * 获取当前用户名称
	 * @param request
	 * @return
	 */
	public static String getUserName( HttpServletRequest request ) {
		return getUserName( request.getSession() );
	}
	
	/**
	 * 获取当前用户名称
	 * @param session
	 * @return
	 */
	public static String getUserName( HttpSession session ) {
		SysUser userInfo = getUserInfo(session) ;
		return userInfo == null ?  null : userInfo.getStr("userName");
	}
	
	/**
	 * 获取用户角色ID
	 * @param request
	 * @return
	 */
	public static int getRoleId( HttpServletRequest request ) {
		return getRoleId( request.getSession() );
	}
	
	/**
	 * 获取用户角色ID
	 * @param session
	 * @return
	 */
	public static int getRoleId( HttpSession session ) {
		SysUser userInfo = getUserInfo(session) ;
		return userInfo == null ? -1 : userInfo.getInt("roleId") ;
	}
	
	/**
	 * 角色名称
	 * @param session
	 * @return
	 */
	public static String getRoleName( HttpSession session ) {
		SysUser userInfo = getUserInfo(session) ;
		return userInfo == null ?  null : userInfo.getStr("roleName");
	}
	
	/**
	 * 角色名称
	 * @param session
	 * @return
	 */
	public static String getRoleCode( HttpSession session ) {
		SysUser userInfo = getUserInfo(session) ;
		return userInfo == null ?  null : userInfo.getStr("roleCode");
	}
	
	/**
	 * 获取当前用户所有权限
	 * @param session
	 * @return
	 */
	public static List<SysPrivilege> getUserPrivilege( HttpSession session ) {
		@SuppressWarnings("unchecked")
		List<SysPrivilege> userPrivilege = (List<SysPrivilege>) session.getAttribute( "userPrivilege" );
		return userPrivilege ;
	}
	
	public static void destory( HttpSession session ) {
		session.removeAttribute( "userInfo" );
		session.removeAttribute( "userPrivilege" );
	}

}
