package com.github.bobofans.hbtrip.module.ai.framework.rpc.config;

import com.github.bobofans.hbtrip.module.infra.api.file.FileApi;
import com.github.bobofans.hbtrip.module.system.api.dict.DictDataApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {DictDataApi.class, FileApi.class})
public class RpcConfiguration {
}
