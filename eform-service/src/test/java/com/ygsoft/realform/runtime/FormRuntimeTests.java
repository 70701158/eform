package com.ygsoft.realform.runtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.bi.framework.bo.Entity;
import org.skynet.bi.framework.context.Context;
import org.skynet.bi.framework.util.IDGenerator;
import org.skynet.bi.realform.RealFormApplication;
import org.skynet.bi.realform.runtime.condition.Condition;
import org.skynet.bi.realform.runtime.service.FormRuntimeService;
import org.skynet.bi.realform.runtime.vo.TableRow;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试表单填充
 * @author javadebug
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RealFormApplication.class)
public class FormRuntimeTests {
	private static final long modelId = 0;
	@Resource(name = "formRuntimeService")
	private FormRuntimeService service;

	@Before
	public void before() {
		clear();
	}

	private void clear() {
		Condition condition = new Condition(Entity.FIELD_NAME_CREATOR, Condition.OPERATOR_EQ, 
				Context.current().getLoginUser().getId());
		service.deleteByConditions(modelId, new Condition[] {condition});
	}

	@Test
	public void testCreate() {
		TableRow row = create();
		service.delete(modelId, row.getId());
	}
	
	private TableRow create() {
		TableRow row = new TableRow();
		row.setId(IDGenerator.nextLong());
		row.setTenantId(100);
		row.getProps().put("field_1", 100);
		row.getProps().put("field_2", "dsdfsdf");
		
		service.create(modelId, row);
		return row;
	}
	
	private TableRow create(Map<String, Object> props) {
		TableRow row = new TableRow();
		row.setId(IDGenerator.nextLong());
		row.setTenantId(100);
		row.setProps(props);
		
		service.create(modelId, row);
		return row;
	}
	
	@Test
	public void testGetById() {
		TableRow row = create();
		TableRow row2 = service.getById(modelId, row.getId());
		assertThat(row2).isNotNull();
		assertThat(row2.getProps().get("field_1")).isEqualTo(100);
	}
	
	@Test
	public void testGetByCondition() {
		Map<String, Object> map = new HashMap<String, Object>() {
			{
				put("name", "June"); 
				put("age", 12);  
			}
		};

		String searchName = "bbb" + System.currentTimeMillis();
		TableRow row1 = create(map);
		map.put("name", searchName);
		map.put("age", 20);
		
		TableRow row2 = create(map);
		map.put("name", searchName);
		map.put("age", 10);
		
		TableRow row3 = create(map);
		
		Condition nameCondition = new Condition("name", Condition.OPERATOR_EQ, searchName);
		Condition ageCondition = new Condition("age", Condition.OPERATOR_EQ, 20);
		
		List<TableRow> result = service.getByConditions(modelId, new Condition[] {nameCondition});
		
		assertThat(result.size()).isEqualByComparingTo(2);
		assertThat(result.get(0).getProps().get("name")).isEqualTo(searchName);
		
		result = service.getByConditions(modelId, new Condition[] {nameCondition, ageCondition});
		
		assertThat(result.size()).isEqualByComparingTo(1);
		assertThat(result.get(0).getProps().get("age")).isEqualTo(20);
//		service.getByConditions(modelId, conditions)
	}
	
	@Test
	public void testGetByMyRows() {
		create();
		create();
		List<TableRow> rows = service.getMyRows(modelId);
		assertThat(rows.size()).isEqualByComparingTo(2);
	}
	

}
