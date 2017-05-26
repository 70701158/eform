package org.skynet.bi.pub.obj.service;

import java.util.List;

import org.skynet.bi.pub.obj.po.BIObjectDependPO;

/**
 * BI对象依赖服务
 * @author javadebug
 *
 */
public interface BIObjectDependService {
	List<BIObjectDependPO> getPriors(long id);
	
	List<BIObjectDependPO> getFellows(long id);
	
	long create(BIObjectDependPO depend);
	
	void delete(long id);
	
	void deleteByIds(long[] ids);
}
