package com.twosnail.basic.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {

	/**
	 * ftl模板
	 * @param request
	 * @param response
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @param root 参数
	 */
	public static void writeTemplate( 
			HttpServletRequest request , HttpServletResponse response , 
			String filePath , String fileName , Map<String, Object> root ) {
		
        //通过Freemaker的Configuration读取相应的ftl
		Configuration cfg = new Configuration();
        //设定去哪里读取相应的ftl模板文件
        cfg.setServletContextForTemplateLoading( request.getServletContext(), filePath );
        cfg.setClassicCompatible( true );
        Writer out = null ;
		try {
			
			//在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate( fileName );
            //写入映射内容
			out = response.getWriter();
			root.put( "rootPath" , RequestHandler.getBasePath(request) ) ;
			temp.process(root, out);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch ( TemplateException e) {
			
		} finally {
            try {
                if (out != null){
                	out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }	
}
