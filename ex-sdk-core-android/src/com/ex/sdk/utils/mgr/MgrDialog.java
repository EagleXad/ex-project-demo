package com.ex.sdk.utils.mgr;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/** 
* @ClassName: MgrDialog 
* @Description: MgrDialog 管理类 
* @author Aloneter
* @date 2014-10-24 下午5:52:42 
* @Version 1.0
* 
*/
public class MgrDialog {

	private static Context mContext;

	private static ProgressDialog mDialog;
	private static Dialog mAlertDialog;

	/**
	 * 创建者
	 */
	private static class DialogHolder {
		private static final MgrDialog mgr = new MgrDialog();
	}

	/**
	 * 获取当前实例
	 * 
	 * @param context
	 * @return
	 */
	public static MgrDialog getInstance(Context context) {

		mContext = context;

		return DialogHolder.mgr;
	}

	public void showProgressDialog(String title, String msg) {

		if (mDialog != null && mDialog.isShowing() == true) {

			return;
		}
		mDialog = ProgressDialog.show(mContext, title, msg, true, false);
	}

	public void showProgressDialog(int titleId, int msgId) {

		showProgressDialog(mContext.getString(titleId), mContext.getString(msgId));
	}

	public void dismissProgressDialog() {

		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	public boolean isDialogShowing() {

		if (mDialog != null) {

			return mDialog.isShowing();
		}

		return false;
	}

}
