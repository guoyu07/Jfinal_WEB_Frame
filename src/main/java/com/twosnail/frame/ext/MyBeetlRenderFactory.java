package com.twosnail.frame.ext;

import org.beetl.ext.jfinal.BeetlRender;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.render.Render;

/**   
 * @Title: MyBeetlRenderFactory.java
 * @Description: TODO 
 * @author jason   
 * @date 2015年4月17日 下午12:30:12 
 * @version V1.0   
 */
public class MyBeetlRenderFactory extends BeetlRenderFactory{
	
	@Override  
    public Render getRender(String view) {  
        BeetlRender render=new BeetlRender(groupTemplate, view);  
        return render;  
    }  
    @Override  
    public String getViewExtension() {  
        return ".html";  
    }

}
