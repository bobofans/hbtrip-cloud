package com.github.bobofans.hbtrip.framework.security.config;

import com.github.bobofans.hbtrip.framework.security.core.rpc.LoginUserRequestInterceptor;
import com.github.bobofans.hbtrip.module.system.api.oauth2.OAuth2TokenApi;
import com.github.bobofans.hbtrip.module.system.api.permission.PermissionApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author 芋道源码
 */
@AutoConfiguration
@EnableFeignClients(clients = {OAuth2TokenApi.class, // 主要是引入相关的 API 服务
        PermissionApi.class})
public class HbtripSecurityRpcAutoConfiguration {

    @Bean
    public LoginUserRequestInterceptor loginUserRequestInterceptor() {
        return new LoginUserRequestInterceptor();
    }

}
