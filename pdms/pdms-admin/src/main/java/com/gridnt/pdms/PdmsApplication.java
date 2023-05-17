package com.gridnt.pdms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {"com.ruoyi", "com.gridnt"}, exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.ruoyi.**.mapper,com.gridnt.**.mapper")
public class PdmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdmsApplication.class, args);
        System.out.println("...pdms online started...");
    }

}
