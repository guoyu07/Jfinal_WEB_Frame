package com.twosnail.basic.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.model.SysRole;
import com.twosnail.basic.model.SysUser;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.result.ResultObj;

/**   
 * @Title: UserController.java
 * @Description: 用户 
 * @author 两只蜗牛   
 * @date 2015年4月17日 上午9:26:37 
 * @version V1.0   
 */
public class UserController extends Controller {
	
	private Logger logger = Logger.getLogger(UserController.class) ;
	
	@RequiresPermissions("UserController")
	public void index(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			String keyWord = getPara("keyWord") ;
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        
	        Page<Record> list = SysUser.me.getUser( -1 , getPara("keyWord"), pageNum, numPerPage );
	        List<SysRole> roles = SysRole.me.getSysRoles();
	        setAttr( "list", list );
	        setAttr( "roles", roles );
	        setAttr( "keyWord", keyWord );
	        
		} catch (Exception e) {
			this.logger.warn( "用户列表信息，初始化失败！" , e );
		}
        render( "user_list.html" );
	}
	
	/**
     * 添加页面
     */
	@RequiresPermissions("UserController.addview")
    public void addview(){
    	render( "user_add.html" ) ;
    }
    
    /**
     * 添加页面
     */
    public void addinit(){
    	
        JSONObject result = new JSONObject() ;
        try {
        	List<SysRole> roles = SysRole.me.getSysRoles();
        	result.put( "roles" ,  roles ) ;
        	renderJson(new ResultObj( ResultObj.SUCCESS , "", result )) ;
		} catch (Exception e) {
			this.logger.warn( "添加用户信息初始化失败！" , e );
			renderJson(new ResultObj( ResultObj.FAIL , e.getMessage() , null ))  ;
		}
        
    }
    
    /**
     * 保存页面
     */
    public void addsave() {
    	
    	SysUser sysUser = getModel(SysUser.class) ;
        String dp = getPara("isDefPassWord");
        int isDefPas = dp == null ? 1 : 0 ;
        sysUser.set( "isDefPassWord" , isDefPas);
        if(isDefPas==0)
        	sysUser.set( "passWord" , "PassWord" );
        if( sysUser == null || sysUser.getInt("roleId") < 0 )
        	renderJson( new ResultObj( ResultObj.FAIL , "角色不能为空！" , null )) ;
        if( SysUser.me.checkUserName( sysUser.getStr("userName")) )
        	renderJson( new ResultObj( ResultObj.FAIL , "新增失败，用户名存在！" , null ) ) ;
    	try {
             SysUser.me.addUser( sysUser , getRequest() , getSession() );
             renderJson( new ResultObj( ResultObj.SUCCESS , "添加成功！" , null ) ) ;
         } catch( BusiException e ) {
             this.logger.warn( "新增用户失败" , e );
             renderJson( new ResultObj( ResultObj.FAIL , "添加失败"+e.getMessage() , null )) ;
         }
    }
    
    /**
     * 修改页面
     * @return
     */
    @RequiresPermissions("UserController.editview")
    public void editview(){
        
        try {
        	List<SysRole> roles = SysRole.me.getSysRoles();
            SysUser sysUser = SysUser.me.getUserById(getParaToInt("id")) ;        
            setAttr( "sysUser" , sysUser ) ;
            setAttr( "roles" ,  roles ) ;
		} catch (Exception e) {
			this.logger.warn( "修改信息，初始化信息失败！" , e );
		}
		render( "user_edit.html" ) ;
    }
    
    /**
     * 修改保存页面
     * @param sysUser
     * @param request
     * @return
     */
    public void editsave(){
    	
        String message = "" ;
        SysUser sysUser = getModel(SysUser.class ) ;
        try {
        	//角色编号不能为空
            if( sysUser == null || sysUser.get("") == null ){
            	renderJson(  new ResultObj( ResultObj.FAIL , "用户名不能为空！" , null ) );
            }
            SysUser.me.updUser( sysUser , getRequest() , getSession() ) ;
            renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) ) ;
            return ;
        } catch( BusiException e ) {
            this.logger.warn( "保存用户信息失败！"  , e );
            message = "保存用户信息失败！";
        } catch (Exception e) {
        	this.logger.warn( "保存用户信息,系统错误！"  , e );
        	message = "保存用户信息,系统错误！" ;
		}
        renderJson( new ResultObj( ResultObj.FAIL , message , null ) ) ;
    }
    

    /**
     * 修改状态
     * @param id
     * @param isUsed
     * @return
     */
    @RequiresPermissions("UserController.upstatus")
    public void upstatus(){
        try {
            SysUser.me.updUserStasus( getParaToLong("id") , getParaToInt("isUsed") );
            renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
            return ;
        } catch ( BusiException e ) {
            this.logger.warn( "修改状态失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
            return ;
        } catch ( Exception e ) {
            this.logger.warn( "系统异常，状态失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
            return ;
        }
    }
    
    /**
     * 删除
     * @return
     */
    @RequiresPermissions("UserController.delete")
    public void delete(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		String[] ids =  id.split(",") ;
        		SysUser.me.delUser(  ids );
        	}            
        	renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
        } catch ( BusiException e ) {
            this.logger.info( "删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
        } catch ( Exception e ) {
            this.logger.info( "系统异常，删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
        }
    }
    
    /**
     * 用户信息
     * @param id
     * @param request
     * @param response
     * @return
     */
	public void info(){		
		try {
	        setAttr( "sysUser" , SysUser.me.getUserById( getParaToInt("id") ) ) ;
		} catch (Exception e) {
			this.logger.warn( "添加信息，初始化失败！" , e );
		}
        render( "user_info.html" );
    }
	
}
