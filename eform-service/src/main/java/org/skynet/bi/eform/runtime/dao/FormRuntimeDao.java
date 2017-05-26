package org.skynet.bi.eform.runtime.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.skynet.bi.eform.runtime.condition.Condition;
import org.skynet.bi.eform.runtime.vo.TableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

/**
 * 表格运行期数据访问对象
 * @author javadebug
 *
 */
@Service
public class FormRuntimeDao {
	private static final String COLLECTION_TABLE_INPUT_PREFIX = "table_input_";
	
	private static final LinkedHashSet<String> ENTITY_PROP_SET = new LinkedHashSet<String>();
	static {
		Field[] fields = TableRow.class.getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			if ((field.getModifiers() & java.lang.reflect.Modifier.STATIC) != java.lang.reflect.Modifier.STATIC) {
				String name = field.getName();
				ENTITY_PROP_SET.add(name);
			}
		}
	}
	
	@Autowired
	private  MongoTemplate mongoTemplate;
	
	//填充一条数据
	public void create(long modelId, TableRow row) {
		DBCollection collection = getCollection(modelId);
		collection.insert(convert2DBObject(row));
	}

	private DBCollection getCollection(long modelId) {
		return mongoTemplate.getCollection(COLLECTION_TABLE_INPUT_PREFIX + modelId);
	}
	
	//填充多条数据
	public void batchCreate(long modelId, TableRow[] rows) {
		DBCollection collection = getCollection(modelId);
		collection.insert(convert2TableRows(rows));
	}
	
	private DBObject[] convert2TableRows(TableRow[] rows) {
		DBObject[] objs = new BasicDBObject[rows.length];
		for (int i = 0; i < rows.length; i++) {
			objs[i] = convert2DBObject(rows[i]);
		}
		
		return objs;
	}

	/**
	 * 转换为DBObject
	 * @param row
	 * @return
	 */
	private DBObject convert2DBObject(TableRow row) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_id", row.getId());
		
		try {
			for (String key : ENTITY_PROP_SET) {
				Field field = row.getClass().getSuperclass().getDeclaredField(key);
				field.setAccessible(true);
				String name = field.getName();
				if (!"id".equals(name)) {
					map.put(name, field.get(row));
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			//ignore
		}
		
		map.putAll(row.getProps());
		
		return new BasicDBObject(map);
	}

	/**
	 * 删除一条数据
	 * @param modelId
	 * @param id
	 */
	public void delete(long modelId, long id) {
		DBCollection collection = getCollection(modelId);
		DBObject dbo_specify = new BasicDBObject();
        //指定的_id
        dbo_specify.put("_id", id);
        collection.remove(dbo_specify);
	}
	
	public void deleteByCondition(long modelId, Condition[] conditions) {
		DBCollection collection = getCollection(modelId);
		
        //指定的_id
		DBObject query = buildQuery(conditions);
        
        collection.remove(query, WriteConcern.ACKNOWLEDGED);
	}
	
	/**
	 * 更新一条数据
	 * @param modelId
	 * @param id
	 * @param row
	 */
	public void update(long modelId, long id, TableRow row) {
		DBCollection collection = getCollection(modelId);
		DBObject dbo_specify = new BasicDBObject();
		//指定的_id
        dbo_specify.put("_id", id);
        
		collection.update(dbo_specify, convert2DBObject(row));
	}
	
	/**
	 * 获得指定一条数据
	 * @param modelId
	 * @param id
	 * @return
	 */
	public TableRow getById(long modelId, long id) {
		DBCollection collection = getCollection(modelId);
		DBObject dbo_specify = new BasicDBObject();
		//指定的_id
        dbo_specify.put("_id", id);
        
        DBCursor cursor = collection.find(dbo_specify);
        while(cursor.hasNext()){
            DBObject obj = cursor.next();
            return convert2TableRow(obj);
        }
        
        return null;
	}

	private TableRow convert2TableRow(DBObject obj) {
		TableRow row = new TableRow();
		Map<String, Object> props = new HashMap<String, Object>();
		row.setProps(props);
		
		try {
			for (String key : ENTITY_PROP_SET) {
				Field field = obj.getClass().getField(key);
				field.set(row, obj.get(key));
			}
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			//ignore
		}
		
		for (String key : obj.keySet()) {
			if (!ENTITY_PROP_SET.contains(key)) {
				props.put(key, obj.get(key));
			}
		}
		
		return row;
	}
	
	/**
	 * 根据多个条件查询获得TableRow的列表
	 * @param modelId
	 * @param conditions
	 * @return
	 */
	public List<TableRow> getByConditions(long modelId, Condition[] conditions) {
		DBCollection collection = getCollection(modelId);
		DBObject query = buildQuery(conditions);
		
		List<TableRow> resultList = new ArrayList<TableRow>();
		DBCursor cursor = collection.find(query);
        while(cursor.hasNext()){
            DBObject obj = cursor.next();
            resultList.add(convert2TableRow(obj));
        }
		return resultList;
	}
	
	/**
	 * 获得指定表格下所有的数据
	 * @param modelId
	 * @return
	 */
	public List<TableRow> getAll(long modelId) {
		DBCollection collection = getCollection(modelId);
		
		List<TableRow> resultList = new ArrayList<TableRow>();
		DBCursor cursor = collection.find();
        while(cursor.hasNext()){
            DBObject obj = cursor.next();
            resultList.add(convert2TableRow(obj));
        }
		return resultList;
	}

	private DBObject buildQuery(Condition[] conditions) {
		DBObject query = new BasicDBObject();
		for (Condition condition : conditions) {
			query.put(condition.getFieldName(), parseConditon(condition));
		}
		return query;
	}

	private Object parseConditon(Condition condition) {
		switch (condition.getOperator()) {
		case Condition.OPERATOR_EQ:
			return getDBObjectForEQ(condition);
		case Condition.OPERATOR_GT:
			return getDBObjectForGT(condition);
		case Condition.OPERATOR_IN:
			return getDBObjectForIn(condition);
		case Condition.OPERATOR_LIKE:
			return getDBObjectForLike(condition);
		case Condition.OPERATOR_LT:
			return getDBObjectForLT(condition);
		case Condition.OPERATOR_NOT_IN:
			return getDBObjectForNotIn(condition);
		}
		
		return null;
	}

	private DBObject getDBObjectForNotIn(Condition condition) {
		BasicDBList values = new BasicDBList();
		for (Object obj : condition.getListValue()) {
			values.add(obj);
		}
		
		return new BasicDBObject("$nin", values);
	}

	private DBObject getDBObjectForLT(Condition condition) {
		return new BasicDBObject("$lt", condition.getValue());
	}

	private DBObject getDBObjectForLike(Condition condition) {
		BasicDBObject dbo = new BasicDBObject();// 新建查询基类对象 dbo
		Pattern pattern = Pattern.compile("^.*" + condition.getValue()+ ".*$", Pattern.CASE_INSENSITIVE); 
		dbo.put("content", pattern);
		
		return dbo;
	}

	private DBObject getDBObjectForIn(Condition condition) {
		BasicDBList values = new BasicDBList();
		for (Object obj : condition.getListValue()) {
			values.add(obj);
		}
		
		return new BasicDBObject("$in", values);
	}

	private DBObject getDBObjectForGT(Condition condition) {
		return new BasicDBObject("$gt", condition.getValue());
	}

	private Object getDBObjectForEQ(Condition condition) {
		return condition.getValue();
	}
}
