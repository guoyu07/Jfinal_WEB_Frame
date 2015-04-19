package com.twosnail.basic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.basic.model.SysInfoRole;
import com.twosnail.basic.model.SysInfoUser;
import com.twosnail.basic.util.FreemarkerUtil;
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
	
	public void index(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			String keyWord = getPara("keyWord") ;
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 3 : numPerPage;
	        
	        Page<Record> list = SysInfoUser.me.getUserInfo( -1 , getPara("keyWord"), pageNum, numPerPage );
	        List<SysInfoRole> roles = SysInfoRole.me.getSysRoles();
	        setAttr( "list", list );
	        setAttr( "roles", roles );
	        setAttr( "keyWord", keyWord );
	        
		} catch (Exception e) {
			this.logger.warn( "添加信息，初始化失败！" , e );
		}
        render( "user_list.html" );
	}
	
	/**
     * 添加页面
     */
    public void addview(){
    	render( "user_add.html" ) ;
    }
    
    /**
     * 添加页面
     */
    public void addinit(){
    	
        JSONObject result = new JSONObject() ;
        try {
        	List<SysInfoRole> roles = SysInfoRole.me.getSysRoles();
        	result.put( "roles" ,  roles ) ;
        	renderJson(new ResultObj( ResultObj.SUCCESS , "", result )) ;
		} catch (Exception e) {
			this.logger.warn( "添加用户信息初始化失败！" , e );
			renderJson(new ResultObj( ResultObj.FAIL , e.getMessage() , null ))  ;
		}
        
    }
    
    /**
     * 保存页面
     * @param infoUser
     * @param request
     * @param session
     * @return
     */
    public void addsave() {
    	
    	SysInfoUser infoUser = getModel(SysInfoUser.class) ;
        String dp = getPara("isDefPassWord");
        int isDefPas = dp == null ? 1 : 0 ;
        infoUser.set( "isDefPassWord" , isDefPas);
        if(isDefPas==0)
        	infoUser.set( "passWord" , "PassWord" );
        if( infoUser == null || infoUser.getInt("roleId") < 0 )
        	renderJson( new ResultObj( ResultObj.FAIL , "角色不能为空！" , null )) ;
        if( SysInfoUser.me.checkUserName( infoUser.getStr("userName")) )
        	renderJson( new ResultObj( ResultObj.FAIL , "新增失败，用户名存在！" , null ) ) ;
    	try {
             SysInfoUser.me.addUserInfo( infoUser , getRequest() );
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
    public void editview(){
        
        try {
        	List<SysInfoRole> roles = SysInfoRole.me.getSysRoles();
            SysInfoUser infoUser = SysInfoUser.me.getUserInfoById(getParaToInt("id")) ;        
            setAttr( "infoUser" , infoUser ) ;
            setAttr( "roles" ,  roles ) ;
		} catch (Exception e) {
			this.logger.warn( "修改信息，初始化信息失败！" , e );
		}
		render( "user_edit.html" ) ;
    }
    
    /**
     * 修改保存页面
     * @param infoUser
     * @param request
     * @return
     */
    public void editsave(){
    	
        String message = "" ;
        SysInfoUser infoUser = getModel(SysInfoUser.class ) ;
        try {
        	//角色编号不能为空
            if( infoUser == null || infoUser.get("") == null ){
            	renderJson(  new ResultObj( ResultObj.FAIL , "用户名不能为空！" , null ) );
            }
            SysInfoUser.me.updateSysInfoUser( infoUser , getRequest() ) ;
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
    public void upstatus(){
        try {
            SysInfoUser.me.updateSysInfoUserStasus( getParaToLong("id") , getParaToInt("isUsed") );
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
    public void delete(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		String[] ids =  id.split(",") ;
        		SysInfoUser.me.deleteSysInfoUserTx(  ids );
        	}            
        	renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
        } catch ( BusiException e ) {
            this.logger.info( "删除失败！", e );
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
	public void userInfoview( ){		
		Map<String, Object> root = new HashMap<String, Object>(3) ;
		try {
	        SysInfoUser info = SysInfoUser.me.getUserInfoById( getParaToInt("id") );
	        root.put( "info" , info ) ;
		} catch (Exception e) {
			this.logger.warn( "添加信息，初始化失败！" , e );
		}        
        //写入模板
        FreemarkerUtil.writeTemplate(
    			getRequest(), getResponse() , "WEB-INF/html/system/user", "user_info.ftl" , root ) ;
        render( "user_info.ftl" );
    }
    
    /**
     * 选择用户
     * @param id
     * @return
     */
    /*public ModelAndView infoChooseRole( Integer pageNum, String keyWord, Integer numPerPage, HttpSession session )
    {
        ModelAndView view = new ModelAndView( "/system/user/role_user_choose" );
        pageNum = pageNum == null ? 1 : pageNum;
        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 20 : numPerPage;
        PageList<Map> list = SysInfoUser.me.getUserInfo(1, -1 , keyWord, pageNum, numPerPage );
        List<Map> listMap =  list.getDatalist() ;
        for (Map<String, Object> map : listMap)
		{
			JSONObject obj = new JSONObject() ;
			obj.put( "ids", map.get ("id" )) ;
			obj.put( "userName" , map.get( "userName" ) ) ;
			obj.put( "id" , map.get( "id" ) ) ;
			map.put("checkbox", obj.toString().replace("\"", "\'")) ;
		}
        list.setDatalist( listMap ) ;
        view.addObject( "list", list );
        view.addObject( "keyWord", keyWord );
        return view ;
    }*/
	
	
}
