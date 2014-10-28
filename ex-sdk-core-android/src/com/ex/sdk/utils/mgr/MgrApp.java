package com.ex.sdk.utils.mgr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ex.sdk.utils.Ex;

/** 
* @ClassName: MgrApp 
* @Description: MgrApp 管理类
* @author Aloneter
* @date 2014-10-24 下午5:53:09 
* @Version 1.0
* 
*/
public class MgrApp {

	public static Context mContext; // 上下文

	/**
	 * 创建者
	 */
	private static class AppHolder {

		private static final MgrApp mgr = new MgrApp();
	}

	/**
	 * 获取当前实例
	 * 
	 * @param context
	 * @return
	 */
	public static MgrApp getInstance(Context context) {

		mContext = context;

		return AppHolder.mgr;
	}

	public void showSysKeyBord(View v) {

		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, 0);
	}

	public void hideSysKeyBord(View v) {

		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromInputMethod(v.getWindowToken(), 0);
	}

	public void callPhone(String phoneNumber) {

		mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
	}

	public void sendEmail(String[] emailAddr) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, emailAddr);
		intent.setType("plain/text");

		mContext.startActivity(Intent.createChooser(intent, "选择 Email 客户端"));
	}

	public void sendSMSMgr(String phoneNumber, String content) {

		SmsManager smsManager = SmsManager.getDefault();

		PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(), 0);

		if (content.length() > 70) {
			List<String> divideContents = smsManager.divideMessage(content);

			for (String text : divideContents) {
				smsManager.sendTextMessage(phoneNumber, null, text, sentIntent, null);
			}
		} else {
			smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
		}
	}

	public void sendSMS(String phoneNumber, String content) {

		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
		intent.putExtra("sms_body", content);

		mContext.startActivity(intent);
	}

	public void showMapByWeb() {

	}

	public void showPageByWeb(String url) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setData(Uri.parse(url));

		mContext.startActivity(intent);
	}

	public void openExcel(String path) {

		openFile(path, "application/vnd.ms-excel");
	}

	public void openPPT(String path) {

		openFile(path, "application/vnd.ms-powerpoint");
	}

	public void openWord(String path) {

		openFile(path, "application/msword");
	}

	public void openFile(String path, String type) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(new File(path)), type);

		try {
			mContext.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("WorldReadableFiles")
	@SuppressWarnings("deprecation")
	public boolean install(String filename) {

		if (Ex.String().isEmpty(filename)) {

			return false;
		}

		int result = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0);

		if (result == 0) {
			Toast.makeText(mContext, "请选中“允许安装非电子市场的应用程序选项”再试！", Toast.LENGTH_SHORT).show();

			Intent intent = new Intent();

			intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			mContext.startActivity(intent);
		} else {

			if (!filename.contains("//sdcard")) {

				String oldname = filename;
				filename = "sharetemp_" + System.currentTimeMillis();

				try {
					FileInputStream fis = new FileInputStream(oldname);
					FileOutputStream fos = mContext.openFileOutput(filename, Context.MODE_WORLD_READABLE);

					filename = mContext.getFilesDir() + "/" + filename;
					byte[] buffer = new byte[1024];
					int len = 0;

					while ((len = fis.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}

					buffer = null;
					fis.close();
					fis = null;
					fos.flush();
					fos.close();
					fos = null;
				} catch (IOException e) {
					e.printStackTrace();

					return false;
				}
			}

			Intent intent = new Intent(Intent.ACTION_VIEW);

			intent.setDataAndType(Uri.parse("file://" + filename), "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

			mContext.startActivity(intent);

			return true;
		}

		return false;
	}

	public boolean unInstall(String packageName) {

		if (Ex.String().isEmpty(packageName)) {

			return false;
		}

		Uri packageURI = Uri.parse("package:" + packageName);
		Intent i = new Intent(Intent.ACTION_DELETE, packageURI);

		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		((Activity) mContext).startActivityForResult(i, 10);

		return true;

	}

	public boolean run(String packageName) {

		try {

			mContext.getPackageManager().getPackageInfo(packageName, PackageManager.SIGNATURE_MATCH);

			try {
				Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);

				mContext.startActivity(intent);
			} catch (Exception e) {

				try {
					Toast.makeText(mContext, "运行失败,请检查该应用的类型!", Toast.LENGTH_SHORT).show();
				} catch (Exception ex) {
				}

				e.printStackTrace();

				return false;
			}

			return true;
		} catch (NameNotFoundException e) {
			e.printStackTrace();

			return false;
		}
	}

}
