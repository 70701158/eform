package org.skynet.bi.pub.obj.mapper;

import java.util.Map;

import org.skynet.bi.framework.sql.SQLUtil;

/**
 * 分析对象依赖SQL提供者
 * @author skynet
 *
 */
public class BIObjectDependSqlProvider {
	public String getByIds(Map<String,Object> param){
		Long[] uids = (Long[]) param.get("ids");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT " + BIObjectDependMapper.FIELDS.SELECT_FIELDS + " from realform.ri_object_depend_on WHERE id in (");
		sql.append(SQLUtil.toSqlPart(uids)).append(")");
				
		return sql.toString();
	}
	
	public String deleteByIds(Map<String,Object> param){
		Long[] uids = (Long[]) param.get("ids");
		StringBuilder sql = new StringBuilder();
		sql.append("delete from realform.ri_object_depend_on WHERE id in (");
		sql.append(SQLUtil.toSqlPart(uids)).append(")");
				
		return sql.toString();
	}
}