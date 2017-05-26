package org.skynet.bi.eform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan(basePackages={"org.skynet"}) // 扫描该包路径下的所有spring组件
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class EFormApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFormApplication.class, args);
		
//		SpringApplication app = new SpringApplication(RealFormApplication.class);
		
	}
	
//	@Bean
//    public Object testBean(PlatformTransactionManager platformTransactionManager){
//        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
//        return new Object();
//    }
//	
//	@Bean
//	@ConditionalOnMissingBean(PlatformTransactionManager.class)
//	public PlatformTransactionManager transactionManager() {
//		return new DataSourceTransactionManager().set;
//	}
}
