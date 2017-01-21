package com.flyersoft.sdk.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;

import com.flyersoft.sdk.config.Const;


/**
 * 工具类
 * 
 * @author mingsong.zhang
 * @date 2Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE+Context.
 *       MODE_MULTI_PROCESS12
 *       -Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE
 *       +Context.MODE_MULTI_PROCESS7-23
 */
public class Tools {

	/**
	 * 设置信息long
	 */
	public static void setInformain(String Key, long Value, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "setInformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);

		LogTools.showLog("hzj", "setInformain settings:" + settings);
		Editor editor = settings.edit();
		editor.putLong(Key, Value);
		editor.commit();
	}

	/**
	 * 取设置信息String
	 */
	public static long getLongInformain(String Key, long defValue, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "getInformain, get context = null");
			return defValue;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		try {
			return settings.getLong(Key, defValue);
		} catch (ClassCastException e) {
			// TODO: handle exception
			LogTools.showLog("hzj", "getLongInformain error Key:"+Key+" e:"+e.getMessage());
		}
		return defValue;
	}

	/**
	 * 设置信息String
	 */
	public static void setInformain(String Key, String Value, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "setInformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "setInformain settings:" + settings);
		Editor editor = settings.edit();
		editor.putString(Key, Value);
		editor.commit();		
	}

	/**
	 * 取设置信息String
	 */
	public static String getInformain(String Key, String defValue, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "getInformain, get context = null");
			return defValue;
		}

		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "getInformain settings:" + settings);
		try {
			return settings.getString(Key, defValue);
		} catch (ClassCastException e) {
			// TODO: handle exception
			LogTools.showLog("hzj", "String getInformain error Key:"+Key+" e:"+e.getMessage());
		}
		return defValue;
		
	}

	/**
	 * 取设置信息
	 */
	public static int getInformain(String key, int defValue, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "getInformain, get context = null");
			return defValue;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "getInformain settings:" + settings);
		try {
			return settings.getInt(key, defValue);
		} catch (ClassCastException e) {
			// TODO: handle exception
			LogTools.showLog("hzj", "int getInformain error Key:"+key+" e:"+e.getMessage());
		}
		return defValue;
		
	}

	/**
	 * 设置信息
	 */
	public static void setInformain(String key, int value, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "setInformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "setInformain settings:" + settings);
		Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 设置信息
	 */
	public static void setBooleaninformain(String key, boolean value, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "setBooleaninformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 取设置信息
	 */
	public static boolean getBooleanInformain(String key, boolean defaultValue, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "getBooleanInformain, get context = null");
			return defaultValue;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}

	/**
	 * 取设置信息
	 */
	public static int getInformain(String name, String key, int defaultValue, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "getInformain, get context = null");
			return defaultValue;
		}
		
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "getInformain settings:" + settings);
		try {
			return settings.getInt(key, defaultValue);
		} catch (ClassCastException e) {
			// TODO: handle exception
			LogTools.showLog("hzj", "int getInformain error Key:"+key+" e:"+e.getMessage());
		}
		return defaultValue;
	
	}

	/**
	 * 设置信息
	 */
	public static void setInformain(String name, String key, String value, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "setInformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "setInformain settings:" + settings);
		Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 取设置信息
	 */
	public static String getInformain(String name, String key, String defValue, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "getInformain, get context = null");
			return defValue;
		}
		
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "getInformain settings:" + settings);
		try {
			return settings.getString(key, defValue);
		} catch (ClassCastException e) {
			// TODO: handle exception
			LogTools.showLog("hzj", "getStringInformain error Key:"+key+" e:"+e.getMessage());
		}
		return defValue;

	}

	/**
	 * 设置信息
	 */
	public static void setInformain(String name, String key, int value,
									Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "setInformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		LogTools.showLog("hzj", "setInformain settings:" + settings);
		Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 清除settings.xml某个键值对
	 * 
	 * @author fei.zhang
	 * @date 2Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE+Context.
	 *       MODE_MULTI_PROCESS12-11-16
	 */
	public static void remove(String key, Context c) {
		Context context = getPackageContext(c);
		if(context == null) {
			LogTools.showLog("hzj", "remove informain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.SETTINGS, Context.MODE_PRIVATE);
		Editor editor = settings.edit().remove(key);
		editor.commit();
	}

	public static Context getPackageContext(Context c) {
		
		Context context = null;

		try {
			context = c.createPackageContext(Const.CONTEXT_PK_NAME,
					Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogTools.showLog("hzj", "getPackageContext:" + context + " c:" + c
					+ " ex:" + e.getMessage());
			context = c;
		} catch (Exception exception) {
			LogTools.showLog("hzj", "getPackageContext:" + context + " c:" + c
					+ " ex:" + exception.getMessage());
			context = c;
		}finally{
			if (context==null) {
				return c;
			}
		}
		return context;
	}
	
	public static Context getPackageContextByName(Context c, String pkName){
		Context context = null;

		try {
			context = c.createPackageContext(pkName,
					Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogTools.showLog("hzj", "getPackageContextByName:" + context + " c:" + c
					+ " ex:" + e.getMessage());
			context = c;
		} catch (Exception exception) {
			LogTools.showLog("hzj", "getPackageContextByName:" + context + " c:" + c
					+ " ex:" + exception.getMessage());
			context = c;
		}finally{
			if (context==null) {
				return c;
			}
		}
		return context;
	}
	
	
	/**
	 * 设置信息String(支持跨进程)
	 * @param  pkName   跨进程取信息,对应存信息的应用包名
	 */
	public static void setWorldInformain(String Key, String Value, Context c, String pkName) {
		Context context = getPackageContextByName(c,pkName);
		LogTools.showLog("WorldInformain", "setWorldInformain----------context:" + context+"----Context c="+c );
		if(context == null) {
			LogTools.showLog("hzj", "setInformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.WORLD_SETTINGS, Context.MODE_WORLD_READABLE
						| Context.MODE_WORLD_WRITEABLE| Context.MODE_MULTI_PROCESS);
		LogTools.showLog("hzj", "setInformain settings:" + settings);
		Editor editor = settings.edit();
		editor.putString(Key, Value);
		editor.commit();		
	}
	
	/**
	 * 取设置信息String(支持跨进程)
	 * @param  pkName   跨进程取信息,对应存信息的应用包名
	 */
	public static String getWorldInformain(String Key, String defValue, Context c, String pkName) {
		Context context = getPackageContextByName(c,pkName);
		LogTools.showLog("WorldInformain", "getWorldInformain---------context:" + context +"----Context c="+c);
		if(context == null) {
			LogTools.showLog("hzj", "getInformain, get context = null");
			return defValue;
		}

		SharedPreferences settings = context.getSharedPreferences(
				Const.WORLD_SETTINGS, Context.MODE_WORLD_READABLE| Context.MODE_WORLD_WRITEABLE
						| Context.MODE_MULTI_PROCESS);
		LogTools.showLog("hzj", "getInformain settings:" + settings);
		return settings.getString(Key, defValue);
	}
	
	
	/**
	 * 设置信息(支持跨进程)
	 * @param  pkName   跨进程取信息,对应存信息的应用包名
	 */
	public static void setWorldBooleaninformain(String key, boolean value, Context c, String pkName) {
		Context context = getPackageContextByName(c,pkName);
		LogTools.showLog("WorldInformain", "getWorldInformain---------context:" + context +"----Context c="+c);
		if(context == null) {
			LogTools.showLog("hzj", "setBooleaninformain, get context = null");
			return;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.WORLD_SETTINGS, Context.MODE_WORLD_READABLE
				| Context.MODE_WORLD_WRITEABLE| Context.MODE_MULTI_PROCESS);
		Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 取设置信息(支持跨进程)
	 * @param  pkName   跨进程取信息,对应存信息的应用包名
	 */
	public static boolean getWorldBooleanInformain(String key, boolean defaultValue, Context c, String pkName) {
//		Context context = getPackageContextByName(c,"com.inveno.qiku");
		Context context = getPackageContextByName(c,pkName);
		LogTools.showLog("WorldInformain", "getWorldInformain---------context:" + context +"----Context c="+c);
		if(context == null) {
			LogTools.showLog("hzj", "getBooleanInformain, get context = null");
			return defaultValue;
		}
		
		SharedPreferences settings = context.getSharedPreferences(
				Const.WORLD_SETTINGS, Context.MODE_WORLD_READABLE| Context.MODE_WORLD_WRITEABLE
				| Context.MODE_MULTI_PROCESS);
		return settings.getBoolean(key, defaultValue);
	}
	

	public static void saveStringToSharedPreference(Context mContext,
													String tableName, String key, String value) {
		if (null == mContext || null == key || key.length() < 1) {
			return;
		}
		SharedPreferences shared = mContext.getSharedPreferences(tableName, 0);
		Editor editor = shared.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getStringFromSharedPreference(Context mContext,
													   String tableName, String key) {
		String value = "";
		
		if (null == mContext || null == key || key.length() < 1) {
			return value;
		}
		
		SharedPreferences shared = mContext.getSharedPreferences(tableName, 0);
		value = shared.getString(key, "");
		return value;
	}
	
	public static long getLongFromSharedPreference(Context mContext,
												   String tableName, String key) {
		long value = 0;
		
		if (null == mContext || null == key || key.length() < 1) {
			return value;
		}
		
		SharedPreferences shared = mContext.getSharedPreferences(tableName, 0);
		value = shared.getLong(key, 0);
		return value;
	}
	
	public static void saveLongToSharedPreference(Context mContext,
												  String tableName, String key, long value) {
		if (null == mContext || null == key || key.length() < 1) {
			return;
		}
		SharedPreferences shared = mContext.getSharedPreferences(tableName, 0);
		Editor editor = shared.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
}
