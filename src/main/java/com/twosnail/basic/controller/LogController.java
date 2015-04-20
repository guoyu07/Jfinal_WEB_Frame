package com.twosnail.basic.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.model.SysButtonLog;
import com.twosnail.basic.model.SysLoginLog;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.result.ResultObj;

/**   
 * @Title: UserController.java
 * @Description: 用户 
 * @author 两只蜗牛   
 * @date 2015年4月17日 上午9:26:37 
 * @version V1.0   
 */
public class LogController extends Controller {
	
	private Logger logger = Logger.getLogger(LogController.class) ;
	
	/**
	 * 登录日志
	 */
	public void login(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			String keyWord = getPara("keyWord") ;
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        
	        Page<Record> list = SysLoginLog.me.getLoginLog(  getPara("keyWord"), pageNum, numPerPage );
	        setAttr( "list", list );
	        setAttr( "keyWord", keyWord );	        
		} catch (Exception e) {
			this.logger.warn( "用户列表信息，初始化失败！" , e );
		}
        render( "login_log_list.html" );
	}
	
	/**
     * 删除登录日志
     */
    public void dellogin(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		String[] ids =  id.split(",") ;
        		SysLoginLog.me.delUserTx(  ids );
        	}            
        	renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
        } catch ( BusiException e ) {
            this.logger.warn( "删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
        } catch (Exception e) {
        	this.logger.warn( "系统异常，删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		}
    }
	
	
	/**
	 * 按钮日志
	 */
	public void button(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			String keyWord = getPara("keyWord") ;
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        
	        Page<Record> list = SysButtonLog.me.getButtonLog(  getPara("keyWord"), pageNum, numPerPage );
	        setAttr( "list", list );
	        setAttr( "keyWord", keyWord );	        
		} catch (Exception e) {
			this.logger.warn( "用户列表信息，初始化失败！" , e );
		}
        render( "botton_log_list.html" );
	}
	
	/**
     * 删除操作日志
     */
    public void delbotton(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		String[] ids =  id.split(",") ;
        		SysButtonLog.me.delUserTx(  ids );
        	}            
        	renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
        } catch ( BusiException e ) {
            this.logger.warn( "删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
        } catch (Exception e) {
        	this.logger.warn( "系统异常，删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		}
    }
	
}
