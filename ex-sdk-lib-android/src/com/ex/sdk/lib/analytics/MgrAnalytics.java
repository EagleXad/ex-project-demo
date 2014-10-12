package com.ex.sdk.lib.analytics;

import android.app.Activity;

public class MgrAnalytics {

	private static Activity mActivity;

	private static class AnalyticsHolder {

		private static final MgrAnalytics mgr = new MgrAnalytics();
	}

	public static MgrAnalytics getInstance(Activity activity) {

		if (mActivity == null) {
			mActivity = activity;
		}

		return AnalyticsHolder.mgr;
	}

	private void init() {

	}

}
