package org.skynet.bi.realform.runtime.service;

import java.util.List;

import org.skynet.bi.realform.runtime.condition.Condition;
import org.skynet.bi.realform.runtime.vo.TableRow;
import org.springframework.stereotype.Service;

/**
 * 表格运行期服务
 * @author javadebug
 *
 */
@Service(value="formRuntimeServiceInterface")
public interface FormRuntimeService {
	/**
	 * 填充一条数据
	 * @param modelId
	 * @param row
	 */
	public long create(long modelId, TableRow row);

	/**
	 * 填充多条数据
	 * @param modelId
	 * @param rows
	 */
	public long[] batchCreate(long modelId, TableRow[] rows);
	
	/**
	 * 删除一条数据
	 * @param modelId
	 * @param id
	 */
	public void delete(long modelId, long id);
	
	/**
	 * 根据条件删除
	 * @param modelId
	 * @param conditions
	 */
	public void deleteByConditions(long modelId, Condition[] conditions);
	
	/**
	 * 更新一条数据
	 * @param modelId
	 * @param id
	 * @param row
	 */
	public void update(long modelId, long id, TableRow row);
	
	/**
	 * 获得某表格下所有的数据
	 * @param modelId
	 */
	public List<TableRow> getAll(long modelId);
	
	/**
	 * 获得指定一条数据
	 * @param modelId
	 * @param id
	 * @return
	 */
	public TableRow getById(long modelId, long id);
	
	/**
	 * 获得当前操作者的数据
	 * @param modelId
	 * @return
	 */
	public List<TableRow> getMyRows(long modelId);
	
	/**
	 * 根据多个条件查询获得TableRow的列表
	 * @param modelId
	 * @param conditions
	 * @return
	 */
	public List<TableRow> getByConditions(long modelId, Condition[] conditions);
	
	/**
	 * 根据一个条件查询获得TableRow
	 * @param modelId
	 * @param conditions
	 * @return
	 */
	public List<TableRow> getByCondition(long modelId, Condition conditions);
}
