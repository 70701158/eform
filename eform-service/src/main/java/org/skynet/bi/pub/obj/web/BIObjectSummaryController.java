package org.skynet.bi.pub.obj.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.skynet.bi.framework.context.Context;
import org.skynet.bi.framework.web.BaseController;
import org.skynet.bi.pub.obj.service.BIObjectService;
import org.skynet.bi.pub.vo.BIObjectSummary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * BI对象摘要控制器
 * @author javadebug
 *
 */
@RestController
@RequestMapping("/summaries")
@Api("BI对象摘要API")
public class BIObjectSummaryController extends BaseController {
	@Resource(name = "biObjectService") 
    private BIObjectService service;

    @ApiOperation(value="获得我的对象", notes="")
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public Map<Short, List<BIObjectSummary>> getSummaryMap(HttpServletResponse response){
        Map<Short, List<BIObjectSummary>> summaryMap = new HashMap<Short, List<BIObjectSummary>>();
    	List<BIObjectSummary>summaries = service.getAllObjectByUser(Context.current().getLoginUser().getId());
    	summaries.forEach((BIObjectSummary summary) -> {
    		List<BIObjectSummary> summaryList = summaryMap.get(summary.getType());
    		if (summaryList == null) {
    			summaryList = new ArrayList<BIObjectSummary>();
    			summaryMap.put(summary.getType(), summaryList);
    		}
    		
    		summaryList.add(summary);
    	});
        
        response.setStatus(HttpServletResponse.SC_CREATED);
        
        return summaryMap;
    }
}
