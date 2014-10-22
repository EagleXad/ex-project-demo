package com.ex.sdk.core.callback;

import android.view.View;

/**
 * @ClassName: ExLoadImageCallback
 * @Description: ExLoadImageCallback 回调接口
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public abstract class ExLoadImageCallback {

	public static final int LOAD_IMAGE_SUCCESS = 0; // 图片加载成功
	public static final int LOAD_IMAGE_FAIL = -1; // 图片加载事变

	/**
	 * 请求执行结果回调
	 * 
	 * @param code
	 * @param view
	 * @param data
	 */
	public abstract void onPostExecute(int code, View view, Object data);
	
}
