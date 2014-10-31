/*      
 * Copyright (c) 2014 by EagleXad
 * Team: EagleXad 
 * Create: 2014-08-29
 */

package com.ex.sdk.core.utils.mgr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @ClassName: MgrT
 * @Description: MgrT 管理类
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public class MgrT {

	/**
	 * 创建者
	 */
	private static class tHolder {

		private static final MgrT mgr = new MgrT();
	}

	/**
	 * 获取当前实例对象
	 * 
	 * @return
	 */
	public static MgrT getInstance() {

		return tHolder.mgr;
	}

	/**
	 * 流转换字符串 (默认字符集)
	 * 
	 * @param in
	 * @return
	 */
	public String getInStream2Str(InputStream in) {

		return getInStream2Str(in, Charset.defaultCharset());
	}

	/**
	 * 流转换字符串 (字符串处理字符集)
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 */
	public String getInStream2Str(InputStream in, String encoding) {

		return getInStream2Str(in, Charset.forName(encoding));
	}

	/**
	 * 流转换字符串 (处理字符集)
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 */
	public String getInStream2Str(InputStream in, Charset encoding) {

		InputStreamReader input = new InputStreamReader(in, encoding);
		StringWriter output = new StringWriter();

		try {
			char[] buffer = new char[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output.toString();
	}

	/**
	 * Bitmap 转换字节流
	 * 
	 * @param bm
	 * @return
	 */
	public byte[] getBitmap2Bytes(Bitmap bm) {

		if (bm == null) {

			return null;
		}

		byte[] result = null;
		ByteArrayOutputStream baos = null;

		try {
			baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

			result = baos.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 字节流转换 Bitmap
	 * 
	 * @param b
	 * @return
	 */
	public Bitmap getBytes2Bitmap(byte[] b) {

		if (b.length == 0) {

			return null;
		}

		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/**
	 * Drawable 转换 Bitmap
	 * 
	 * @param d
	 * @return
	 */
	public Bitmap getDrawable2Bitmap(Drawable d) {

		if (d == null) {

			return null;
		}

		int w = d.getIntrinsicWidth();
		int h = d.getIntrinsicHeight();

		Bitmap.Config config = d.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;

		Bitmap b = Bitmap.createBitmap(w, h, config);

		Canvas canvas = new Canvas(b);

		d.setBounds(0, 0, w, h);
		d.draw(canvas);

		return b;
	}

	/**
	 * Bitmap 转换 Drawable
	 * 
	 * @param bm
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Drawable getBitmap2Drawable(Bitmap bm) {

		if (bm == null) {

			return null;
		}

		BitmapDrawable bd = new BitmapDrawable(bm);

		bd.setTargetDensity(bm.getDensity());

		return new BitmapDrawable(bm);
	}

	/**
	 * @param htmlStr
	 * @return
	 */
	public String getHtml2Text(String htmlStr) {

		String result = "";

		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;

		String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
		String regEx_html = "<[^>]+>";

		try {
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(regEx_html);
			htmlStr = m_html.replaceAll("");

			result = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 对象转换 Byte
	 * 
	 * @param obj
	 * @return
	 */
	public byte[] getObject2Byte(Object obj) {

		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;

		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);

			oos.writeObject(obj);

			oos.flush();

			bytes = bos.toByteArray();

			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return bytes;
	}

	/**
	 * Byte 转换 对象
	 * 
	 * @param bytes
	 * @return
	 */
	public Object getByte2Object(byte[] bytes) {

		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;

		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);

			obj = ois.readObject();

			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		return obj;
	}

	/**
	 * 字符串转换 Int
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public int getString2Int(String str, int defValue) {

		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}

		return defValue;
	}

	/**
	 * 字符串转换 long
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public long getString2Long(String str, long defValue) {

		try {
			return Long.parseLong(str);
		} catch (Exception e) {
		}

		return defValue;
	}

	/**
	 * 字符串转换 double
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public double getString2Double(String str, double defValue) {

		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}

		return defValue;
	}

	/**
	 * 字符串转换 float
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public float getString2Float(String str, float defValue) {

		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
		}

		return defValue;
	}

	/**
	 * 字符串转换 bool
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public boolean getString2Bool(String str, boolean defValue) {

		try {
			return Boolean.parseBoolean(str);
		} catch (Exception e) {
		}

		return defValue;
	}

	/**
	 * Dip 转换 Px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public int getDip2Px(Context context, float dpValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * Px 转换 Dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public int getPx2Dip(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * Px 转换 Sp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public int getPx2Sp(Context context, float pxValue) {

		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * Sp 转换 Px
	 * 
	 * @param context
	 * @param spValue
	 * @return
	 */
	public int getSp2Px(Context context, float spValue) {

		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (spValue * fontScale + 0.5f);
	}

}
