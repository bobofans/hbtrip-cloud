package com.github.bobofans.hbtrip.framework.signature.config;

import com.github.bobofans.hbtrip.framework.redis.config.HbtripRedisAutoConfiguration;
import com.github.bobofans.hbtrip.framework.signature.core.aop.ApiSignatureAspect;
import com.github.bobofans.hbtrip.framework.signature.core.redis.ApiSignatureRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * HTTP API 签名的自动配置类
 *
 * @author Zhougang
 */
@AutoConfiguration(after = HbtripRedisAutoConfiguration.class)
public class HbtripApiSignatureAutoConfiguration {

    @Bean
    public ApiSignatureAspect signatureAspect(ApiSignatureRedisDAO signatureRedisDAO) {
        return new ApiSignatureAspect(signatureRedisDAO);
    }

    @Bean
    public ApiSignatureRedisDAO signatureRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new ApiSignatureRedisDAO(stringRedisTemplate);
    }

}
