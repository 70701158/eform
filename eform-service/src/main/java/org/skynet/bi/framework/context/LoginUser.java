package org.skynet.bi.framework.context;

/**
 * 当前登录用户
 * @author javadebug
 *
 */
public class LoginUser {
	private long id;
	private String name;
	private int tenantId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
}
