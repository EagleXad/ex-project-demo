package com.ex.sdk.utils.mgr;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * @ClassName: MgrFile
 * @Description: MgrFile 管理类
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public class MgrFile {

	/**
	 * 创建者
	 */
	private static class SpaceHolder {

		private static final MgrFile mgr = new MgrFile();
	}

	/**
	 * 获取当前实例
	 * 
	 * @return
	 */
	public static MgrFile getInstance() {

		return SpaceHolder.mgr;
	}

	/**
	 * 计算剩余空间
	 * 
	 * @param path
	 * @return
	 */
	private long getAvailableSize(String path) {

		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);

		return (long) fileStats.getAvailableBlocks() * fileStats.getBlockSize(); // 注意与fileStats.getFreeBlocks()的区别
	}

	/**
	 * 计算总空间
	 * 
	 * @param path
	 * @return
	 */
	private long getTotalSize(String path) {

		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);

		return (long) fileStats.getBlockCount() * fileStats.getBlockSize();
	}

	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return
	 */
	public long getSDAvailableSize() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			return getAvailableSize(Environment.getExternalStorageDirectory().toString());
		}

		return 0;
	}

	/**
	 * 计算系统的剩余空间
	 * 
	 * @return
	 */
	public long getSystemAvailableSize() {

		return getAvailableSize("/data");
	}

	/**
	 * 是否有足够的空间
	 * 
	 * @param filePath
	 * @return
	 */
	@SuppressLint("SdCardPath")
	public boolean hasEnoughMemory(String filePath) {

		File file = new File(filePath);
		long length = file.length();

		if (filePath.startsWith("/sdcard") || filePath.startsWith("/mnt/sdcard")) {

			return getSDAvailableSize() > length;
		} else {

			return getSystemAvailableSize() > length;
		}
	}

	/**
	 * 获取SD卡的总空间
	 * 
	 * @return
	 */
	public long getSDTotalSize() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			return getTotalSize(Environment.getExternalStorageDirectory().toString());
		}

		return 0;
	}

	/**
	 * 获取系统可读写的总空间
	 * 
	 * @return
	 */
	public long getSysTotalSize() {

		return getTotalSize("/data");
	}

	/**
	 * 是否有可用空间
	 * 
	 * @return
	 */
	public boolean isAvailableSpace() {

		if (getSDAvailableSize() / 1024 > 10 || getSystemAvailableSize() / 1024 > 10) {

			return true;
		}

		return false;
	}

	/**
	 * 将文件保存到本地
	 */
	public void saveFileCache(byte[] fileData, String folderPath, String fileName) {

		File folder = new File(folderPath);
		folder.mkdirs();

		File file = new File(folderPath, fileName);
		ByteArrayInputStream is = new ByteArrayInputStream(fileData);

		OutputStream os = null;

		if (!file.exists()) {

			try {
				file.createNewFile();
				os = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;

				while (-1 != (len = is.read(buffer))) {
					os.write(buffer, 0, len);
				}

				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				closeIO(is, os);
			}
		}
	}

	/**
	 * 从指定文件夹获取文件
	 */
	public File getSaveFile(String folder, String fileNmae) {

		File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + folder + "/" + fileNmae);

		return file.exists() ? file : null;
	}

	/**
	 * 获取文件夹路径
	 */
	public String getSavePath(String folderName) {

		File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + folderName + "/");
		file.mkdirs();

		return file.getAbsolutePath();
	}

	/**
	 * 复制文件
	 * 
	 * @param from
	 * @param to
	 */
	public void copyFile(File from, File to) {

		if (null == from || !from.exists()) {

			return;
		}
		if (null == to) {

			return;
		}
		InputStream is = null;
		OutputStream os = null;

		try {
			is = new FileInputStream(from);

			if (!to.exists()) {
				to.createNewFile();
			}

			os = new FileOutputStream(to);

			byte[] buffer = new byte[1024];
			int len = 0;

			while (-1 != (len = is.read(buffer))) {
				os.write(buffer, 0, len);
			}

			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeIO(is, os);
		}
	}

	/**
	 * 关闭流
	 * 
	 * @param closeables
	 */
	public void closeIO(Closeable... closeables) {

		if (null == closeables || closeables.length <= 0) {
			return;
		}

		for (Closeable cb : closeables) {

			try {

				if (null == cb) {

					continue;
				}

				cb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从文件中读取文本
	 * 
	 * @param filePath
	 * @return
	 */
	public String readFile(String filePath) {

		InputStream is = null;

		try {
			is = new FileInputStream(filePath);
		} catch (Exception e) {
		}

		return MgrT.getInstance().getInStream2Str(is);
	}

	/**
	 * 从assets中读取文本
	 * 
	 * @param name
	 * @return
	 */
	public String readFileFromAssets(Context context, String name) {

		InputStream is = null;

		try {
			is = context.getResources().getAssets().open(name);
		} catch (Exception e) {
		}

		return MgrT.getInstance().getInStream2Str(is);
	}

}
