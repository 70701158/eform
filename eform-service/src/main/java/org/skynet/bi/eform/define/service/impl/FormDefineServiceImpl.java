package org.skynet.bi.eform.define.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.skynet.bi.eform.constants.FormConstants;
import org.skynet.bi.eform.define.service.FormDefineService;
import org.skynet.bi.eform.define.vo.RealFormModel;
import org.skynet.bi.framework.service.BaseService;
import org.skynet.bi.framework.util.BeanCompareUtil;
import org.skynet.bi.framework.util.IDGenerator;
import org.skynet.bi.pub.obj.constants.ObjectTypeConstants;
import org.skynet.bi.pub.obj.service.BIObjectService;
import org.skynet.bi.pub.vo.BIObject;
import org.skynet.bi.pub.vo.BIObjectDefine;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Form服务实现
 * @author javadebug
 *
 */
@Service(value="formDefineService")
@Transactional
public class FormDefineServiceImpl extends BaseService implements FormDefineService {
	@Resource(name = "biObjectService")
	private BIObjectService service;
	 
	@Override
	public RealFormModel getModel(long id) {
		RealFormModel model = new RealFormModel();
		List<BIObject> fieldList = new ArrayList<BIObject>();
		List<BIObject> filterList = new ArrayList<BIObject>();
		
		model.setFields(fieldList);
		model.setFilters(filterList);
		
		List<BIObjectSummary> allObjList = service.getSummariesWithChildren(id);
		List<Long> ids = new ArrayList<Long>(allObjList.size() * 2);
		allObjList.forEach((BIObjectSummary summary) -> {
			ids.add(summary.getId());
			if (summary.getId() == id) {
				BeanUtils.copyProperties(summary, model);
			}
		});
		
		List<BIObjectDefine> allDefineList = service.getDefinesByIds(ids.toArray(new Long[ids.size()]));
		
		Map<Long, BIObjectDefine> defineMap = new HashMap<Long, BIObjectDefine>();
		allDefineList.forEach((BIObjectDefine define) -> {
			defineMap.put(define.getId(), define);
		});
		
		allObjList.forEach((BIObjectSummary summary) -> {
			if (FormConstants.TYPE_FORM_FIELD == summary.getType()) {
				fieldList.add(toBIObject(summary, defineMap.get(summary.getId())));
			} else if (FormConstants.TYPE_FORM_FILTER == summary.getType()) {
				filterList.add(toBIObject(summary, defineMap.get(summary.getId())));
			} else if (FormConstants.TYPE_FORM_FORM == summary.getType()) {
				model.setForm(toBIObject(summary, defineMap.get(summary.getId())));
			} else if (FormConstants.TYPE_FORM_TABLE == summary.getType()) {
				model.setTable(toBIObject(summary, defineMap.get(summary.getId())));
			}
		});
		
		return model;
	}
	
	@Override
	public BIObjectSummary getSummary(long id) {
		return service.getSummary(id);
	}
	
	/**
	 * 将摘要和定义合并转换为VO
	 * @param summaryPO
	 * @param define
	 * @return
	 */
	private BIObject toBIObject(BIObjectSummary summary, BIObjectDefine define) {
		BIObject obj = new BIObject();
		BeanUtils.copyProperties(summary, obj);
		obj.setDefine(define == null ? null : define.getDefine());
		
		return obj;
	}

