package com.ex.sdk.utils.mgr;

import android.util.Log;

/**
 * @ClassName: MgrLog
 * @Description: MgrLog 管理类
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public class MgrLog {

	public String mTag = "Demo"; // 便签
	public boolean mDebug = true; // 是否开启调试

	/**
	 * 创建者
	 */
	private static class LogHolder {

		private static final MgrLog mgr = new MgrLog();
	}

	/**
	 * 获取当前实例
	 * 
	 * @return
	 */
	public static MgrLog getInstance() {

		return LogHolder.mgr;
	}

	/**
	 * 设置是否开启调试
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {

		mDebug = debug;
	}

	/**
	 * 这是便签
	 * 
	 * @param tag
	 */
	public void setTag(String tag) {

		mTag = tag;
	}

	public void d(String msg) {

		if (mDebug) {
			Log.d(mTag, msg);
		}
	}

	public void w(String msg) {

		if (mDebug) {
			Log.w(mTag, msg);
		}
	}

	public void i(String msg) {

		if (mDebug) {
			Log.i(mTag, msg);
		}
	}

	public void e(String msg) {

		if (mDebug) {
			Log.e(mTag, msg);
		}
	}

	public void e(String msg, Throwable e) {

		if (mDebug) {
			Log.e(mTag, msg, e);
		}
	}

}
