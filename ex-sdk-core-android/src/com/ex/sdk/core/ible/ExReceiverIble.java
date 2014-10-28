package com.ex.sdk.core.ible;

import android.content.Intent;

/**
 * @ClassName: ExReceiverIble
 * @Description: 广播处理接口
 * @author Aloneter
 * @date 2014-10-24 下午5:35:03
 * @Version 1.0
 * 
 */
public interface ExReceiverIble {

	/**
	 * 广播接口回调
	 * 
	 * @param intent
	 *            操作意图对象
	 */
	public void onReceiver(Intent intent);

}
