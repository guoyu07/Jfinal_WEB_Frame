package com.twosnail.init;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.twosnail.basic.model.SysButtonLog;
import com.twosnail.basic.model.SysInfoRole;
import com.twosnail.basic.model.SysInfoUrl;
import com.twosnail.basic.model.SysInfoUser;
import com.twosnail.basic.model.SysLogLog;
import com.twosnail.basic.model.SysPrivilege;
import com.twosnail.basic.util.AdminRoutes;
import com.twosnail.basic.util.RequestHandler;

import freemarker.template.TemplateModelException;

/**
 * API引导式配置
 */
public class SysConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("a_little_config.txt");
		//配置模板  
        me.setDevMode(getPropertyToBoolean("devModel", false));
        me.setViewType( ViewType.FREE_MARKER );
        me.setEncoding("UTF-8"); 
        me.setBaseViewPath( "/view" );
        me.setFreeMarkerViewExtension(".html");
        
        //
        try {
			FreeMarkerRender.getConfiguration().setSharedVariable( "rootPath" , "http://localhost:8080" );
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
        
        FreeMarkerRender.setProperty("template_update_delay", "0");//模板更更新时间,0表示每次都加载
        FreeMarkerRender.setProperty("classic_compatible", "true");//如果为null则转为空值,不需要再用!处理
        FreeMarkerRender.setProperty("whitespace_stripping", "true");//去除首尾多余空格
        FreeMarkerRender.setProperty("date_format", "yyyy-MM-dd");
        FreeMarkerRender.setProperty("time_format", "HH:mm:ss");
        FreeMarkerRender.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        FreeMarkerRender.setProperty("default_encoding", "UTF-8");
        //FreeMarkerRender.setProperties(properties);
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add( new AdminRoutes() );	// 后台视图地址映射
		me.add( new ExtRoutes() );	// 前台视图地址映射
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
		dp.addFilter(new StatFilter());
		WallFilter wall = new WallFilter();
		wall.setDbType(JdbcConstants.MYSQL);
		dp.addFilter(wall);
		me.add(dp);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		me.add(arp);
		// 映射表到模型
		arp.addMapping( "sysinfouser" , SysInfoUser.class ) ;
		arp.addMapping( "sysbuttonlog" , SysButtonLog.class ) ;
		arp.addMapping( "sysinforole" , SysInfoRole.class ) ;
		arp.addMapping( "sysinfourl" , SysInfoUrl.class ) ;
		arp.addMapping( "sysloglog" , SysLogLog.class ) ;
		arp.addMapping( "sysprivilege" , SysPrivilege.class ) ;
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
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
		JFinal.start("src/main/webapp", 8080, "/", 5);
	}
}
