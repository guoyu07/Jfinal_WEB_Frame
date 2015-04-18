package com.twosnail.basic.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <b>Description:</b></br> 
 * <b>Title:</b>信息化系统
 * @company:7dosoft
 * @author: tangXD
 * @Date: 2014-7-28下午5:08:01
 * @version 1.0
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface PermissionName
{
	public String value() default "";
}
