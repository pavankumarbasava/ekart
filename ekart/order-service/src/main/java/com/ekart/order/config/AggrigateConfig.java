//package com.ekart.order.config;
//
//import javax.inject.Scope;
//
//import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AggrigateConfig {
//
//	   @Bean
//	    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	    public AuditServiceAggregate auditServiceAggregate() {
//	        AuditServiceAggregate auditServiceAggregate = new AuditServiceAggregate();
//	        return auditServiceAggregate;
//	    }
//
//	    @Bean
//	    public SpringPrototypeAggregateFactory<AuditServiceAggregate> aggregateFactory() {
//	        SpringPrototypeAggregateFactory<AuditServiceAggregate> aggregateFactory = new SpringPrototypeAggregateFactory<AuditServiceAggregate>();
//	        aggregateFactory.setPrototypeBeanName("auditServiceAggregate");
//	        return aggregateFactory;
//	    }
//}
