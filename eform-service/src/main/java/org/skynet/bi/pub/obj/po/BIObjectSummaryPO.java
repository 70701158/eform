package org.skynet.bi.pub.obj.po;

import org.skynet.bi.framework.bo.Entity;

/**
 * BI对象
 * 
 * @author javadebug
 *
 */
public class BIObjectSummaryPO extends Entity {
	private long parentId = -1;
	private String name;
	private short type;
	private String originThumbnailUrl;
	private String middleThumbnailUrl;
	private String smallThumbnailUrl;
	private String customIconUrl;
	private boolean deleted;
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public String getOriginThumbnailUrl() {
		return originThumbnailUrl;
	}
	public void setOriginThumbnailUrl(String originThumbnailUrl) {
		this.originThumbnailUrl = originThumbnailUrl;
	}
	public String getSmallThumbnailUrl() {
		return smallThumbnailUrl;
	}
	public void setSmallThumbnailUrl(String smallThumbnailUrl) {
		this.smallThumbnailUrl = smallThumbnailUrl;
	}
	public String getCustomIconUrl() {
		return customIconUrl;
	}
	public void setCustomIconUrl(String customIconUrl) {
		this.customIconUrl = customIconUrl;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getMiddleThumbnailUrl() {
		return middleThumbnailUrl;
	}
	public void setMiddleThumbnailUrl(String middleThumbnailUrl) {
		this.middleThumbnailUrl = middleThumbnailUrl;
	}
}
