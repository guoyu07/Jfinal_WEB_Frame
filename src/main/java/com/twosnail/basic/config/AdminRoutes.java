package com.twosnail.basic.config;

import com.jfinal.config.Routes;
import com.twosnail.basic.controller.IndexController;
import com.twosnail.basic.controller.LogController;
import com.twosnail.basic.controller.MenuController;
import com.twosnail.basic.controller.RoleController;
import com.twosnail.basic.controller.UserController;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add( "/" , IndexController.class  ) ;
		add( "/sys/user" , UserController.class  ) ;
		add( "/sys/role" , RoleController.class  ) ;
		add( "/sys/menu" , MenuController.class  ) ;
		add( "/sys/log" , LogController.class  ) ;
	}

}
