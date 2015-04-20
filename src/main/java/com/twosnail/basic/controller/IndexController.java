package com.twosnail.basic.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.twosnail.basic.model.SysMenu;
import com.twosnail.basic.model.SysUser;
import com.twosnail.basic.util.RequestHandler;
import com.twosnail.basic.util.result.ResultObj;
import com.twosnail.basic.util.tree.TreeNode;

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
			SysUser.me.userLogin( 
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
	 * 后台主页
	 */
	public void main() {
		//菜单
		List<TreeNode<SysMenu>> list = SysMenu.me.getMenuList() ;
        String tree = treeMenu( list,  new StringBuilder() ,  RequestHandler.getBasePath(getRequest()) ) ;
        setAttr( "tree", tree ) ;
		
		
		
		
		
		
		
		
		
		render("main.html");
	}
	
	/**
	 * 内容
	 */
	public void content() {
		render("content.html");
	}
	
	
	public String treeMenu( List<TreeNode<SysMenu>> list , StringBuilder str , String basePath ){    	
    	SysMenu sysMenu = null ;
    	for (TreeNode<SysMenu> node : list){
			sysMenu = node.get() ;	
			str.append( "<li " ) ;
			if( -1 == sysMenu.getInt( "parentId" ) ) ;
				str.append( "class=\"admin-parent\" " ) ;
			str.append( ">" ) ;
			
			str.append( " <a class=\"am-cf\" onclick=\"setidValue("+sysMenu.get("id")+");\"  " ) ;
			if( node.getChildren().size() > 0 ) {
				str.append( " data-am-collapse=\"{target: '#"+ sysMenu.get("id") +"'}\"" ) ;
				str.append( "><span class=\"am-icon-file\"></span> " ) ;
				str.append( sysMenu.get("name") ) ;
				str.append( "<span class=\"am-icon-angle-right am-fr am-margin-right\"></span>" ) ;
				str.append( "</a>" ) ;
				
				str.append("<ul class=\"am-list am-collapse admin-sidebar-sub am-in\" id=\""+ sysMenu.get("id") +"\">") ;
				treeMenu( node.getChildren() , str , basePath );
				str.append("</ul>") ;
				
			} else {
				str.append( " href="+ basePath + sysMenu.get("href") +" target=\"content\"  " ) ;
				str.append( "><span class=\"am-icon-file\"></span> " ) ;
				str.append( sysMenu.get("name") ) ;
				str.append( "</a>" ) ;
			}
			str.append( "</li>" ) ;
		}
		return str.toString() ;
	}
}



