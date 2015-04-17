package com.twosnail.basic.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;

/**   
 * @Title: LoginController.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月17日 上午9:26:37 
 * @version V1.0   
 */
public class LoginController extends Controller {
	
	public String login(){
		try {
			//this.loginService.userLogin( loginName, password,code, session ,request);
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
			System.out.println(getPara("loginName"));
			System.out.println(getPara("password"));
			System.out.println(getPara("code"));
			JSONObject result = new JSONObject();
			result.put( "statusCode", 200 );
			return result.toString();
		} catch( Exception e ) {
			JSONObject result = new JSONObject();
			result.put( "statusCode", 300 );
			result.put( "message", e.getMessage() );
			return result.toString();
		}
	}
	
}
