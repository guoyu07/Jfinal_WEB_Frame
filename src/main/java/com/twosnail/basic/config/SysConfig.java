package com.twosnail.basic.config;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.twosnail.basic.model.SysButtonLog;
import com.twosnail.basic.model.SysRole;
import com.twosnail.basic.model.SysMenu;
import com.twosnail.basic.model.SysUser;
import com.twosnail.basic.model.SysLoginLog;
import com.twosnail.basic.model.SysPrivilege;
import com.twosnail.basic.util.tools.ShiroExt;
import com.twosnail.init.ExtRoutes;

/**
 * API引导式配置
 */
public class SysConfig extends JFinalConfig {
	
	private Routes routes;
	/**
	 * 配置常量
	 */
	public void configConstant( Constants me )  {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("jfinal.properties");
        me.setDevMode(getPropertyToBoolean("devModel", false));
        // Beetl
 		me.setMainRenderFactory(new BeetlRenderFactory());
 		GroupTemplate gt = BeetlRenderFactory.groupTemplate;
 		WebAppResourceLoader loader = (WebAppResourceLoader ) gt.getResourceLoader(); 
 		loader.setRoot("src/main/webapp/view");
 		gt.registerFunctionPackage("so", new ShiroExt());
 		
 		//全局变量
 		Map<String, Object> sharedVars = new HashMap<String, Object>();
 		sharedVars.put("rootPath", "http://localhost:8082") ;
 		gt.setSharedVars(sharedVars);
 		
 		//error
 		me.setError404View("error/404.html");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		this.routes = me;
		me.add( new AdminRoutes() );	// 后台视图地址映射
		me.add( new ExtRoutes() );	// 前台视图地址映射
		
		/*this.routes = me;
		AutoBindRoutes routes = new AutoBindRoutes();
		routes.addExcludeClasses(Controller.class);
		me.add(routes);*/
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		//C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		//me.add(c3p0Plugin);
		
		//阿里巴巴 数据库连接池插件
		DruidPlugin dp = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		//dp.addFilter(new StatFilter());
		WallFilter wall = new WallFilter();
		wall.setDbType(JdbcConstants.MYSQL);
		dp.addFilter(wall);
		me.add(dp);
		
		
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setShowSql(getPropertyToBoolean("showSql", false));
		me.add(arp);
		// 映射表到模型
		arp.addMapping( "sys_user" , SysUser.class ) ;
		arp.addMapping( "sys_button_log" , SysButtonLog.class ) ;
		arp.addMapping( "sys_role" , SysRole.class ) ;
		arp.addMapping( "sys_menu" , SysMenu.class ) ;
		arp.addMapping( "sys_login_log" , SysLoginLog.class ) ;
		arp.addMapping( "sys_privilege" , SysPrivilege.class ) ;
		
		// 加载Shiro插件
		me.add(new ShiroPlugin(routes));
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8082, "/", 5);
	}
}
