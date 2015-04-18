package com.twosnail.basic.util.exception;
/**   
 * @Title: BusinessException.java
 * @Description: TODO
 * @author: 两只蜗牛
 * @date: 2015年4月18日 下午1:06:25
 * @version: V1.0
 */
public class BusiException extends Exception{

	private static final long serialVersionUID = 1L;

	public BusiException(String message){
		super(message);
	}
	
	public BusiException(Throwable e){
		super(e);
	}
	
	public BusiException(String message, Throwable e){
		super(message, e);
	}
	
}
