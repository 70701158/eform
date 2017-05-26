package org.skynet.bi.pub.obj.po;

/**
 * BI对象依赖PO
 * @author javadebug
 *
 */
public class BIObjectDependPO {
	private long id;
	private long priorId;
	private long fellowId;
	private long tenantId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPriorId() {
		return priorId;
	}
	public void setPriorId(long priorId) {
		this.priorId = priorId;
	}
	public long getFellowId() {
		return fellowId;
	}
	public void setFellowId(long fellowId) {
		this.fellowId = fellowId;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
}
