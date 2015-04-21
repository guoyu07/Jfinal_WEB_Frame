package com.twosnail.basic.model;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.util.RequestHandler;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.user.UserInfo;

/**   
 * @Title: SysLoginLog.java
 * @Description: 登录日志
 * @author 两只蜗牛   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysLoginLog extends Model<SysLoginLog>{
	public static final SysLoginLog me = new SysLoginLog() ; 
	
	private Logger logger = Logger.getLogger(SysLoginLog.class) ;
	
	/**
	 * 添加登录日志
	 * @param request
	 */
	public void addLoginLog( HttpServletRequest request ) {
		try {
			me.set( "userId", UserInfo.getId(request) );
			me.set( "loginTime",System.currentTimeMillis());
			me.set( "logIp",RequestHandler.getIpAddr(request));
			me.set( "lastlogTime" ,getLastTime( UserInfo.getId(request) ) );
			me.set( "status" ,1);
			if( !me.save() ) {
	            throw new BusiException( "添加信息失败!" );
	        }
		} catch (Exception e) {
			logger.warn( "添加登录日志出错！" , e );
		}
		
	}
	
	/**
	 * 查询用户最后一次登录的时间
	 * @param userId
	 * @return
	 */
	private long getLastTime( long userId ){
		 Long loginTime = Db.queryLong( 
				"SELECT l.loginTime FROM sys_login_log l where l.userId=? order by l.logintime DESC LIMIT 0,1 " , userId ) ;
		 return loginTime == null ? 0 : loginTime ;
	}
	/**
	 * 获取登录日志
	 * @param keyWord
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Record> getLoginLog( String keyWord , int pageNumber, int pageSize) {
		StringBuffer sb = new StringBuffer(" FROM sys_login_log a WHERE 1=1 ");
		/*if( keyWord != null && "".equals( keyWord = keyWord.trim() ) ) {
			sb.append( " AND (a.userName LIKE '%"+keyWord+"%' or a.id LIKE '%"+keyWord+"%')" ) ;
		}*/
		
		return Db.paginate(pageNumber, pageSize, 
				"SELECT a.* ,(select a1.userName from sys_user a1 WHERE a1.id = a.userId) userName" , sb.toString() );
	}
	
	public void delUserTx( String[] ids ) throws BusiException{
		for (String id : ids) {
			if( !me.deleteById( id ) ) {
	            throw new BusiException( "删除用户失败!" );
	        }
		}
    }
	
}
