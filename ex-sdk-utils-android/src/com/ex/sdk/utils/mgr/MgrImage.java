package com.ex.sdk.utils.mgr;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Set;
import java.util.WeakHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;

import com.ex.sdk.core.callback.ExLoadImageCallback;
import com.ex.sdk.core.callback.ExRequestCallback;
import com.ex.sdk.core.exception.ExException;

/**
 * @ClassName: MgrImage
 * @Description: MgrImage 管理类
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public class MgrImage {

	private static Context mContext; // 上下文

	private WeakHashMap<String, WeakReference<Bitmap>> mImageCache; // 软引用缓存连接

	/**
	 * 创建者
	 */
	private static class ImageHolder {

		private static final MgrImage mgr = new MgrImage();
	}

	/**
	 * 获取当前实例
	 * 
	 * @param context
	 * @return
	 */
	public static MgrImage getInstance(Context context) {

		mContext = context;

		return ImageHolder.mgr;
	}

	/**
	 * 构造函数
	 */
	public MgrImage() {

		mImageCache = new WeakHashMap<String, WeakReference<Bitmap>>();
	}

	/**
	 * 获取缓存集合
	 * 
	 * @return
	 */
	public WeakHashMap<String, WeakReference<Bitmap>> getImageCache() {

		return mImageCache;
	}

	/**
	 * 加载图片
	 * 
	 * @param view
	 * @param url
	 * @param callback
	 */
	public void loadBitMap(View view, String url, ExLoadImageCallback callback) {

		if (MgrString.getInstance().isEmpty(url)) {

			return;
		}

		Bitmap temp = null;

		if (mImageCache.containsKey(url)) {
			temp = mImageCache.get(url).get();

			if (temp != null) {
				callback.onPostExecute(ExLoadImageCallback.LOAD_IMAGE_SUCCESS, view, temp);

				return;
			}
		}

		startTask(view, url, callback);
	}

	/**
	 * 清理图片
	 */
	public void clearBitMap() {

		Set<String> keys = mImageCache.keySet();

		while (keys.iterator().hasNext()) {
			clearBitMap(keys.iterator().next());
		}
	}

	/**
	 * 清理图片通过地址
	 * 
	 * @param url
	 */
	public void clearBitMap(String url) {

		WeakReference<Bitmap> sbitMap = mImageCache.get(url);

		if (sbitMap == null) {

			return;
		}

		Bitmap bitmap = sbitMap.get();

		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}

		mImageCache.remove(url);
	}

	/**
	 * 启动任务
	 * 
	 * @param view
	 * @param url
	 * @param callback
	 */
	private void startTask(View view, String url, ExLoadImageCallback callback) {

		new LoadImageTask(view, url, callback).execute();
	}

	public static class LoadImageTask extends AsyncTask<String, Integer, String> {

		private String tUrl; // 地址
		private int tRc; // 返回码
		private View tView; // 视图
		private Bitmap tBitmap; // Bitmap
		private ExLoadImageCallback tCallBack; // 函数回调

		/**
		 * 构造函数
		 * 
		 * @param view
		 * @param url
		 * @param callback
		 */
		public LoadImageTask(View view, String url, ExLoadImageCallback callback) {
			tCallBack = callback;
			tView = view;
			tUrl = url;
		}

		@Override
		protected String doInBackground(String... params) {

			final String md5Str = MgrMD5.getInstance().getMD5(tUrl);

			tBitmap = MgrCache.getInstance(mContext).getAsBitmap(md5Str);

			if (tBitmap != null) {

				return "OM";
			}

			MgrNet.getInstance(mContext).sendSyncGet(tUrl, new ExRequestCallback() {

				@Override
				public void onSuccess(InputStream inStream, HashMap<String, String> cookies) {

					if (inStream != null) {

						tRc = ExLoadImageCallback.LOAD_IMAGE_SUCCESS;

						tBitmap = BitmapFactory.decodeStream(inStream);

						MgrCache.getInstance(mContext).put(md5Str, tBitmap);
					}
				}

				@Override
				public void onError(int statusCode, ExException e) {

					tRc = statusCode;
				}
			});

			return "OM";
		}

		@Override
		protected void onPostExecute(String result) {

			if (result != null && result.equals("OM")) {
				MgrImage.getInstance(mContext).getImageCache().put(tUrl, new WeakReference<Bitmap>(tBitmap));

				tCallBack.onPostExecute(tRc, tView, tBitmap);
			}
		}
	}

}