	@Override
	public BIObjectSummary create(RealFormModel model) {
		model.setId(IDGenerator.nextLong());
		List<BIObjectSummary> summaryList = new ArrayList<BIObjectSummary>();
		List<BIObjectDefine> defineList = new ArrayList<BIObjectDefine>();
		
		BIObjectSummary summary = new BIObjectSummary();
		BeanUtils.copyProperties(model, summary);
		
		summary.setType(ObjectTypeConstants.TYPE_FORM);
		this.fillCreateInfo(summary);
		
		summaryList.add(summary);
		
		model.getFields().forEach((BIObject obj) -> {
			obj.setParentId(summary.getId());
			obj.setType(FormConstants.TYPE_FORM_FIELD);
			fillCreateInfo(obj);
			summaryList.add(toBIObjectSummary(obj));
			defineList.add(toBIObjectDefine(obj));
		});
		
		model.getFilters().forEach((BIObject obj) -> {
			obj.setType(FormConstants.TYPE_FORM_FILTER);
			obj.setParentId(summary.getId());
			fillCreateInfo(obj);
			summaryList.add(toBIObjectSummary(obj));
			defineList.add(toBIObjectDefine(obj));
		});
		
		if (model.getForm() != null) {
			model.getForm().setParentId(summary.getId());
			BIObject formObj = model.getForm();
			formObj.setType(FormConstants.TYPE_FORM_FORM);
			formObj.setParentId(summary.getId());
			fillCreateInfo(formObj);
			summaryList.add(toBIObjectSummary(formObj));
			defineList.add(toBIObjectDefine(model.getForm()));
		}
		
		if (model.getTable() != null) {
			BIObject tableObj = model.getTable();
			tableObj.setType(FormConstants.TYPE_FORM_TABLE);
			tableObj.setParentId(summary.getId());
			tableObj.setParentId(summary.getId());
			fillCreateInfo(tableObj);
			summaryList.add(toBIObjectSummary(tableObj));
			defineList.add(toBIObjectDefine(model.getTable()));
		}
		
		service.batchCreate(summaryList, defineList);
		
		return model;
	}

	private BIObjectSummary toBIObjectSummary(BIObject obj) {
		BIObjectSummary summary = new BIObjectSummary();
		BeanUtils.copyProperties(obj, summary);
		
		return summary;
	}
	
	private BIObjectDefine toBIObjectDefine(BIObject obj) {
		return new BIObjectDefine(obj.getId(), obj.getTenantId(), obj.getDefine());
	}

	@Override
	public BIObjectSummary updateModel(long id, RealFormModel model) {
		RealFormModel oldModel = this.getModel(id);
		List<BIObject> oldFieldList = new ArrayList<BIObject>();
		Map<Long, BIObject> oldFieldMap = new HashMap<Long, BIObject>();
		List<BIObject> oldFilterList = new ArrayList<BIObject>();
		Map<Long, BIObject> oldFilterMap = new HashMap<Long, BIObject>();
		
		Set<Long> fieldSet = new HashSet<Long>();
		Set<Long> filterSet = new HashSet<Long>();
		
		List<BIObjectSummary> updateSummaryList = new ArrayList<BIObjectSummary>();
		List<BIObjectDefine> updateDefineList = new ArrayList<BIObjectDefine>();
		
		List<BIObjectSummary> createSummaryList = new ArrayList<BIObjectSummary>();
		List<BIObjectDefine> createDefineList = new ArrayList<BIObjectDefine>();
		
		BIObjectSummary summary = new BIObjectSummary();
		BeanUtils.copyProperties(model, summary);
		
		this.fillUpdateInfo(summary);
		updateSummaryList.add(summary);
		
		oldModel.getFields().forEach((BIObject obj) -> {
			oldFieldList.add(obj);
			oldFieldMap.put(obj.getId(), obj);
		});
		
		oldModel.getFilters().forEach((BIObject obj) -> {
			oldFilterList.add(obj);
			oldFilterMap.put(obj.getId(), obj);
		});
		
		model.getFields().forEach((BIObject obj) -> {
			fieldSet.add(obj.getId());
		});
		
		model.getFilters().forEach((BIObject obj) -> {
			filterSet.add(obj.getId());
		});
		
		//处理更新表格
		if (model.getTable() != null) {
			if (!BeanCompareUtil.isEquals(model.getTable(), oldModel.getTable())) {
				model.getTable().setParentId(model.getId());
				updateSummaryList.add(toBIObjectSummary(model.getTable()));
				updateDefineList.add(toBIObjectDefine(model.getTable()));
			}
		}
		
		//处理更新表单
		if (model.getForm() != null) {
			if (!BeanCompareUtil.isEquals(model.getTable(), oldModel.getTable())) {
				model.getForm().setParentId(model.getId());
				updateSummaryList.add(toBIObjectSummary(model.getForm()));
				updateDefineList.add(toBIObjectDefine(model.getForm()));
			}
		}
		
		//找出有更新的字段
		model.getFields().forEach((BIObject obj) -> {
			BIObject old = oldFieldMap.get(obj.getId());
			obj.setParentId(model.getId());
			obj.setType(FormConstants.TYPE_FORM_FIELD);
			processForUpdate(oldFieldList, oldFieldMap, updateSummaryList, updateDefineList, createSummaryList,
					createDefineList, obj, old);
		});
		
		//更新过滤器
		model.getFilters().forEach((BIObject obj) -> {
			obj.setParentId(model.getId());
			BIObject old = oldFilterMap.get(obj.getId());
			obj.setType(FormConstants.TYPE_FORM_FILTER);
			processForUpdate(oldFieldList, oldFieldMap, updateSummaryList, updateDefineList, createSummaryList,
					createDefineList, obj, old);
		});
		
		//找出删除不存在的内容
		List<Long> deleteIdList = new ArrayList<Long>();
		oldModel.getFields().forEach((BIObject obj) -> {
			if (!fieldSet.contains(obj.getId())) {
				deleteIdList.add(obj.getId());
			}
		});
		oldModel.getFilters().forEach((BIObject obj) -> {
			if (!filterSet.contains(obj.getId())) {
				deleteIdList.add(obj.getId());
			}
		});
		
		//执行创建、更新、删除
		service.batchCreate(createSummaryList, createDefineList);
		service.batchUpdate(updateSummaryList, updateDefineList);
		long[] deleteIds = deleteIdList.stream().mapToLong(t->t.longValue()).toArray();
		if (deleteIds.length > 0) {
			service.deleteByIds(deleteIdList.stream().mapToLong(t->t.longValue()).toArray());
		}
		
		return summary;
	}

