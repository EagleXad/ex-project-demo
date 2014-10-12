package com.ex.sdk.lib;

import android.app.Activity;

import com.ex.sdk.lib.analytics.MgrAnalytics;
import com.ex.sdk.lib.push.MgrPush;
import com.ex.sdk.lib.share.MgrSharer;

public class Ex {

	public static MgrSharer Share(Activity activity) {

		return MgrSharer.getInstance(activity);
	}

	public static MgrPush Push(Activity activity) {

		return MgrPush.getInstance(activity);
	}

	public static MgrAnalytics Analytics(Activity activity) {

		return MgrAnalytics.getInstance(activity);
	}

}
