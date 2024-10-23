package com.github.bobofans.hbtrip.module.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author 芋道源码
 */
@SpringBootApplication
public class SystemServerApplication {

    public static void main(String[] args) {
        //http://127.0.0.1:48081/admin-api/system/
        SpringApplication.run(SystemServerApplication.class, args);
    }

}
