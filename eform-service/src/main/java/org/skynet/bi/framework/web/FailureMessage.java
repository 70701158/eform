package org.skynet.bi.framework.web;

import java.io.Serializable;

/**
 * 失败消息
 * @author zhanglld
 *
 */
public class FailureMessage implements Serializable {
	private static final long serialVersionUID = -1240096395988693703L;
	
	public static final String CODE_UNKOWN = "unkown";
	
	private String code = null;
	private String body = null;
	
	public FailureMessage(String code, String body) {
		this.code = code == null ? CODE_UNKOWN : code;
		this.body = body;
	}
	
	public FailureMessage() {}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
