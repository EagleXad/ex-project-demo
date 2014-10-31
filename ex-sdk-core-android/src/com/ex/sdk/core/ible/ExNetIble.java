/*      
 * Copyright (c) 2014 by EagleXad
 * Team: EagleXad 
 * Create: 2014-08-29
 */

package com.ex.sdk.core.ible;

import java.util.Map;

/**
 * @ClassName: ExNetIble
 * @Description: 网络处理接口
 * @author Aloneter
 * @date 2014-10-24 下午5:34:40
 * @Version 1.0
 * 
 */
public interface ExNetIble {

	/**
	 * Method_启动处理回调
	 * 
	 * @param what
	 *            操作码
	 * @return 请求参数
	 */
	public Map<String, String> onStart(int what);

	/**
	 * Method_成功处理回调
	 * 
	 * @param what_操作码
	 * @param result_请求结果字符串
	 */
	public void onSuccess(int what, String result);

	/**
	 * Method_错误处理回调
	 * 
	 * @param what_操作码
	 * @param e_错误码
	 * @param message_错误信息
	 */
	public void onError(int what, int e, String message);

}
