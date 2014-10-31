package com.ex.app.demo.ui.widget;

import android.app.ProgressDialog;
import android.content.Context;

public class DeProcessDialog {

	private static ProgressDialog mPDialog;
	private static Context mContext;

	private static ProgressDialog getDialog(Context context) {

		if (mPDialog != null) {
			mPDialog.dismiss();
		}

		mContext = context;
		mPDialog = new ProgressDialog(mContext);

		return mPDialog;
	}

	public static void show(Context context, String text) {

		ProgressDialog pDialog = getDialog(mContext);

		if (pDialog != null) {
			pDialog.setMessage(text);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
	}

	public static void hide(Context context) {

		ProgressDialog pDialog = getDialog(mContext);

		if (pDialog != null) {
			pDialog.dismiss();
		}
	}

}
