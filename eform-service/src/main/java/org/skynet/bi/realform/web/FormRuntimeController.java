package org.skynet.bi.realform.web;

import java.util.List;

import javax.annotation.Resource;

import org.skynet.bi.framework.util.JSONUtil;
import org.skynet.bi.framework.web.BaseController;
import org.skynet.bi.realform.define.service.FormDefineService;
import org.skynet.bi.realform.runtime.condition.Condition;
import org.skynet.bi.realform.runtime.service.FormRuntimeService;
import org.skynet.bi.realform.runtime.vo.TableRow;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 表格/表单运行期控制器
 * @author javadebug
 *
 */
@RestController
@RequestMapping("/forms")
@Api("表单运行期API")
public class FormRuntimeController extends BaseController {
	@Resource(name = "formDefineService") 
    private FormDefineService defineService;
	
	@Resource(name = "formRuntimeService")
	private FormRuntimeService runtimeService;
	
	/**
	 * 填充多条数据
	 * @param modelId
	 * @param rows
	 */
	@ApiOperation(value = "创建一条记录行", notes = "")
	@RequestMapping(value = "/{modelId}/rows", method = RequestMethod.POST)
	public long[] create(@PathVariable("modelId") long modelId, @RequestBody TableRow[] rows) {
		return runtimeService.batchCreate(modelId, rows);
	}
	
	/**
	 * 删除一条数据
	 * @param modelId
	 * @param id
	 */
	@ApiOperation(value = "删除指定记录", notes = "")
	@RequestMapping(value = "/{modelId}/rows/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("modelId") long modelId, @PathVariable("id") long id) {
		runtimeService.delete(modelId, id);
	}
	
	/**
	 * 根据条件删除
	 * @param modelId
	 * @param conditions
	 */
	@ApiOperation(value = "根据条件删除", notes = "")
	@RequestMapping(value = "/{modelId}/rows", method = RequestMethod.DELETE)
	public void deleteByConditions(@PathVariable("modelId") long modelId, @MatrixVariable("conditions") String conditions) {
		runtimeService.deleteByConditions(modelId, JSONUtil.toBean(conditions, Condition[].class));
	}
	
	/**
	 * 更新一条数据
	 * @param modelId
	 * @param id
	 * @param row
	 */
	@ApiOperation(value = "更新记录", notes = "")
	@RequestMapping(value = "/{modelId}/rows/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable("modelId") long modelId, @PathVariable("id") long id, @RequestBody TableRow row) {
		runtimeService.update(modelId, id, row);
	}
	
	/**
	 * 获得指定一条数据
	 * @param modelId
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获得指定记录", notes = "")
	@RequestMapping(value = "/{modelId}/rows/{id}", method = RequestMethod.GET)
	public TableRow getById(@PathVariable("modelId") long modelId, @PathVariable("id") long id) {
		return runtimeService.getById(modelId, id);
	}
	
	/**
	 * 获得当前操作者的数据
	 * @param modelId
	 * @return
	 */
	@ApiOperation(value = "根据条件获得指定多条记录", notes = "")
	@RequestMapping(value = "/{modelId}/myrows", method = RequestMethod.GET)
	public List<TableRow> getMyRows(@PathVariable("modelId") long modelId) {
		return runtimeService.getMyRows(modelId);
	}
	
	/**
	 * 根据多个条件查询获得TableRow的列表
	 * @param modelId
	 * @param conditions
	 * @return
	 */
	@ApiOperation(value = "根据条件获得指定多条记录", notes = "")
	@RequestMapping(value = "{modelId}/rows", method = RequestMethod.GET)
	public List<TableRow> getByConditions(@PathVariable("modelId") long modelId, @MatrixVariable("conditions") String conditions) {
		if (conditions == null) {
			return runtimeService.getAll(modelId);
		} else {
			return runtimeService.getByConditions(modelId, JSONUtil.toBean(conditions, Condition[].class));
		}
	}
}
