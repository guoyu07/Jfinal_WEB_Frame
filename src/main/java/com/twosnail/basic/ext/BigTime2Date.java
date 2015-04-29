package com.twosnail.basic.ext;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.beetl.core.Format;


/**   
 * @Title: BigTime2Date.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月29日 上午11:11:25 
 * @version V1.0.0   
 */
public class BigTime2Date implements Format{

	@Override
	public Object format(Object data, String pattern) {
		 if (data == null)
             return null;
		 
		SimpleDateFormat sdf=new SimpleDateFormat( pattern );  
		return sdf.format(new Date((long) data));
	}

}
