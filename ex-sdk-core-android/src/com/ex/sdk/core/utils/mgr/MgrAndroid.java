/*      
 * Copyright (c) 2014 by EagleXad
 * Team: EagleXad 
 * Create: 2014-08-29
 */

package com.ex.sdk.core.utils.mgr;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @ClassName: MgrAndroid
 * @Description: MgrAndroid 管理类
 * @author Aloneter
 * @date 2014-10-24 下午5:52:58
 * @Version 1.0
 * 
 */
public class MgrAndroid {

	private static Context mContext;

	/**
	 * 创建者
	 */
	private static class AndroidHolder {

		private static final MgrAndroid mgr = new MgrAndroid();
	}

	/**
	 * 获取当前实例
	 * 
	 * @param context
	 * @return
	 */
	public static MgrAndroid getInstance(Context context) {

		mContext = context;

		return AndroidHolder.mgr;
	}

	/**
	 * Method_通过名称和类型获取 ID
	 * 
	 * @param name_名称
	 * @param defType_类型
	 * @return 资源 ID
	 */
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

	/**
	 * Method_stirng
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int string(String name) {

		return getIdentifier(name, "string");
	}

	/**
	 * Method_anim
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int anim(String name) {

		return getIdentifier(name, "anim");
	}

	/**
	 * Method_attr
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int attr(String name) {

		return getIdentifier(name, "attr");
	}

	/**
	 * Method_dimen
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int dimen(String name) {

		return getIdentifier(name, "dimen");
	}

	/**
	 * Method_drawable
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int drawable(String name) {

		return getIdentifier(name, "drawable");
	}

	/**
	 * Method_id
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int id(String name) {

		return getIdentifier(name, "id");
	}

	/**
	 * Method_layout
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int layout(String name) {

		return getIdentifier(name, "layout");
	}

	/**
	 * Method_menu
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int menu(String name) {

		return getIdentifier(name, "menu");
	}

	/**
	 * Method_style
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int style(String name) {

		return getIdentifier(name, "style");
	}

	/**
	 * Method_xml
	 * 
	 * @param name_资源名称
	 * @return 资源 ID
	 */
	public int xml(String name) {

		return getIdentifier(name, "xml");
	}

}
