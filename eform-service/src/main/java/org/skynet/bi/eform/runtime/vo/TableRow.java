package org.skynet.bi.eform.runtime.vo;

import java.util.HashMap;
import java.util.Map;

import org.skynet.bi.framework.bo.Entity;

/**
 * 一行数据
 * @author javadebug
 *
 */
public class TableRow extends Entity {
	private Map<String, Object> props = new HashMap<String, Object>();

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
}
