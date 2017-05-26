package org.skynet.bi.eform.runtime.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.skynet.bi.eform.runtime.condition.Condition;
import org.skynet.bi.eform.runtime.dao.FormRuntimeDao;
import org.skynet.bi.eform.runtime.service.FormRuntimeService;
import org.skynet.bi.eform.runtime.vo.TableRow;
import org.skynet.bi.framework.bo.Entity;
import org.skynet.bi.framework.context.Context;
import org.skynet.bi.framework.context.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 运行时表格数据管理服务实现
 * @author javadebug
 *
 */
@Service(value="formRuntimeService")
public class FormRuntimeServiceImpl implements FormRuntimeService {

	@Autowired
	private FormRuntimeDao dao;
	
	@Override
	public long create(long modelId, TableRow row) {
		fillTableRow(row);
		
		dao.create(modelId, row);
		
		return row.getId();
	}

	private void fillTableRow(TableRow row) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		LoginUser user = Context.current().getLoginUser();
		row.setCreator(user.getId());
		row.setLastModified(time);
		row.setLastModifier(user.getId());
	}

	@Override
	public long[] batchCreate(long modelId, TableRow[] rows) {
		for (TableRow row : rows) {
			fillTableRow(row);
		}
		
		dao.batchCreate(modelId, rows);
		
		long[] ret = new long[rows.length];
		for (int i = 0; i < rows.length; i++) {
			ret[i] = rows[i].getId();
		}
		
		return ret;
	}

	@Override
	public void delete(long modelId, long id) {
		dao.delete(modelId, id);
	}
	
	@Override
	public void deleteByConditions(long modelId, Condition[] conditions) {
		dao.deleteByCondition(modelId, conditions);
	}

	@Override
	public void update(long modelId, long id, TableRow row) {
		dao.update(modelId, id, row);
	}

	@Override
	public TableRow getById(long modelId, long id) {
		return dao.getById(modelId, id);
	}
	
	@Override
	public List<TableRow> getMyRows(long modelId) {
		Condition condition = new Condition(Entity.FIELD_NAME_CREATOR, Condition.OPERATOR_EQ, 
				Context.current().getLoginUser().getId());
		return this.getByCondition(modelId, condition);
	}

	@Override
	public List<TableRow> getByConditions(long modelId, Condition[] conditions) {
		return dao.getByConditions(modelId, conditions);
	}

	@Override
	public List<TableRow> getByCondition(long modelId, Condition condition) {
		return dao.getByConditions(modelId, new Condition[] {condition});
	}

	@Override
	public List<TableRow> getAll(long modelId) {
		return dao.getAll(modelId);
	}

}
