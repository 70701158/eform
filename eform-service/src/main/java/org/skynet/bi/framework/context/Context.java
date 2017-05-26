package org.skynet.bi.framework.context;

/**
 * 上下文
 * @author javadebug
 *
 */
public class Context {
	private static Context ctx = new Context();
	
	private LoginUser loginUser = new LoginUser();
	
	public static Context current() {
		return ctx;
	}
	
	public LoginUser getLoginUser() {
		return loginUser;
	}
}
