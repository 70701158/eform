package org.skynet.bi.pub.obj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.skynet.bi.framework.service.BaseService;
import org.skynet.bi.pub.obj.mapper.BIObjectDefineMapper;
import org.skynet.bi.pub.obj.mapper.BIObjectSummaryMapper;
import org.skynet.bi.pub.obj.po.BIObjectDefinePO;
import org.skynet.bi.pub.obj.po.BIObjectSummaryPO;
import org.skynet.bi.pub.obj.service.BIObjectService;
import org.skynet.bi.pub.vo.BIObjectDefine;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * BI对象服务
 * @author javadebug
 *
 */
@Service(value="biObjectService")
//@ Rollback(value = true)
@Transactional
public class BIObjectServiceImpl extends BaseService implements BIObjectService {

    @Autowired
    private BIObjectSummaryMapper summaryMapper;
    
    @Autowired
    private BIObjectDefineMapper defineMapper;

    @Override
    public BIObjectSummary getSummary(long id){
        return toBIObjectSummary(summaryMapper.get(id));
    }

	@Override
	public List<BIObjectSummary> getSummariesByIds(Long[] ids) {
		List<BIObjectSummaryPO> summaryPOs = summaryMapper.getByIds(ids);
		return toSummaryList(summaryPOs);
	}

	private List<BIObjectSummary> toSummaryList(List<BIObjectSummaryPO> summaryPOs) {
		List<BIObjectSummary> summaries = new ArrayList<BIObjectSummary>();
		summaryPOs.forEach((BIObjectSummaryPO po) -> {
			summaries.add(toBIObjectSummary(po));
		});
		return summaries;
	}

	@Override
	public List<BIObjectSummary> getChildSummaries(long parentId) {
		List<BIObjectSummaryPO> summaryPOs = summaryMapper.getChildren(parentId);
		return toSummaryList(summaryPOs);
	}

	@Override
	public List<BIObjectSummary> getSummariesWithChildren(long parentId) {
		List<BIObjectSummaryPO> summaryPOs = summaryMapper.getWithChildren(parentId);
		return toSummaryList(summaryPOs);
	}

	@Transactional
	@Override
	public BIObjectSummary create(BIObjectSummary summary, String define) {
		this.fillCreateInfo(summary);
		
		summaryMapper.create(summary);
		
		BIObjectDefinePO definePO = new BIObjectDefinePO(summary.getId(), summary.getTenantId(), define);
		
		defineMapper.create(definePO);
		
		return summary;
	}

	@Override
	public BIObjectSummary updateSummary(BIObjectSummary obj) {
		this.fillUpdateInfo(obj);
		summaryMapper.update(obj);
		
		return obj;
	}
	
	@Override
	public void updateDefine(BIObjectDefine define) {
		BIObjectDefinePO po = new BIObjectDefinePO(define.getId(), define.getTenantId(), define.getDefine());
		defineMapper.update(po);
	}

	@Transactional
	@Override
	public void delete(long id) {
		summaryMapper.delete(id);
//		defineMapper.delete(id);
	}

	@Transactional
	@Override
	public void deleteByIds(long[] ids) {
		summaryMapper.deleteByPrimaryKies(ids);
		defineMapper.deleteByIds(ids);
	}

	@Override
	public BIObjectDefine getDefine(long id) {
		BIObjectDefinePO define = defineMapper.get(id);
		return define == null ? null : new BIObjectDefine(define.getId(), define.getTenantId(), define.getDefine());
	}

	@Override
	public List<BIObjectDefine> getDefinesByIds(Long[] ids) {
		if (ids.length == 0) {
			return new ArrayList<BIObjectDefine>(1);
		} else  {
			List<BIObjectDefinePO> pos = defineMapper.getByIds(ids);
			List<BIObjectDefine> defines = new ArrayList<BIObjectDefine>();
			toDefineList(pos, defines);
			return defines;
		}
	}

	private void toDefineList(List<BIObjectDefinePO> pos, List<BIObjectDefine> defines) {
		pos.forEach((BIObjectDefinePO po) -> {
			defines.add(new BIObjectDefine(po.getId(), po.getTenantId(), po.getDefine()));
		});
	}

	@Transactional
	@Override
	public List<BIObjectSummary> batchCreate(List<BIObjectSummary> summaries, List<BIObjectDefine> defines) {
		List<BIObjectSummary> resultList = new ArrayList<BIObjectSummary>();
		summaries.forEach((BIObjectSummary summary) -> {
			fillCreateInfo(summary);
			summaryMapper.create(summary);
			resultList.add(summary);
		});
		
		defines.forEach(defineMapper::create);
		
		return resultList;
	}

	@Transactional
	@Override
	public List<BIObjectSummary> batchUpdate(List<BIObjectSummary> summaries, List<BIObjectDefine> defines) {
		List<BIObjectSummary> resultList = new ArrayList<BIObjectSummary>();
		summaries.forEach((BIObjectSummary summary) -> {
			fillCreateInfo(summary);
			summaryMapper.update(summary);
			resultList.add(summary);
		});
		
		defines.forEach(defineMapper::update);
		
		return resultList;
	}

	@Override
	public List<BIObjectSummary> getAllObjectByUser(long userId) {
		return toSummaryList(summaryMapper.getByUser(userId));
	}
	
	/**
	 * 从摘要的PO转换为VO
	 * @param summaryPO
	 * @return
	 */
	private BIObjectSummary toBIObjectSummary(BIObjectSummaryPO summaryPO) {
		BIObjectSummary obj = new BIObjectSummary();
		BeanUtils.copyProperties(summaryPO, obj);
		
		return obj;
	}
}