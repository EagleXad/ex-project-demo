package com.ex.sdk.utils.mgr;

import android.content.Context;

import com.ex.sdk.core.callback.ExDownloadCallback;

public class MgrDownload {

	private static Context mContext; // 上下文

	/**
	 * 创建者
	 */
	private static class DownloadHolder {

		private static final MgrDownload mgr = new MgrDownload();
	}

	/**
	 * 获取当前实例
	 * 
	 * @param context
	 * @return
	 */
	public static MgrDownload getInstance(Context context) {

		mContext = context;

		return DownloadHolder.mgr;
	}

	public void start(String urlString, ExDownloadCallback callback) {

	}


}
