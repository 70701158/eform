package org.skynet.bi.eform.define.vo;

import java.util.ArrayList;
import java.util.List;

import org.skynet.bi.pub.vo.BIObject;
import org.skynet.bi.pub.vo.BIObjectSummary;

/**
 * BI对象模型
 * @author javadebug
 *
 */
public class RealFormModel extends BIObjectSummary {
	
	private List<BIObject> fields = new ArrayList<BIObject>();
	private List<BIObject> filters = new ArrayList<BIObject>();
	private BIObject table = null;
	private BIObject form = null;
	
	public List<BIObject> getFields() {
		return fields;
	}
	public void setFields(List<BIObject> fields) {
		this.fields = fields;
	}
	public List<BIObject> getFilters() {
		return filters;
	}
	public void setFilters(List<BIObject> filters) {
		this.filters = filters;
	}
	public BIObject getTable() {
		return table;
	}
	public void setTable(BIObject table) {
		this.table = table;
	}
	public BIObject getForm() {
		return form;
	}
	public void setForm(BIObject form) {
		this.form = form;
	}
}
