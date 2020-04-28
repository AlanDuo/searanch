package com.yunyuan.searanch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author alan
 * @date 2020/3/15
 */
@SpringBootApplication
@MapperScan("com.yunyuan.searanch.dao")
@EnableCaching
public class SearanchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearanchApplication.class, args);
    }

}
