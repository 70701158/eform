package org.skynet.bi.pub.obj.mapper;

import java.util.Map;

import org.skynet.bi.framework.sql.SQLUtil;

/**
 * 分析对象定义字段sql提供者
 * @author skynet
 *
 */
public class BIObjectDefineSqlProvider {
	public String getByIds(Map<String,Object> param){
		Long[] uids = (Long[]) param.get("ids");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT " + BIObjectDefineMapper.FIELDS.SELECT_FIELDS + " from realform.ri_object_define WHERE id in (");
		sql.append(SQLUtil.toSqlPart(uids)).append(")");
				
		return sql.toString();
	}
	
	public String deleteByIds(Map<String,Object> param){
		Long[] uids = (Long[]) param.get("ids");
		StringBuilder sql = new StringBuilder();
		sql.append("delete from realform.ri_object_object WHERE id in (");
		sql.append(SQLUtil.toSqlPart(uids)).append(")");
				
		return sql.toString();
	}
}