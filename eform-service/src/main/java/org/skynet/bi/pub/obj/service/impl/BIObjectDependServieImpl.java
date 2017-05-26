package org.skynet.bi.pub.obj.service.impl;
import java.util.List;

import org.skynet.bi.framework.context.Context;
import org.skynet.bi.framework.service.BaseService;
import org.skynet.bi.framework.util.IDGenerator;
import org.skynet.bi.pub.obj.mapper.BIObjectDependMapper;
import org.skynet.bi.pub.obj.po.BIObjectDependPO;
import org.skynet.bi.pub.obj.service.BIObjectDependService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BI对象依赖服务
 * @author javadebug
 *
 */
@Service(value="biObjectDependService")
public class BIObjectDependServieImpl extends BaseService implements BIObjectDependService {

    @Autowired
    private BIObjectDependMapper mapper;

	@Override
	public List<BIObjectDependPO> getPriors(long id) {
		return mapper.getPirors(id);
	}

	@Override
	public List<BIObjectDependPO> getFellows(long id) {
		return mapper.getFollows(id);
	}

	@Override
	public long create(BIObjectDependPO depend) {
		if (depend.getId() <= 0) {
			depend.setId(IDGenerator.nextLong());
		}
		
		depend.setTenantId(Context.current().getLoginUser().getTenantId());
		
		mapper.create(depend);
		
		return depend.getId();
	}

	@Override
	public void delete(long id) {
		mapper.deleteByIds(new long[] {id});
	}

	@Override
	public void deleteByIds(long[] ids) {
		mapper.deleteByIds(ids);
	}

}