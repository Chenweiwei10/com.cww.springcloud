package com.cww.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTempalte(){
        return new RestTemplate();
    }
}
