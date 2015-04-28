package com.twosnail.basic.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.twosnail.basic.ext.CaptchaRender;
import com.twosnail.basic.model.SysLoginLog;
import com.twosnail.basic.model.SysMenu;
import com.twosnail.basic.model.SysUser;
import com.twosnail.basic.util.RequestHandler;
import com.twosnail.basic.util.exception.BusiException;
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
	
	private Logger logger = Logger.getLogger(IndexController.class) ;
	
	public void index() {
		render("login.html");
	}
	
	public void login(){
		index() ;
	}
	
	public void img(){
		CaptchaRender img = new CaptchaRender(4); 
		this.setSessionAttr( CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY, img.getMd5RandonCode() );
		render(img);
	}	
	
	public void check(){
		try {
			
			String captchaCode = getPara("code");
			Object objMd5RandomCode = this.getSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
            String md5RandomCode = null;
            if(objMd5RandomCode != null){
                md5RandomCode = objMd5RandomCode.toString();
                this.removeSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
            }
            if(!CaptchaRender.validate( md5RandomCode, captchaCode )){
            	renderJson( new ResultObj( ResultObj.FAIL , "验证码错误，请重新输入！" , null ) );
                return;
            }
			
			SysUser.me.userLogin( 
					getPara("loginName"), getPara("password") , getParaToBoolean("rm", true) , getSession() );
			
			//添加登录日志
			SysLoginLog.me.addLoginLog( getRequest() , getSession() );
			renderJson( new ResultObj( ResultObj.SUCCESS , null, null ) );
		} catch( BusiException e ) {
			logger.debug(  e.getMessage() );
			renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		} catch( Exception e ) {
			logger.warn( "系统异常，登录失败！" , e );
			renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		}
	}
	
	/**
	 * 后台主页
	 */
	public void main() {
		//菜单
		List<TreeNode<SysMenu>> list = SysMenu.me.getMenuTree() ;
        String tree = treeMenu( list,  new StringBuilder() ,  RequestHandler.getBasePath(getRequest()) ) ;
        setAttr( "tree", tree ) ;
        setAttr( "userName", getSessionAttr( "userName" ) ) ;
		render("main.html");
	}
	
	/**
	 * 内容
	 */
	public void content() {
		render("content.html");
	}
	
	/**
	 * 退出
	 */
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if ( currentUser.isAuthenticated() ) {
			currentUser.logout();
		}
		index();
	}
	
	
	public String treeMenu( List<TreeNode<SysMenu>> list , StringBuilder str , String basePath ){    	
    	SysMenu sysMenu = null ;
    	for (TreeNode<SysMenu> node : list){
			sysMenu = node.get() ;	
			str.append( "<li " ) ;
			if( -1 == sysMenu.getInt( "parentId" ) ) ;
				str.append( "class=\"admin-parent\" " ) ;
			str.append( ">" ) ;
			
			str.append( " <a class=\"am-cf\" " ) ;
			if( node.getChildren().size() > 0 ) {
				str.append( " data-am-collapse=\"{target: '#"+ sysMenu.get("id") +"'}\"" ) ;
				str.append( "><span class=\""+sysMenu.get("icon")+"\"></span> " ) ;
				str.append( sysMenu.get("name") ) ;
				str.append( "<span class=\"am-icon-angle-right am-fr am-margin-right\"></span>" ) ;
				str.append( "</a>" ) ;
				
				str.append("<ul class=\"am-list am-collapse admin-sidebar-sub am-in\" id=\""+ sysMenu.get("id") +"\">") ;
				treeMenu( node.getChildren() , str , basePath );
				str.append("</ul>") ;
				
			} else {
				str.append( " href="+ basePath + sysMenu.get("href") +" target=\"content\"  " ) ;
				str.append( "><span class=\""+sysMenu.get("icon")+"\"></span> " ) ;
				str.append( sysMenu.get("name") ) ;
				str.append( "</a>" ) ;
			}
			str.append( "</li>" ) ;
		}
		return str.toString() ;
	}
}



