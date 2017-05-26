package com.ygsoft.realform.define;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.bi.pub.vo.BIObject;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.skynet.bi.realform.RealFormApplication;
import org.skynet.bi.realform.define.service.FormDefineService;
import org.skynet.bi.realform.define.vo.RealFormModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=RealFormApplication.class)
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Transactional
public class FormDefineServiceTests {
	@Resource(name = "formDefineService") 
    private FormDefineService service;

    @Test
    public void testCreate() {
        // RemoteService has been injected into the reverser bean
//        given(this.remoteService.someCall()).willReturn("mock");
//        String reverse = reverser.reverseSomeCall();
    	BIObjectSummary summary = doCreate();
//    	if (true) throw new RuntimeException("");
    	assertThat(summary).isNotNull();
    }

	private BIObjectSummary doCreate() {
		RealFormModel model = new RealFormModel();
    	model.setName("test");
    	
    	BIObject field = new BIObject();
    	field.setName("字段1");
    	field.setDefine("{}");
    	
    	model.getFields().add(field);
    	
    	BIObject filter = new BIObject();
    	filter.setName("测试过滤器");
    	field.setDefine("{}");
    	model.getFilters().add(filter);
    	
    	BIObject table = new BIObject();
    	table.setName("测试表格");
    	table.setDefine("{}");
    	model.setTable(table);
    	
    	BIObject form = new BIObject();
    	form.setName("测试表单");
    	form.setDefine("{}");
    	model.setForm(form);
    	
    	return service.create(model);
	}
    
	@Test
	public void testUpdate() {
		BIObjectSummary summary = doCreate();
		RealFormModel model = service.getModel(summary.getId());
		model.setName("new name");
		model.getForm().setName("new form name");
		model.getTable().setName("new table");
		model.getFields().forEach((BIObject field) -> {
			field.setName("更新字段名称1");
		});
		
		BIObject field = new BIObject();
    	field.setName("新字段1");
    	field.setDefine("{}");
    	
    	model.getFields().add(field);
    	
    	model.getFilters().forEach((BIObject filter) -> {
    		filter.setName("更新过滤器名称1");
    	});
    	
    	BIObject filter = new BIObject();
    	filter.setName("新过滤器1");
    	field.setDefine("{}");
    	
    	model.getFilters().add(filter);
    	
    	BIObjectSummary summary2 = service.updateModel(summary.getId(), model);
    	
    	RealFormModel model2 = service.getModel(summary.getId());
    	
    	assertThat(model2.getName()).isEqualTo("new name");
    	assertThat(model2.getTable().getName()).isEqualTo("new table");
    	assertThat(model2.getForm().getName()).isEqualTo("new form name");
    	assertThat(model2.getFields().size()).isEqualTo(2);
    	assertThat(model2.getFields().get(0).getName()).isEqualTo("更新字段名称1");
    	assertThat(model2.getFields().get(1).getName()).isEqualTo("新字段1");
    	assertThat(model2.getFilters().get(0).getName()).isEqualTo("更新过滤器名称1");
    	assertThat(model2.getFilters().get(1).getName()).isEqualTo("新过滤器1");
	}
	
	@Test
	public void testDelete() {
		BIObjectSummary summary = doCreate();
		service.delete(summary.getId());
		summary = service.getModel(summary.getId());
		assertThat(summary.isDeleted()).isEqualTo(true);
	}

}
