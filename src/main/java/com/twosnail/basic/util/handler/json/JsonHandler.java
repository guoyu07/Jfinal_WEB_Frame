package com.twosnail.basic.util.handler.json;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Logger;

/**
 * @author XUJUN
 * @date 2012-12-25
 * @version 1.0.0 
 * @note json帮助类
 */
public class JsonHandler {

	private final static Logger logger = Logger.getLogger( JsonHandler.class );
	
	/**
	 * 把model对象转换成JSONObject.
	 * @author XUJUN
	 * @date 2013-1-10
	 * @param obj model对象
	 * @return JSONObject
	 */
	public static JSONObject toJSONObject( Object obj ) {
	    return JSONObject.parseObject(obj.toString());
	}
	/**
	 * 把集合类型的转换成JSONArray.
	 * @author XUJUN
	 * @date 2013-1-10
	 * @param obj 集合类型的
	 * @return JSONArray
	 */
	public static JSONArray toJSONArray( Object obj ) {
	    return JSONArray.parseArray(obj.toString());
	}
	
	public static <T> T json2Bean( JSONObject json, Class<T> clazz ) {
		
		Field[] fields = clazz.getDeclaredFields();
		Class<?> type = null;
		try {
			T t = clazz.newInstance();;
			for( Field f : fields ) {
				type = f.getType();
				if( Modifier.isStatic( f.getModifiers() ) ) {
					continue;
				}
				
				f.setAccessible( true );
				if( type == int.class || type == Integer.class ) {
					f.set( t, getInt( json, f.getName() ) );
				} else if( type == double.class || type == Double.class ) {
					f.set( t, getDouble( json, f.getName() ) );
				} else if( type == float.class || type == Float.class ) {
					f.set( t, (float) getDouble( json, f.getName() ) );
				} else if( type == short.class || type == Short.class ) {
					f.set( t, (short) getInt( json, f.getName() ) );
				} else if( type == boolean.class || type == Boolean.class ) {
					f.set( t, getBoolean( json, f.getName(), false ) );
				} else if( type == byte.class || type == Byte.class ) {
					f.set( t, (byte) getInt( json, f.getName() ) );
				} else if( type == long.class || type == Long.class ) {
					f.set( t, getLong( json, f.getName() ) );
				} else if( type == String.class ) {
					f.set( t, getString( json, f.getName(), "" ) );
				}
			}
			return t;
		} catch ( Exception e ) {
			logger.warn( "", e );
			return null;
		}
		
	}
	
	public static <T> List<T> json2BeanList( JSONArray array, Class<T> clazz ) {
		
		int length = array.size();
		ArrayList<T> list = new ArrayList<T>();
		T t = null;
		for( int i = 0; i < length; i++ ) {
			t = json2Bean( array.getJSONObject( i ), clazz );
			if( t != null ) {
				list.add( t );
			}
		}
		
		return list;
	}
	
	public static Map<String, Object> json2Map(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.parseObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k); 
            //如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                List<Object> list = new ArrayList<Object>();
                Iterator<?> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    Object json2 = it.next();
                    list.add(json2);
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }
	

	
	public static int getInt( JSONObject json, String name ) {
		try {
			return json.getIntValue( name );
		} catch( JSONException e ) {
			return 0;
		}
	}
	
	public static int getInt( JSONObject json, String name, int defaultValue ) {
		try {
			return json.getIntValue( name );
		} catch( JSONException e ) {
			return defaultValue;
		}
	}

	public static double getDouble( JSONObject json, String name ) {
		try{
			return json.getDouble( name );
		} catch( JSONException e ) {
			return 0;
		}
	}
	
	public static long getLong( JSONObject json, String name ) {
		try {
			return json.getLong( name );
		} catch( JSONException e ) {
			return 0;
		}
	}
	
	public static String getString( JSONObject json, String name ) {
		try {
			return json.getString( name );
		} catch( JSONException e ) {
			return null;
		}
	}
	
	public static String getString( JSONObject json, String name, String defaultValue ) {
		try {
			return json.getString( name );
		} catch( JSONException e ) {
			return defaultValue;
		}
	}
	
	public static boolean getBoolean( JSONObject json, String name, boolean defaultValue ) {
		try {
			return json.getBoolean( name );
		} catch( JSONException e ) {
			return defaultValue;
		}
	}
	
	public static JSONObject getJSONObject( JSONObject json, String name ) {
		try {
			return json.getJSONObject( name );
		} catch( JSONException e ) {
			return null;
		}
	}
	
	public static JSONArray getJSONArray( JSONObject json, String name ) {
		try {
			return json.getJSONArray( name );
		} catch( JSONException e ) {
			return null;
		}
	}
	
}
