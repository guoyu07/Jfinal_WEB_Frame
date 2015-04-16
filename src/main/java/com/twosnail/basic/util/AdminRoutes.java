package com.twosnail.basic.util;

import com.jfinal.config.Routes;
import com.twosnail.basic.controller.IndexController;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add( "/" , IndexController.class ) ;
		
	}

}
