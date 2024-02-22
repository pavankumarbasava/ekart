package com.ekart.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.TimeZone;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("IST +0:00"));
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

}




