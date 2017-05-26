package org.skynet.bi.realform.define.service;

import org.skynet.bi.pub.vo.BIObject;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.skynet.bi.realform.define.vo.RealFormModel;
import org.springframework.stereotype.Service;

/**
 * 表格模型服务
 * @author javadebug
 *
 */
@Service(value="formDefineServiceInterface")
public interface FormDefineService {
	RealFormModel getModel(long id);
	
	BIObjectSummary getSummary(long id);
	
	BIObjectSummary create(RealFormModel model);
	
	BIObjectSummary updateModel(long id, RealFormModel model);
	
	BIObjectSummary updateSummary(long id, BIObjectSummary summary);
	
	void delete(long id);
	
	void deleteByIds(long[] ids);
	
	BIObjectSummary createField(long modelId, BIObject obj);
	
	BIObjectSummary updateField(long modelId, long fieldId, BIObject obj);
	
	void deleteField(long tableId, long fieldId);
	
	BIObjectSummary updateTable(long modelId, BIObject obj);
	
	BIObjectSummary updateForm(long modelId, BIObject obj);
}
