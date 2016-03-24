package com.cifpay.cifpaylib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * 封装gson的工具类库,该工具类依托于GSON.jar
 * 
 * @author chenkb
 * 
 * @createAt 2015-3-17下午1:59:41
 */
public class JsonUtil {
	public static Gson gson = new Gson();
	/**
	 * Mode转Json字符串
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static synchronized <T> String getJsonStr(T t) {

		return gson.toJson(t);
	}

	/**
	 * Json转对象，可以是mode,也可以是mode集合
	 * 
	 * @param <T>
	 * @param gsonStr
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T> T getMode(String gsonStr, Type type) {
		return (T) gson.fromJson(gsonStr, type);
	}

	@SuppressWarnings("unchecked")
	public static synchronized <T> T getMode2(String gsonStr, Type type) {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
				.create();

		return (T) gson.fromJson(gsonStr, type);
	}

	public static <T> T parseObject(String gsonStr, Class<T> className){
		return gson.fromJson(gsonStr,className);
	}

//	public static <T> List<T> getList(String gsonStr, Object object ){
//		List<T> objects = gson.fromJson(gsonStr, new TypeToken<List<T>>(){}.getType());
//		for(int i = 0; i < objects.size() ; i++)
//		{
//			Object obj = objects.get(i);
//			objects.add(obj);
//		}
//		return objects;
//	}

}
