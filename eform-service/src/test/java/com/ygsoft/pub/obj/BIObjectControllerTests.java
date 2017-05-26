package com.ygsoft.pub.obj;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.bi.pub.vo.BIObject;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.skynet.bi.realform.RealFormApplication;
import org.skynet.bi.realform.define.service.FormDefineService;
import org.skynet.bi.realform.define.vo.RealFormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 测试表单定义控制器
 * @author javadebug
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RealFormApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class BIObjectControllerTests {
	@Resource(name = "formDefineService") 
    private FormDefineService service;
	
    @Autowired
    private MockMvc mvc;
    
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
    public void testGetMySummaries() throws Exception {
    	BIObjectSummary summary = doCreate();
    	summary = doCreate();
    	
//    	String content = this.mvc.perform(get("/summaries/my").contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
//    			.andExpect(status());
    	this.mvc.perform(get("/summaries/my").contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status()
    			.is(HttpServletResponse.SC_CREATED));
    }

}