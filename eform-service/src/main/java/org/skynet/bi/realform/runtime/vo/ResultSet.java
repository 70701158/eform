package org.skynet.bi.realform.runtime.vo;

import org.skynet.bi.pub.vo.BIObject;

/**
 * 结果集
 * @author javadebug
 *
 */
public class ResultSet {
	private BIObject[] fields;
	private Object[] datas;
	
	public BIObject[] getFields() {
		return fields;
	}
	public void setFields(BIObject[] fields) {
		this.fields = fields;
	}
	public Object[] getDatas() {
		return datas;
	}
	public void setDatas(Object[] datas) {
		this.datas = datas;
	}
}
