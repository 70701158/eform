package com.ygsoft.realform.define;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.bi.framework.util.JSONUtil;
import org.skynet.bi.pub.vo.BIObject;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.skynet.bi.realform.RealFormApplication;
import org.skynet.bi.realform.define.vo.RealFormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * 测试表单定义控制器
 * @author javadebug
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RealFormApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class FormDefineControllerTests {
	
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void testCreate() throws Exception {
    	String content = this.mvc.perform(post("/forms").contentType(MediaType.APPLICATION_JSON_UTF8)
    			.content(JSONUtil.toJson(buildModel()))).andDo(print()).andExpect(status()
    			.is(HttpServletResponse.SC_CREATED)).andDo((MvcResult result) -> {
    				String json = result.getResponse().getContentAsString();
    				JSONUtil.toBean(json, BIObjectSummary.class).getId();
    			}).andReturn().getResponse().getContentAsString();
//    	System.out.println(content);
//    	this.mvc.perform(get("/forms/" + MODEL_ID).accept(MediaType.APPLICATION_JSON_UTF8))
//    	.andExpect(status().isOk());
    }
    
//    @Test
//    public void testCreate() throws Exception {
//    	this.mvc.perform(post("http://localhost:8090/forms", JSONUtil.toJson(buildModel())).accept(MediaType.APPLICATION_JSON_UTF8))
//    			.andExpect(status().isOk());
//    }
//
//    @Test
//    public void testExample() throws Exception {
////        given(this.service.getVehicleDetails("sboot"))
////                .willReturn(new VehicleDetails("Honda", "Civic"));
//        this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
//    }
    
    private RealFormModel buildModel() {
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
    	
    	return model;
	}

}