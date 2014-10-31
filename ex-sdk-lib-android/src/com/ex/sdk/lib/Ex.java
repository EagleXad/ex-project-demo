package com.ex.sdk.lib;

import android.app.Activity;

import com.ex.sdk.lib.analytics.MgrAnalytics;
import com.ex.sdk.lib.barcode.MgrQuer;
import com.ex.sdk.lib.push.MgrPush;
import com.ex.sdk.lib.share.MgrSharer;

/**
 * @ClassName: Ex
 * @Description: Ex 管理类
 * @author Aloneter
 * @date 2014-10-31 上午11:07:52
 * @Version 1.0
 * 
 */
public class Ex {

	/**
	 * Ex 分享管理
	 */
	public static MgrSharer Share(Activity activity) {

		return MgrSharer.getInstance(activity);
	}

	/**
	 * Ex 推送管理
	 */
	public static MgrPush Push(Activity activity) {

		return MgrPush.getInstance(activity);
	}

	/**
	 * Ex 统计管理
	 */
	public static MgrAnalytics Analytics(Activity activity) {

		return MgrAnalytics.getInstance(activity);
	}

	/**
	 * Ex 二维码管理
	 */
	public static MgrQuer Quer() {

		return MgrQuer.getInstance();
	}

}
