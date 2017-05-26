package org.skynet.bi.framework.web;

/**
 * 设置缓存条件
 * 以后增加条件
 * 
 * @author skynet
 *
 */
public class CacheControl {
	private int maxAge = -1;
	
	public CacheControl(int maxAge) {
		this.maxAge = maxAge;
	}
	
	public int getMaxAge() {
		return this.maxAge;
	}
}
