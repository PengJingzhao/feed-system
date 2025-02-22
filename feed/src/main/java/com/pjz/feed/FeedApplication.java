package com.pjz.feed;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDubbo
@EnableAsync
@EnableDiscoveryClient
public class FeedApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedApplication.class,args);
    }
}
