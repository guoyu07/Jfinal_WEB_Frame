package com.twosnail.basic.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.twosnail.basic.model.SysMenu;
import com.twosnail.basic.model.SysUser;
import com.twosnail.basic.util.RequestHandler;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.result.ResultObj;
import com.twosnail.basic.util.tree.TreeNode;

/**   
 * @Title: MenuController.java
 * @Description: 菜单 
 * @author 两只蜗牛   
 * @date 2015年4月17日 上午9:26:37 
 * @version V1.0   
 */
public class MenuController extends Controller {	
	private Logger logger = Logger.getLogger(MenuController.class) ;	
	
	//@RequiresRoles(value = { "user", "admin" }, logical = Logical.OR)
	@RequiresPermissions("MenuController")
	public void index(){
		try {
			List<TreeNode<SysMenu>> list = SysMenu.me.getMenuTree() ;
	        String tree = treeMenu( list,  new StringBuilder() ,  RequestHandler.getBasePath(getRequest()) ) ;
	        setAttr( "tree", tree ) ;
		} catch (Exception e) {
			this.logger.warn( "菜单列表信息，初始化失败！" , e );
		}
        render( "menu_list.html" );
	}
	
	/**
     * 添加页面
     */
	@RequiresPermissions("MenuController.addview")
    public void addview(){
    	
    	int id = getParaToInt( "id" ) ;
        setAttr( "parentId" , id ) ;
        setAttr( "parentName" , SysMenu.me.getMenuName(id)) ;
    	render( "menu_add.html" ) ;
    }
    
    /**
     * 保存页面
     * @param infoUser
     * @param request
     * @param session
     * @return
     */
    public void add() {
    	SysMenu sysMenu = getModel( SysMenu.class ) ;
    	try {
            //添加菜单信息
    		SysMenu.me.addMenu( sysMenu );
    		renderJson( new ResultObj( ResultObj.SUCCESS , "", null ) ) ;
    		return ;
        } catch( BusiException e ) {
            this.logger.warn( "添加失败！" , e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
        } catch (Exception e) {
        	this.logger.warn( "系统异常！" , e );
        	renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
		}
    }
    
    /**
     * 修改页面
     * @return
     */
    @RequiresPermissions("MenuController.editview")
    public void editview(){        
    	try {
    		setAttr( "sysMenu" , SysMenu.me.getMenuById( getParaToInt("id") )  ) ;
		} catch (Exception e) {
			this.logger.warn( "修改菜单信息，初始化失败！" , e ); 
		}
		render( "menu_edit.html" ) ;
    }
    
    /**
     * 修改保存页面
     * @param infoUser
     * @param request
     * @return
     */
    public void edit(){
    	SysMenu sysMenu = getModel( SysMenu.class ) ;
    	try {
    		SysMenu.me.updMenu(sysMenu) ;
        	renderJson( new ResultObj( ResultObj.SUCCESS , "" , null )) ;
        	return ;
        } catch( BusiException e ) {
            this.logger.warn( "修改菜单信息失败！" ,e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
            return ;
        } catch (Exception e) {
        	this.logger.warn( "系统异常！" , e );
        	renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
        	return ;
		}
    }
    

    /**
     * 修改状态
     * @param id
     * @param isUsed
     * @return
     */
    @RequiresPermissions("MenuController.editstatus")
    public void editstatus(){
    	int id  = getParaToInt( "id" ) ;
    	try {
        	SysMenu sysMenu= SysMenu.me.getMenuById( id ) ;
            int isUsed = sysMenu.getInt("isUsed") == 1 ? 0 : 1 ;
            sysMenu.set( "isUsed" , isUsed );
            SysMenu.me.updMenuStasus( sysMenu );
            renderJson( new ResultObj( ResultObj.SUCCESS , "" , null )) ;
        } catch ( BusiException e ) {
            this.logger.warn( e.getMessage() , e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
        } catch (Exception e) {
        	this.logger.warn( "系统异常！" , e );
        	renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
		}
    }
    
    /**
     * 删除
     * @return
     */
    @RequiresPermissions("MenuController.delview")
    public void delview(){
    	int id  = getParaToInt( "id" ) ;
    	try {
			if( SysUser.me.checkUserById( id ) ){
	        	try {
	        		SysMenu.me.delMenuTx( id );
	        		renderJson( new ResultObj( ResultObj.SUCCESS , "删除成功" , null )) ;
	        	} catch ( BusiException e ) {
	        		this.logger.warn( "删除失败！" , e );
	        		renderJson( new ResultObj( ResultObj.FAIL , "删除失败！" , null )) ;
	        	}
	        }else{
	        	renderJson( new ResultObj( ResultObj.FAIL , "该菜单存在菜单，不能删除" , null )) ;
	        }
		} catch (Exception e ) {
			this.logger.warn( "系统异常！" , e );
			renderJson( new ResultObj( ResultObj.FAIL , "系统异常！" , null )) ;
		}
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
			str.append( " href="+ basePath + "/sys/menu/editview?id=" +sysMenu.get("id") +" target=\"content_in\"  " ) ;
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
				str.append( "><span class=\""+sysMenu.get("icon")+"\"></span> " ) ;
				str.append( sysMenu.get("name") ) ;
				str.append( "</a>" ) ;
			}
			str.append( "</li>" ) ;
		}
		return str.toString() ;
	}
	
	
}
