package com.ex.sdk.utils.exception;

/**
 * @ClassName: ExException
 * @Description: ExException 异常数据接口
 * @author Aloneter
 * @date 2014-8-26 下午4:06:25
 * @Version 1.0
 * 
 */
public class ExException extends Throwable {

	private static final long serialVersionUID = 1L; // 序列 ID

	/**
	 * 构造函数
	 */
	public ExException() {
	}

	/**
	 * 构造函数
	 * 
	 * @param detailMessage
	 */
	public ExException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * 构造函数
	 * 
	 * @param throwable
	 */
	public ExException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param detailMessage
	 * @param throwable
	 */
	public ExException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}