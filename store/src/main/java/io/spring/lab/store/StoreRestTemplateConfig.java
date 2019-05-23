package io.spring.lab.store;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.spring.lab.cloud.ConditionalOnEurekaClient;
import io.spring.lab.cloud.ConditionalOnMissingEurekaClient;

@Configuration
public class StoreRestTemplateConfig {

    @Bean
    @ConditionalOnMissingEurekaClient
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @LoadBalanced
    @ConditionalOnEurekaClient
    RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
