package com.ex.sdk.core.callback;

import java.io.InputStream;
import java.util.HashMap;

import com.ex.sdk.core.exception.ExException;

/**
 * @ClassName: ExRequestCallback
 * @Description: ExRequestCallback 回调接口
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public abstract class ExRequestCallback {

	public static final int REQUEST_TIMEOUT = 408; // 网络请求超时
	public static final int REQUEST_FAIL = -1; // 网络请求失败
	public static final int REQUEST_ERROR = 1; // 网络请求错误
	public static final int REQUEST_UNAVAILABLE = 600; // 未联网

	/**
	 * 请求成功回调
	 * 
	 * @param inStream
	 * @param cookies
	 */
	public abstract void onSuccess(InputStream inStream, HashMap<String, String> cookies);

	/**
	 * 请求失败回调
	 * 
	 * @param statusCode
	 * @param e
	 */
	public abstract void onError(int statusCode, ExException e);

}
