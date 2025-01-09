package com.ltfullstack.employservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ScopeMetadata;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.ltfullstack.employservice", "com.ltfullstack.commonservice"})
public class EmployserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployserviceApplication.class, args);
	}

}
