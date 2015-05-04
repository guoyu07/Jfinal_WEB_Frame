package com.twosnail.basic.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**   
 * @Title: AuthInterceptor.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月30日 下午5:34:54 
 * @version V1.0.0   
 */
public class AuthInterceptor implements Interceptor{

	
	@Override
	public void intercept(ActionInvocation ai) {
		
		Controller controller = ai.getController();
		
		Subject subject = SecurityUtils.getSubject() ;
		if( subject != null && subject.isAuthenticated()) {
			ai.invoke();
		} else {
			controller.redirect( "/login" );
		}
	}

}
