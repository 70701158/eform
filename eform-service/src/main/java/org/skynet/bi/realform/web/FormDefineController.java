package org.skynet.bi.realform.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.skynet.bi.framework.web.BaseController;
import org.skynet.bi.pub.vo.BIObject;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.skynet.bi.realform.define.service.FormDefineService;
import org.skynet.bi.realform.define.vo.RealFormModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * RealForm定义控制器
 * @author javadebug
 *
 */
@RestController
@RequestMapping("/forms")
@Api("表单定义API")
public class FormDefineController extends BaseController {
	@Resource(name = "formDefineService") 
    private FormDefineService service;

    /**
     * 创建模型
     * @return
     */
	@ApiOperation(value="创建模型", notes="")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BIObjectSummary create(@RequestBody RealFormModel model, HttpServletResponse response){
        BIObjectSummary summary = service.create(model);//保存数据.
        response.setStatus(HttpServletResponse.SC_CREATED);
        
        return summary;
    }
    
	@ApiOperation(value="删除指定模型", notes="")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
    	service.delete(id);
    }
    
	@ApiOperation(value="更新指定模型", notes="")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BIObjectSummary update(@PathVariable("id") long id, @RequestBody RealFormModel model) {
    	return service.updateModel(id, model);
    }
    
	@ApiOperation(value="获得指定模型", notes="")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RealFormModel getModel(@PathVariable("id") long id) {
    	return service.getModel(id);
    }
    
	@ApiOperation(value="创建字段", notes="")
    @RequestMapping(value = "/{modelId}/field", method = RequestMethod.POST)
    public BIObjectSummary createField(@PathVariable("modelId") long modelId, 
    		@RequestBody BIObject field, HttpServletResponse response){
		
        BIObjectSummary summary = service.createField(modelId, field);//保存数据.
        
        response.setStatus(HttpServletResponse.SC_CREATED);
        
        return summary;
    }
    
	@ApiOperation(value="删除字段", notes="")
    @RequestMapping(value = "/{modelId}/field/{fieldId}", method = RequestMethod.DELETE)
    public void deleteField(@PathVariable("modelId") long tableId, @PathVariable("fieldId") long fieldId) {
    	service.deleteField(tableId, fieldId);
    }
    
	@ApiOperation(value="更新字段定义", notes="")
    @RequestMapping(value = "/{modelId}/field/{fieldId}", method = RequestMethod.PUT)
    public BIObjectSummary updateField(@PathVariable("modelId") long tableId, @PathVariable("fieldId") long fieldId,
    		@RequestBody BIObject obj) {
    	return service.updateField(tableId, fieldId, obj); 
    }
    
	@ApiOperation(value="更新表格定义", notes="")
    @RequestMapping(value = "/{modelId}/table", method = RequestMethod.PUT) 
    public void updateTable(@PathVariable("modelId") long modelId, @RequestBody BIObject obj) {
    	service.updateTable(modelId, obj);
    }
    
	@ApiOperation(value="更新表单定义", notes="")
    @RequestMapping(value = "/{modelId}/form", method = RequestMethod.PUT) 
    public void updateForm(@PathVariable("modelId") long modelId, @RequestBody BIObject obj) {
    	service.updateForm(modelId, obj);
    }
}
