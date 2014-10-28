package com.ex.sdk.core.callback;

import com.ex.sdk.core.exception.ExException;

/**
 * @ClassName: ExDownloadCallback
 * @Description: ExDownloadCallback 回调接口
 * @author Aloneter
 * @date 2014-10-24 下午5:33:07
 * @Version 1.0
 * 
 */
public abstract class ExDownloadCallback {

	public static final int DOWN_LOAD_SUCCESS = 0; // 下载成功
	public static final int DOWN_LOAD_ERROR = -1; // 下载错误
	public static final int DOWN_LOAD_FAIL = 1; // 下载失败

	/**
	 * 下载成功回调
	 * 
	 * @param inStream
	 * @param cookies
	 */
	public abstract void onSuccess(int statusCode, String fileString);

	/**
	 * 下载失败回调
	 * 
	 * @param statusCode
	 * @param e
	 */
	public abstract void onError(int statusCode, ExException e);

}
