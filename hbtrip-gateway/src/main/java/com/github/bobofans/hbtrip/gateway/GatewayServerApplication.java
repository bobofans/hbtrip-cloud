package com.github.bobofans.hbtrip.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        //http://127.0.0.1:48080
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
