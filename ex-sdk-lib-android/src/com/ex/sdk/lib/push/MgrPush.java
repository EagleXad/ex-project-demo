package com.ex.sdk.lib.push;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.ex.sdk.lib.R;

public class MgrPush {

	private static final String BD_KEY_STRING = "EX_PUSH";
	private static final String BD_TAG_STRING = "EX_PUSH_TAG";

	private static final String BD_PRE_FILE_STRING = "EX_PRE_FILE";
	private static final String BD_PRE_TAG_STRING = "EX_PRE_TAG";

	private static Activity mActivity;

	private static class PushHolder {

		private static final MgrPush mgr = new MgrPush();

	}

	public static MgrPush getInstance(Activity activity) {

		if (mActivity == null) {
			mActivity = activity;
			PushHolder.mgr.init();
		}

		return PushHolder.mgr;
	}

	private void init() {

		String apiKey = getMetaValue(mActivity, MgrPush.BD_KEY_STRING);
		PushManager.startWork(mActivity, PushConstants.LOGIN_TYPE_API_KEY, apiKey);

		bdNotificationMake();

		bdDeleteTag();

		bdSetTag();
	}

	public void bdNotificationMake() {

		Resources resource = mActivity.getResources();
		String pkgName = mActivity.getPackageName();

		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应

		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(mActivity, resource.getIdentifier("ex_sdk_push_notification", "layout", pkgName),
				resource.getIdentifier("notification_icon", "id", pkgName), resource.getIdentifier("notification_title", "id", pkgName), resource.getIdentifier(
						"notification_text", "id", pkgName));

		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		cBuilder.setStatusbarIcon(R.drawable.ex_sdk_push_notification_icon);
		cBuilder.setLayoutDrawable(R.drawable.ex_sdk_push_notification_icon);

		PushManager.setNotificationBuilder(mActivity, 1, cBuilder);
	}

	public void bdSetTag() {

		List<String> tags = new ArrayList<String>();

		String tag = getMetaValue(mActivity, MgrPush.BD_TAG_STRING);

		tags.add(tag);

		PushManager.setTags(mActivity, tags);
	}

	@SuppressWarnings("unchecked")
	public void bdDeleteTag() {

		List<String> tags = null;

		tags = (List<String>) loadTags(mActivity);

		if (tags == null || tags.size() < 0) {

			return;
		}

		PushManager.delTags(mActivity, tags);
	}

	public static String getMetaValue(Context context, String metaKey) {

		Bundle metaData = null;
		String apiKey = null;

		if (context == null || metaKey == null) {

			return null;
		}

		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}

		return apiKey;
	}

	public static boolean hasBind(Context context) {

		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		String flag = sp.getString("bind_flag", "");

		if ("ok".equalsIgnoreCase(flag)) {

			return true;
		}

		return false;
	}

	public static void setBind(Context context, boolean flag) {

		String flagStr = "not";

		if (flag) {
			flagStr = "ok";
		}

		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

		Editor editor = sp.edit();
		editor.putString("bind_flag", flagStr);

		editor.commit();
	}

	public static void saveTags(Context context, Object obj) {

		String objStr = "";

		try {
			if (obj == null) {

				return;
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(obj);

			objStr = new String(baos.toByteArray());

			oos.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SharedPreferences sharedPre = context.getSharedPreferences(MgrPush.BD_PRE_FILE_STRING, 0);
		SharedPreferences.Editor editor = sharedPre.edit();

		editor.putString(MgrPush.BD_PRE_TAG_STRING, objStr);

		editor.commit();

	}

	public static Object loadTags(Context context) {
		Object obj = null;

		SharedPreferences sharedPre = context.getSharedPreferences(MgrPush.BD_PRE_FILE_STRING, 0);
		String objStr = sharedPre.getString(MgrPush.BD_PRE_TAG_STRING, "");

		if (objStr == null || objStr.length() == 0) {
			return obj;
		}
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		try {
			byte[] base64Bytes = objStr.getBytes();

			bais = new ByteArrayInputStream(base64Bytes);
			ois = new ObjectInputStream(bais);

			while (true) {
				obj = ois.readObject();
			}

		} catch (EOFException e) {
			return obj;
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return obj;
	}

}
