package org.skynet.bi.framework.util;

/**
 * 转换异常
 * @author zll
 *
 */
public class ConvertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConvertException() {
	}

	public ConvertException(String message) {
		super(message);
	}

	public ConvertException(Throwable cause) {
		super(cause);
	}

	public ConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
