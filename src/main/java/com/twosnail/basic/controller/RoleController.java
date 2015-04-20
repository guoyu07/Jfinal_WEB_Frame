package com.twosnail.basic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.twosnail.basic.model.SysRole;
import com.twosnail.basic.model.SysUser;
import com.twosnail.basic.util.RequestHandler;
import com.twosnail.basic.util.exception.BusiException;
import com.twosnail.basic.util.result.ResultObj;
import com.twosnail.basic.util.tree.TreeNode;

/**   
 * @Title: RoleController.java
 * @Description: 用户 
 * @author 两只蜗牛   
 * @date 2015年4月17日 上午9:26:37 
 * @version V1.0   
 */
public class RoleController extends Controller {
	
	private Logger logger = Logger.getLogger(RoleController.class) ;
	
	public void index(){
		try {
			List<TreeNode<SysRole>> list = SysRole.me.getRoleList() ;
	        String tree = treeMenu( list,  new StringBuilder() ,  RequestHandler.getBasePath(getRequest()) ) ;
	        setAttr( "tree", tree ) ;
	        
		} catch (Exception e) {
			this.logger.warn( "角色列表信息，初始化失败！" , e );
		}
        render( "role_list.html" );
	}
	
	/**
     * 添加页面
     */
    public void addview(){
    	
    	int id = getParaToInt( "id" ) ;
        setAttr( "parentId" , id ) ;
        if( id == 0 ) {
        	
        } else if( id == -1 ) {
        	setAttr( "parentName" , "超级管理员" ) ;
        } else {
        	setAttr( "parentName" , SysRole.me.getRoleName(id)) ;
        }        
        
    	render( "role_add.html" ) ;
    }
    
    /**
     * 保存页面
     * @param infoUser
     * @param request
     * @param session
     * @return
     */
    public void add() {
    	
    	SysRole sysRole = getModel( SysRole.class ) ;
    	try {
            //添加角色信息
    		SysRole.me.addRole( sysRole );
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
    public void editview(){
        
    	try {
    		setAttr( "sysRole" , SysRole.me.getRoleById( getParaToInt("id") )  ) ;
		} catch (Exception e) {
			this.logger.warn( "修改角色信息，初始化失败！" , e ); 
		}
		render( "role_edit.html" ) ;
    }
    
    /**
     * 修改保存页面
     * @param infoUser
     * @param request
     * @return
     */
    public void edit(){
    	SysRole sysRole = getModel( SysRole.class ) ;
    	try {
    		SysRole.me.updRole(sysRole) ;
        	renderJson( new ResultObj( ResultObj.SUCCESS , "" , null )) ;
        	return ;
        } catch( BusiException e ) {
            this.logger.warn( "修改角色信息失败！" ,e );
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
    public void editstatus(){
    	int id  = getParaToInt( "id" ) ;
    	try {
        	SysRole sysRole= SysRole.me.getRoleById( id ) ;
            int isUsed = sysRole.getInt("isUsed") == 1 ? 0 : 1 ;
            sysRole.set( "isUsed" , isUsed );
            SysRole.me.updRoleStasus( sysRole );
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
    public void delview(){
    	int id  = getParaToInt( "id" ) ;
    	try {
			if( SysUser.me.checkUserById( id ) ){
	        	try {
	        		SysRole.me.delRoleTx( id );
	        		renderJson( new ResultObj( ResultObj.SUCCESS , "删除成功" , null )) ;
	        	} catch ( BusiException e ) {
	        		this.logger.warn( "删除失败！" , e );
	        		renderJson( new ResultObj( ResultObj.FAIL , "删除失败！" , null )) ;
	        	}
	        }else{
	        	renderJson( new ResultObj( ResultObj.FAIL , "该角色存在用户，不能删除" , null )) ;
	        }
		} catch (Exception e ) {
			this.logger.warn( "系统异常！" , e );
			renderJson( new ResultObj( ResultObj.FAIL , "系统异常！" , null )) ;
		}
    }
    
   
    
	public String treeMenu( List<TreeNode<SysRole>> list , StringBuilder str , String basePath ){    	
    	SysRole sysRole = null ;
    	for (TreeNode<SysRole> node : list){
			sysRole = node.get() ;	
			str.append( "<li " ) ;
			if( -1 == sysRole.getInt( "parentId" ) ) ;
				str.append( "class=\"admin-parent\" " ) ;
			str.append( ">" ) ;
			
			str.append( " <a class=\"am-cf\" onclick=\"setidValue("+sysRole.get("id")+");\"  " ) ;
			str.append( " href="+ basePath + "/sys/role/editview?id=" +sysRole.get("id") +" target=\"content_in\"  " ) ;
			if( node.getChildren().size() > 0 ) {
				
				str.append( " data-am-collapse=\"{target: '#"+ sysRole.get("id") +"'}\"" ) ;
				
				str.append( "><span class=\"am-icon-file\"></span> " ) ;
				str.append( sysRole.get("roleName") ) ;
				str.append( "<span class=\"am-icon-angle-right am-fr am-margin-right\"></span>" ) ;
				str.append( "</a>" ) ;
				
				str.append("<ul class=\"am-list am-collapse admin-sidebar-sub am-in\" id=\""+ sysRole.get("id") +"\">") ;
				treeMenu( node.getChildren() , str , basePath );
				str.append("</ul>") ;
				
			} else {
				str.append( "><span class=\"am-icon-file\"></span> " ) ;
				str.append( sysRole.get("roleName") ) ;
				str.append( "</a>" ) ;
			}
			str.append( "</li>" ) ;
		}
		return str.toString() ;
	}
	
	
}
