package org.skynet.bi.pub.obj.po;

/**
 * RI对象定义
 * @author javadebug
 *
 */
public class BIObjectDefinePO {
	private long id;
	private String define;
	private int tenantId;
	
	public BIObjectDefinePO(long id, int tenantId, String define) {
		this.id = id;
		this.tenantId = tenantId;
		this.define = define;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDefine() {
		return define;
	}
	public void setDefine(String define) {
		this.define = define;
	}
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
}
