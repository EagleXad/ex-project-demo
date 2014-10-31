/*      
 * Copyright (c) 2014 by EagleXad
 * Team: EagleXad 
 * Create: 2014-08-29
 */

package com.ex.sdk.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ex.sdk.core.callback.ExRequestCallback;
import com.ex.sdk.core.exception.ExException;
import com.ex.sdk.core.ible.ExNetIble;
import com.ex.sdk.core.ible.ExReceiverIble;
import com.ex.sdk.core.utils.Ex;
import com.ex.sdk.ext.lib.xutils.ViewUtils;

/**
 * @ClassName: ExBaseFragment
 * @Description: Ex Base Fragment
 * @author Aloneter
 * @date 2014-10-24 下午5:07:34
 * @Version 1.0
 * 
 */
@SuppressLint("HandlerLeak")
public abstract class ExBaseFragment extends Fragment {

	/**
	 * FINAL_显示加载框
	 */
	public static final int LOADING_DIALOG_SHOW = 100;
	/**
	 * FINAL_隐藏加载框
	 */
	public static final int LOADING_DIALOG_DISS = 101;

	private ExNetIble mNetIble; // 网络请求接口
	private ExReceiverIble mReceiverIble; // 广播处理接口

	protected ExBaseAcvitiy mAcvitiy;
	protected Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化全局对象
		mAcvitiy = (ExBaseAcvitiy) getActivity();
		mContext = getActivity().getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// 获取当前操作 View
		View root = inflater.inflate(exInitLayout(), null);
		// 初始化 XUtils 注解绑定控件
		ViewUtils.inject(this, root);

		// 实现类处理传入数据
		exInitBundle();
		// 实现类处理控件数据绑定
		exInitView(root);
		// 实现处理初始化完成之后
		exInitAfter();
		// 注册本地广播
		regiesterReceiver();

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// 注销本地广播
		unregiesterReceiver();
		// 移除 Handler 所有回调和消息
		mHandler.removeCallbacksAndMessages(null);
	}

	/**
	 * 初始化操作接口
	 * 
	 * @param netIble_网络请求处理接口
	 * @param receiverIble_广播处理
	 */
	protected void initIble(ExNetIble netIble, ExReceiverIble receiverIble) {

		mNetIble = netIble;
		mReceiverIble = receiverIble;
	}

	/**
	 * 启动任务请求数据
	 * 
	 * @param url_请求主地址信息
	 * @param what_请求标识码
	 */
	protected void startTask(String url, int what) {

		startTask(url, what, ExBaseAcvitiy.LOADING_DIALOG_SHOW);
	}

	/**
	 * 启动任务请求数据
	 * 
	 * @param url_请求主地址信息
	 * @param what_请求标识码
	 * @param isShow_是否显示加载框
	 */
	protected void startTask(final String url, final int what, final int isShow) {

		// 判断请求地址是否为空
		if (Ex.String().isEmpty(url)) {
			Ex.Toast(mContext).show("请求地址不能为空");

			return;
		}
		// 判断当前是否显示加载框
		if (isShow == ExBaseAcvitiy.LOADING_DIALOG_SHOW) {
			Ex.Dialog(mContext).showProgressDialog("", ExConstants.STR_LOADING_WARN);
		}
		// 接口回调回去操作参数
		Map<String, String> params = mNetIble.onStart(what);
		// 启动网络请求
		Ex.Net(mContext).sendAsyncPost(url, params, new ExRequestCallback() {

			@Override
			public void onSuccess(InputStream inStream, HashMap<String, String> cookies) {

				// 请求结果
				String result = "";
				// 判断结果流是否为空
				if (inStream != null) {
					// 转换流对象
					result = Ex.T().getInStream2Str(inStream);
				}
				// 创建消息对象
				Message msg = mHandler.obtainMessage();
				// 传入操作码
				msg.what = what;
				// 是否显示对话框
				msg.arg1 = isShow;
				// 请求结果码
				msg.arg2 = 0;
				// 请求结果
				msg.obj = result;
				// 发送消息
				mHandler.sendMessage(msg);
			}

			@Override
			public void onError(int statusCode, ExException e) {

				// 创建消息对象
				Message msg = mHandler.obtainMessage();
				// 传入操作码
				msg.what = what;
				// 是否显示对话框
				msg.arg1 = isShow;
				// 请求结果码
				msg.arg2 = statusCode;
				// 请求结果
				msg.obj = e.getMessage();
				// 发送消息
				mHandler.sendMessage(msg);
			}
		});
	}

	/**
	 * NEW_消息处理对象
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			// 获取结果值
			String result = msg.obj.toString();
			// 获取操作码
			int what = msg.what;
			// 获取是否显示对话框
			int arg1 = msg.arg1;
			// 获取请求结果码
			int arg2 = msg.arg2;
			// 判断是否显示对话框
			if (arg1 == ExBaseAcvitiy.LOADING_DIALOG_SHOW) {
				Ex.Dialog(mContext).dismissProgressDialog();
			}
			// 回调请求结果
			if (arg2 == 0) {
				mNetIble.onSuccess(what, result);
			} else {
				mNetIble.onError(what, arg2, result);
			}
		}
	};

	/**
	 * NEW_广播处理对象
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			// 回调广播结果
			mReceiverIble.onReceiver(intent);
		}
	};

	/**
	 * Method_注册本地广播
	 */
	private void regiesterReceiver() {

		IntentFilter intentFilter = new IntentFilter();

		// 获取设置 Action 数据对Action进行绑定
		String[] actions = exInitReceiver();

		if (actions != null) {
			for (int i = 0; i < actions.length; i++) {
				intentFilter.addAction(actions[i]);
			}
		}
		// 注册广播
		mContext.registerReceiver(mReceiver, intentFilter);
	}

	/**
	 * ethod_注销本地广播
	 */
	private void unregiesterReceiver() {

		if (mReceiver != null) {
			mContext.unregisterReceiver(mReceiver);
		}
	}

	/**
	 * ethod_初始化布局 ：对展示布局进行设置
	 * 
	 * @return 布局资源 ID
	 */
	protected abstract int exInitLayout();

	/**
	 * ethod_初始化传入参数 ：处理进入之前传入的数据
	 */
	protected abstract void exInitBundle();

	/**
	 * ethod_初始化控件参数： 在该方法中，可以对已绑定的控件数据初始化
	 */
	protected abstract void exInitView(View root);

	/**
	 * ethod_初始化之后： 在基础数据初始化完成之后可以使用该方法
	 */
	protected abstract void exInitAfter();

	/**
	 * ethod_初始化广播处理 Action： 注意传入数据使用全局常量管理
	 * 
	 * @return 处理 Action 数据
	 */
	protected abstract String[] exInitReceiver();

}
