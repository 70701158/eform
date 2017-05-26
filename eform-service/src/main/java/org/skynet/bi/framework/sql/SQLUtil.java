package org.skynet.bi.framework.sql;

/**
 * SQL工具类
 * @author javadebug
 *
 */
public class SQLUtil {
	
	/**
	 * 检查SQL注入
	 * @param sqlPart
	 * @throws InjectException
	 */
	public static void checkSqlInject(String sqlPart) throws InjectException {
		//TODO
	}
	
	/**
	 * 检查SQL注入
	 * @param sqlParts
	 * @throws InjectException
	 */
	public static void checkSqlInject(String[] sqlParts) throws InjectException {
		//TODO
	}
	
	/**
	 * 将多个字段转换为逗号区分的SQL片段
	 * @param fields
	 * @return
	 */
	public static String toSqlPart(Long[] fields) {
//		checkSqlInject(fields);
		StringBuilder sql = new StringBuilder();
		
		for (long field : fields) {
			sql.append(field).append(",");
		}
		if (sql.length() > 0) {
			return sql.substring(0, sql.length() - 1).toLowerCase();
		} else {
			return "";
		}
	}
}
