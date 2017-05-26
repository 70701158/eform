package org.skynet.bi.pub.obj.service;

import java.util.List;

import org.skynet.bi.pub.vo.BIObjectDefine;
import org.skynet.bi.pub.vo.BIObjectSummary;

/**
 * BI对象服务
 * @author javadebug
 *
 */
public interface BIObjectService {
	//获得指定用户的所有的分析对象
	List<BIObjectSummary> getAllObjectByUser(long userId);
	
	BIObjectSummary getSummary(long id);
	
	List<BIObjectSummary> getSummariesByIds(Long[] ids);
	
	List<BIObjectSummary> getChildSummaries(long id);
	
	List<BIObjectSummary> getSummariesWithChildren(long id);
	
	BIObjectSummary create(BIObjectSummary summary, String define);
	
	List<BIObjectSummary> batchCreate(List<BIObjectSummary> summaries, List<BIObjectDefine> defines);
	
	List<BIObjectSummary> batchUpdate(List<BIObjectSummary> summaries, List<BIObjectDefine> defines);
	
	BIObjectSummary updateSummary(BIObjectSummary summary);
	
	void updateDefine(BIObjectDefine define);
	
	void delete(long id);
	
	void deleteByIds(long[] ids);
	
	BIObjectDefine getDefine(long id);
	
	List<BIObjectDefine> getDefinesByIds(Long[] ids);
}
