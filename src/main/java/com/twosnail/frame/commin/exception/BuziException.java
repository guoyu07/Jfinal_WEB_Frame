package com.twosnail.frame.commin.exception;
/**   
 * @Title: BuziException.java
 * @Description: TODO
 * @author: 两只蜗牛
 * @date: 2015年4月18日 下午1:06:25
 * @version: V1.0
 */
public class BuziException extends Exception{

	private static final long serialVersionUID = 1L;

	public BuziException(String message){
		super(message);
	}
	
	public BuziException(Throwable e){
		super(e);
	}
	
	public BuziException(String message, Throwable e){
		super(message, e);
	}
	
}
