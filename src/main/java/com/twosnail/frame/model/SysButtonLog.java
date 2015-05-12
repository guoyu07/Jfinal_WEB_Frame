package com.twosnail.frame.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.commin.exception.BuziException;

/**   
 * @Title: SysButtonLog.java
 * @Description: TODO 
 * @author 两只蜗牛   
 * @date 2015年4月17日 下午1:02:01 
 * @version V1.0   
 */

@SuppressWarnings("serial")
public class SysButtonLog extends Model<SysButtonLog>{
	public static final SysButtonLog me = new SysButtonLog() ; 
	
	/**
	 * 获取按钮日志
	 * @param keyWord
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Record> getButtonLog( String keyWord , int pageNumber, int pageSize) {
		StringBuffer sb = new StringBuffer(" FROM sys_button_log a WHERE 1=1 ");
		/*if( keyWord != null && "".equals( keyWord = keyWord.trim() ) ) {
			sb.append( " AND (a.userName LIKE '%"+keyWord+"%' or a.id LIKE '%"+keyWord+"%')" ) ;
		}*/
		
		return Db.paginate(pageNumber, pageSize, 
				"SELECT a.*,(select a1.userName from sys_user a1 WHERE a1.id = a.userId) userName " , sb.toString() );
			
	}
	
	public void delUserTx( String[] ids ) throws BuziException{
		for (String id : ids) {
			if( !me.deleteById( id ) ) {
	            throw new BuziException( "删除用户失败!" );
	        }
		}
    }
}
