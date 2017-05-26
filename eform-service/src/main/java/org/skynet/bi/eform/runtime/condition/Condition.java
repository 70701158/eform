package org.skynet.bi.eform.runtime.condition;

/**
 * 查询条件
 * @author javadebug
 *
 */
public class Condition {
	public static final String OPERATOR_GT = ">";
	public static final String OPERATOR_LT = "<";
	public static final String OPERATOR_EQ = "=";
	public static final String OPERATOR_IN = "in";
	public static final String OPERATOR_NOT_IN = "nin";
	public static final String OPERATOR_LIKE = "like";
	
	private String fieldName;
	private String operator;
	private Object value;
	
	private boolean isList = false;
	
	public Condition(String fieldName, String operator, Object value) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
	}
	
	public Condition(String fieldName, String operator, Object[] value) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
	}
	
	public boolean isList() {
		return isList;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public Object getValue() {
		return value;
	}
	
	public Object[] getListValue() {
		return (Object[]) value;
	}

}


