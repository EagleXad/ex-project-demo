package com.ex.sdk.utils;

import android.content.Context;

import com.ex.sdk.utils.mgr.MgrCache;
import com.ex.sdk.utils.mgr.MgrDevice;
import com.ex.sdk.utils.mgr.MgrFile;
import com.ex.sdk.utils.mgr.MgrImage;
import com.ex.sdk.utils.mgr.MgrLog;
import com.ex.sdk.utils.mgr.MgrMD5;
import com.ex.sdk.utils.mgr.MgrNet;
import com.ex.sdk.utils.mgr.MgrPerference;
import com.ex.sdk.utils.mgr.MgrString;
import com.ex.sdk.utils.mgr.MgrT;
import com.ex.sdk.utils.mgr.MgrThread;

/**
 * @ClassName: Ex
 * @Description: Ex 管理类
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public class Ex {

	/**
	 * Ex 设备管理
	 */
	public static MgrDevice Device(Context context) {

		return MgrDevice.getInstance(context);
	}

	/**
	 * Ex 图片管理
	 */
	public static MgrImage Image(Context context) {

		return MgrImage.getInstance(context);
	}

	/**
	 * OM 缓存管理
	 */
	public static MgrCache Cache(Context context) {

		return MgrCache.getInstance(context);
	}

	/**
	 * OM 配置管理
	 */
	public static MgrPerference Perference(Context context) {

		return MgrPerference.getInstance(context);
	}

	/**
	 * Ex 网络管理
	 */
	public static MgrNet Net(Context context) {

		return MgrNet.getInstance(context);

	}

	/**
	 * Ex 字符处理
	 */
	public static MgrString String() {

		return MgrString.getInstance();
	}

	/**
	 * Ex 日志管理
	 */
	public static MgrLog Log() {

		return MgrLog.getInstance();

	}

	/**
	 * Ex 文件管理
	 */
	public static MgrFile File() {

		return MgrFile.getInstance();

	}

	/**
	 * Ex 转换管理
	 */
	public static MgrT T() {

		return MgrT.getInstance();
	}

	/**
	 * Ex 线程管理
	 */
	public static MgrThread Thread() {

		return MgrThread.getInstance();

	}

	/**
	 * Ex MD5 管理
	 */
	public static MgrMD5 MD5() {

		return MgrMD5.getInstance();
	}

}
