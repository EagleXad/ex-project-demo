package com.ex.sdk.utils.mgr;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class MgrAndroid {

	private static Context mContext;

	private static class AndroidHolder {

		private static final MgrAndroid mgr = new MgrAndroid();
	}

	public static MgrAndroid getInstance(Context context) {

		mContext = context;

		return AndroidHolder.mgr;
	}

	private int getIdentifier(String name, String defType) {

		int result = 0;

		String skinPkgName = mContext.getPackageName();

		try {
			result = mContext.createPackageContext(skinPkgName, Context.CONTEXT_IGNORE_SECURITY).getResources().getIdentifier(name, defType, skinPkgName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int string(String name) {

		return getIdentifier(name, "string");
	}

	public int anim(String name) {

		return getIdentifier(name, "anim");
	}

	public int attr(String name) {

		return getIdentifier(name, "attr");
	}

	public int dimen(String name) {

		return getIdentifier(name, "dimen");
	}

	public int drawable(String name) {

		return getIdentifier(name, "drawable");
	}

	public int id(String name) {

		return getIdentifier(name, "id");
	}

	public int layout(String name) {

		return getIdentifier(name, "layout");
	}

	public int menu(String name) {

		return getIdentifier(name, "menu");
	}

	public int style(String name) {

		return getIdentifier(name, "style");
	}

	public int xml(String name) {

		return getIdentifier(name, "xml");
	}

}
