package com.twosnail.basic.config;

import com.jfinal.config.Routes;
import com.twosnail.basic.controller.BtnLogController;
import com.twosnail.basic.controller.ButtonController;
import com.twosnail.basic.controller.IndexController;
import com.twosnail.basic.controller.LoginLogController;
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
		add( "/sys/btn" , ButtonController.class  ) ;
		add( "/sys/log/login" , LoginLogController.class  ) ;
		add( "/sys/log/btn" , BtnLogController.class  ) ;
	}

}