	private void processForUpdate(List<BIObject> oldFieldList, Map<Long, BIObject> oldFieldMap,
			List<BIObjectSummary> updateSummaryList, List<BIObjectDefine> updateDefineList,
			List<BIObjectSummary> createSummaryList, List<BIObjectDefine> createDefineList, BIObject obj,
			BIObject old) {
		if (old != null) {
			if (!BeanCompareUtil.isEquals(obj, old)) {
				updateSummaryList.add(toBIObjectSummary(obj));
				updateDefineList.add(toBIObjectDefine(obj));
				oldFieldList.remove(old);
				oldFieldMap.remove(obj.getId());
			}
		} else {
			BIObjectSummary newSummary = toBIObjectSummary(obj);
			newSummary.setId(IDGenerator.nextLong());
			createSummaryList.add(newSummary);
			BIObjectDefine newDefine = toBIObjectDefine(obj);
			newDefine.setId(newSummary.getId());
			createDefineList.add(newDefine);
		}
	}
	
	@Override
	public BIObjectSummary updateSummary(long id, BIObjectSummary summary) {
		return service.updateSummary(summary);
	}

	@Override
	public void delete(long id) {
		service.delete(id);
	}

	@Override
	public void deleteByIds(long[] ids) {
		service.deleteByIds(ids);
	}

	@Override
	public BIObjectSummary createField(long modelId, BIObject obj) {
		obj.setParentId(modelId);
		
		if (obj.getId() <= 0) {
			obj.setId(IDGenerator.nextLong());
		}
		
		service.create(obj, obj.getDefine());
		
		return toBIObjectSummary(obj);
	}

	@Override
	public BIObjectSummary updateField(long modelId, long fieldId, BIObject obj) {
		return updateObj(obj);
	}

	private BIObjectSummary updateObj(BIObject obj) {
		BIObjectSummary summary = service.updateSummary(obj);
		service.updateDefine(toBIObjectDefine(obj));
		
		return summary;
	}

	@Override
	public void deleteField(long tableId, long fieldId) {
		service.delete(fieldId);
	}

	@Override
	public BIObjectSummary updateTable(long modelId, BIObject obj) {
		return updateObj(obj);
	}

	@Override
	public BIObjectSummary updateForm(long modelId, BIObject obj) {
		return updateObj(obj);
	}
	
}
