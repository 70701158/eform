package org.skynet.bi;

import org.skynet.bi.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@RequestMapping("/hello")
@Api("Hello world Demo")
@ConfigurationProperties(prefix = "hello", ignoreUnknownFields = true)
public class HelloController extends BaseController {
	
	private String content;
	
    @ApiOperation(value="Say hello", notes="")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String sayHello() {
    	return content;
    }
}
