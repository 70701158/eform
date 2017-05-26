package org.skynet.bi.framework.bo;

import java.sql.Timestamp;

/**
 * 实体
 * @author javadebug
 *
 */
public abstract class Entity {
	
	public static final String FIELD_NAME_ID = "id";
	public static final String FIELD_NAME_CREATOR = "creator";
	public static final String FIELD_NAME_LAST_MODIFIER = "lastModifier";
	public static final String FIELD_NAME_TENANT_ID = "tenantId";
	public static final String FIELD_NAME_CREATETIME = "createTime";
	public static final String FIELD_NAME_LAST_MODIFIED = "lastModified";
	
	private long id;
	private long creator;
	private long lastModifier;
	private int tenantId;
	private Timestamp createTime;
	private Timestamp lastModified;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCreator() {
		return creator;
	}
	public void setCreator(long creator) {
		this.creator = creator;
	}
	public long getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(long lastModifier) {
		this.lastModifier = lastModifier;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastModified() {
		return lastModified;
	}
	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
}
