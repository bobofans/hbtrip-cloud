package com.github.bobofans.hbtrip.module.infra.framework.file.config;

import com.github.bobofans.hbtrip.module.infra.framework.file.core.client.FileClientFactory;
import com.github.bobofans.hbtrip.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class HbtripFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
