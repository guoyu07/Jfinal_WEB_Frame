package com.twosnail.basic.controller;

import com.jfinal.core.Controller;
import com.twosnail.basic.model.SysInfoUser;
import com.twosnail.basic.util.result.ResultObj;

/**   
 * @Title: IndexController.java
 * @Description: 登录、首页
 * @author 两只蜗牛   
 * @date 2015年4月17日 上午9:26:37 
 * @version V1.0   
 */
public class IndexController extends Controller {
	/**
	 * 登录首页
	 */
	public void index() {
		render("login.html");
	}
	
	/**
	 * 登录页面
	 */
	public void login(){
		index() ;
	}
	
	/**
	 * 登录校验
	 */
	public void check(){
		try {
			SysInfoUser.me.userLogin( 
					getPara("loginName"), getPara("password"), getPara( "code" )  , getRequest() );
			//获取上次登录时间-->第一次登录、上次登录时间为0
			long lastTime = 0;
			//List<SysLogLog> logs = logService.getlogByUserId(UserInfo.getUserId(session));
			//if(logs!=null && logs.size()>0){
			//	lastTime = logs.get(0).getLoginTime();
			//}
			//SysLogLog sysLogLog = new SysLogLog();
			/*sysLogLog.setUserId(UserInfo.getUserId(session));
			sysLogLog.setLoginTime(System.currentTimeMillis());
			sysLogLog.setLogIp(RequestHandler.getIpAddr(request));
			sysLogLog.setLastlogTime(lastTime);
			sysLogLog.setStatus(1);
			long logId = logService.addRoleInfo(sysLogLog);
			session.setAttribute("logId", logId);*/
			renderJson( new ResultObj( ResultObj.SUCCESS , null, null ) );
		} catch( Exception e ) {
			e.printStackTrace();
			renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		}
	}
	
	/**
	 * 
	 */
	public void main() {
		render("main.html");
	}
	
	/**
	 * 内容
	 */
	public void content() {
		render("content.html");
	}
}



