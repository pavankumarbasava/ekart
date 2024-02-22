package com.ekart.productservice;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ProductserviceApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("IST +0:00"));
		SpringApplication.run(ProductserviceApplication.class, args);
	}

}
