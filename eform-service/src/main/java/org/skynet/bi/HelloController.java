package org.skynet.bi;

import org.skynet.bi.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RealForm定义控制器
 * @author javadebug
 *
 */
@RefreshScope
@RestController
@RequestMapping("/hello")
public class HelloController extends BaseController {
	@Value("${hello.content}")
    private String content;
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String sayHello() {
    	return content;
    }
}
