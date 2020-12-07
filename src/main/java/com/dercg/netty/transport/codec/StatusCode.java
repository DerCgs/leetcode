package com.dercg.netty.transport.codec;

public class StatusCode {

	/**
	 *	处理成功
	 */
	public static final byte SUCCESS = 1;

	/**
	 * 服务器端异常
	 */
	public static final byte EXCEPTION = -1;

	/**
	 * 签名错误
	 */
	public static final byte SIGNERROR = -2;

	/**
	 * 链接超时
	 */
	public static final byte TIMEOUT = -3;

	/**
	 * 链接异常
	 */
	public static final byte CONNECTEXCEPTION = -4;

	/**
	 * 链接关闭
	 */
	public static final byte CONNECTIONCLOSED = -5;
}
