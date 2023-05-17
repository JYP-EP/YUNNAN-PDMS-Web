package com.ruoyi;

import com.ruoyi.framework.config.MyBatisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {"com.ruoyi"}, exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.ruoyi.**.mapper,com.gridnt.**.mapper")
public class RuoYiApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  RBAC启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
