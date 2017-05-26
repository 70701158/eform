package org.skynet.bi.framework.service;

import java.sql.Timestamp;

import org.skynet.bi.framework.bo.Entity;
import org.skynet.bi.framework.context.Context;
import org.skynet.bi.framework.util.IDGenerator;

/**
 * 服务基类
 * @author javadebug
 *
 */
public class BaseService {
	protected void fillCreateInfo(Entity entity) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		entity.setCreateTime(time);
		entity.setCreator(Context.current().getLoginUser().getId());
		entity.setTenantId(Context.current().getLoginUser().getTenantId());
		
		if (entity.getId() <= 0) {
			entity.setId(IDGenerator.nextLong());
		}
	}
	
	protected void fillUpdateInfo(Entity entity) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		entity.setLastModified(time);
		entity.setLastModifier(Context.current().getLoginUser().getId());
	}
}
