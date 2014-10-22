package com.ex.sdk.utils.mgr;

import android.content.Context;

public class MgrNotification {

	private static Context mContext;

	private static class NotificationHolder {

		private static final MgrNotification mgr = new MgrNotification();
	}

	public static MgrNotification getInstance(Context context) {

		mContext = context;

		return NotificationHolder.mgr;
	}

}
