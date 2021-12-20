package com.cddx.auth;

import com.cddx.common.core.annotation.EnableCustomConfig;
import com.cddx.common.core.annotation.EnableFeignClients;
import com.cddx.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 授权服务
 *
 * @author 范劲松
 */
@EnableFeignClients
@EnableCustomConfig
@EnableCustomSwagger2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 授权服务启动成功!   ლ(´ڡ`ლ)ﾞ");
    }

}
