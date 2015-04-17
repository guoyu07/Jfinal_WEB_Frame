package com.twosnail.basic.util;

import com.jfinal.config.Routes;
import com.twosnail.basic.controller.IndexController;
import com.twosnail.basic.controller.LoginController;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add( "/" , IndexController.class ) ;
		add( "/user/login" , LoginController.class ) ;
		add( "/user/login" , LoginController.class ) ;
	}

}
