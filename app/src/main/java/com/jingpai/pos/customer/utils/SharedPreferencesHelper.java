package com.jingpai.pos.customer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jingpai.pos.customer.base.MyApplication;

import java.util.List;

/**
 * SharedPreferences使用帮助类
 */
public class SharedPreferencesHelper {

	private static SharedPreferencesHelper mSPHelper;    //实现单例

	private static Context mContext;                     //上下文

	//私有构造
	private SharedPreferencesHelper() {};

	/**
	 * 提供获得单例接口
	 *
	 * @return   实例对象
	 */
	public static SharedPreferencesHelper getInstance() {
		mContext = MyApplication.getContext();
		if (null == mSPHelper) {
			mSPHelper = new SharedPreferencesHelper();
		}
		mSPHelper.initConfig();
		return mSPHelper;
	}

	/**
	 * 配置文件名称
	 */
	private static final String SHARE_PREFERENCE = "city_life_shared_preference";

	/**
	 * 配置文件
	 */
	private SharedPreferences mSharedPreferences;
	private Editor editor;

	/**
	 * 初始化sharedPreferences、Editor
	 */
	public SharedPreferences initConfig() {
		if (mSharedPreferences == null)
			mSharedPreferences = mContext.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);
		if (editor == null)
			editor = mSharedPreferences.edit();
		return mSharedPreferences;
	}

	/**
	 * 释放sharedPreferences、Editor
	 */
	public void free() {
		if (mSharedPreferences != null) {
			mSharedPreferences = null;
		}
		if (editor != null) {
			editor = null;
		}
	}

	/**
	 * 存string类型的数据
	 *
	 * @param key
	 * @param data
	 */
	public void put(String key, String data) {
		editor.putString(key, data);
		editor.commit();
	}

	/**
	 * 存boolean类型的数据
	 *
	 * @param key
	 * @param data
	 */
	public void put(String key, Boolean data) {
		editor.putBoolean(key, data);
		editor.commit();
	}

	/**
	 * 存float类型的数据
	 *
	 * @param key
	 * @param data
	 */
	public void put(String key, float data) {
		editor.putFloat(key, data);
		editor.commit();
	}

	/**
	 * 存int类型的数据
	 *
	 * @param key
	 * @param data
	 */
	public void put(String key, int data) {
		editor.putInt(key, data);
		editor.commit();
	}

	/**
	 * 存string类型的数据
	 *
	 * @param key
	 * @param data
	 */
	public void put(String key, long data) {
		editor.putLong(key, data);
		editor.commit();
	}

	/**
	 * 取key对应的值，string类型
	 *
	 * @param key
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public String getString(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	/**
	 * 取key对应的值，boolean类型
	 *
	 * @param key
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	/**
	 * 取key对应的值，int类型
	 *
	 * @param key
	 * @param defaultValue 默认值
	 * @return
	 */
	public int getInt(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}

	/**
	 * 取key对应的值，float类型
	 *
	 * @param key
	 * @param defaultValue 默认值
	 * @return
	 */
	public float getFloat(String key, float defaultValue) {
		return mSharedPreferences.getFloat(key, defaultValue);
	}

	/**
	 * 取key对应的值，long类型
	 *
	 * @param key
	 * @param defaultValue  默认值
	 * @return
	 */
	public long getLong(String key, long defaultValue) {
		return mSharedPreferences.getLong(key, defaultValue);
	}

	/**
	 * 是否包含key
	 */
	public boolean hasKey(String key) {
		return mSharedPreferences.contains(key);
	}

	/**
	 * 删除key
     * @return
     */
	public String removeKey(String key) {
		editor.remove(key);
		editor.commit();
        return key;
    }
	public void clear() {
		editor.clear();
		editor.commit();
	}

	public <T> T getObject(String key, Class<T> clazz) {
		String value = getString(key, "");

		if (value.startsWith("{")) {
			return JSONObject.parseObject(value, clazz);
		}

		return null;
	}

	public <T> List<T> getObjectList(String key, Class<T> clazz) {
		String value = getString(key, "");
		if (value.startsWith("[")) {
			return JSONArray.parseArray(value, clazz);
		}

		return null;
	}

}
