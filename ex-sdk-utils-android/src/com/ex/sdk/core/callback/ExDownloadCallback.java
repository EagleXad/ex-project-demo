package com.ex.sdk.core.callback;

import com.ex.sdk.core.exception.ExException;

public abstract class ExDownloadCallback {

	public static final int DOWN_LOAD_SUCCESS = 0;
	public static final int DOWN_LOAD_ERROR = -1;
	public static final int DOWN_LOAD_FAIL = 1;

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
