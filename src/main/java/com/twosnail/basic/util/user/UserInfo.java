package com.twosnail.basic.util.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.model.SysPrivilege;

/**   
 * @Title: UserInfo.java
 * @Description: 用户信息
 * @author: 两只蜗牛
 * @date: 2015年4月18日 下午1:10:38
 * @version: V1.0
 */
public class UserInfo {

	
	public static void setUserSession( 
			HttpSession session, 
			Record userInfo , List<SysPrivilege> userPrivilege ) {
		session.setAttribute( "userInfo", userInfo );
		session.setAttribute( "userPrivilege", userPrivilege );
	}
	
	public static Record getUserInfo( HttpSession session ) {
		return (Record) session.getAttribute( "userInfo" );
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
		Record userInfo = getUserInfo(session) ;
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
		Record userInfo = getUserInfo(session) ;
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
		Record userInfo = getUserInfo(session) ;
		return userInfo == null ? -1 : userInfo.getInt("roleId") ;
	}
	
	/**
	 * 角色名称
	 * @param session
	 * @return
	 */
	public static String getRoleName( HttpSession session ) {
		Record userInfo = getUserInfo(session) ;
		return userInfo == null ?  null : userInfo.getStr("roleName");
	}
	
	/**
	 * 角色名称
	 * @param session
	 * @return
	 */
	public static String getRoleCode( HttpSession session ) {
		Record userInfo = getUserInfo(session) ;
		return userInfo == null ?  null : userInfo.getStr("roleCode");
	}
	
	/**
	 * 获取当前用户所有权限
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<SysPrivilege> getUserPrivilege( HttpSession session ) {
		return (List<SysPrivilege>) session.getAttribute( "userPrivilege" );
	}
	
	public static void destory( HttpSession session ) {
		session.removeAttribute( "userInfo" );
		session.removeAttribute( "userPrivilege" );
	}

}
