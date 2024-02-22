package com.ekart.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	 @Bean
	    public WebClient.Builder webClientBuilder() {
	        return WebClient.builder()
	                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection()
	                        .compress(true) // (Optional) Enable compression
	                        .port(8005) // Port of the service you want to connect to
	                        .keepAlive(true))); // (Optional) Configure other settings as needed
	    }
	 
	 @LoadBalanced
	    @Bean
	    public RestTemplate restTemplateBean() {
	        return new RestTemplate();
	    }
	 
	  @Bean(name = "applicationEventMulticaster")
	  public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
	    SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
	    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
	    threadPoolTaskExecutor.setCorePoolSize(8);
	    threadPoolTaskExecutor.setMaxPoolSize(16);
	    threadPoolTaskExecutor.initialize();
	    eventMulticaster.setTaskExecutor(threadPoolTaskExecutor);
	    return eventMulticaster;
	  }
}
