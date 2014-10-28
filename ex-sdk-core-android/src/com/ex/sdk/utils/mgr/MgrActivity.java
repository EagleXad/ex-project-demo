package com.ex.sdk.utils.mgr;

import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @ClassName: MgrActivity
 * @Description: MgrActivity 管理类
 * @author Aloneter
 * @date 2014-10-24 下午5:53:20
 * @Version 1.0
 * 
 */
public class MgrActivity {

	private static CopyOnWriteArrayList<Activity> mActivities;
	private static Context mContext;

	/**
	 * 创建者
	 */
	private static class ActivityHolder {

		private static final MgrActivity mgr = new MgrActivity();
	}

	/**
	 * 获取当前实例
	 * 
	 * @param context
	 * @return
	 */
	public static MgrActivity getInstance(Context context) {

		mContext = context;

		return ActivityHolder.mgr;
	}

	public void add(Activity activity) {

		if (mActivities == null) {
			mActivities = new CopyOnWriteArrayList<Activity>();
		}

		mActivities.add(activity);
	}

	public Activity getCurrent() {

		Activity activity = mActivities.get(mActivities.size() - 1);

		return activity;
	}

	public void finish() {

		Activity activity = getCurrent();

		if (activity != null) {
			mActivities.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	public void finish(Activity activity) {

		if (activity != null) {
			mActivities.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	public void finish(Class<?> cls) {

		for (int i = 0; i < mActivities.size(); i++) {
			Activity activity = mActivities.get(i);

			if (activity != null && activity.getClass().equals(cls)) {
				finish(activity);
			}
		}
	}

	public void finishAll() {

		for (int i = 0; i < mActivities.size(); i++) {
			Activity activity = mActivities.get(i);

			if (activity != null) {
				finish(activity);
			}
		}

		mActivities.clear();
	}

	public void finishAllExceptLast() {

		for (int i = 0; i < mActivities.size(); i++) {
			Activity activity = mActivities.get(i);

			if (activity != null && !getCurrent().equals(activity)) {
				finish(activity);
			}
		}
	}

	public void exit() {

		try {
			finishAll();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(Intent intent) {

		mContext.startActivity(intent);
	}

	public void start(Class<?> cls) {

		start(cls, null);
	}

	public void start(Class<?> cls, Bundle bundle) {

		Intent intent = new Intent(mContext, cls);

		if (bundle != null) {
			intent.putExtras(bundle);
		}

		start(intent);
	}

	public void startForResult(Class<?> cls, int requestCode) {

		startForResult(cls, null, requestCode);
	}

	public void startForResult(Class<?> cls, Bundle bundle, int requestCode) {

		Intent intent = new Intent(mContext, cls);

		if (bundle != null) {
			intent.putExtras(bundle);
		}

		((Activity) mContext).startActivityForResult(intent, requestCode);
	}

	public void startNew(Class<?> cls) {

		startNew(cls, null);
	}

	public void startNew(Class<?> cls, Bundle bundle) {

		Intent intent = new Intent(mContext, cls);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (bundle != null) {
			intent.putExtras(bundle);
		}

		start(intent);
	}

}
