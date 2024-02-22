package com.ekart.order;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrdersApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(OrdersApplication.class, args);
	}
//	@Autowired
//	public void configure(EventProcessingConfigurer configurer) {
//		configurer.registerListenerInvocationErrorHandler(
//				"order",
//				configuration -> new OrdereServiceEventsErrorHandler()
//		);
//	}
}
