package com.cddx.auth;

import com.cddx.common.core.annotation.EnableCustomConfig;
import com.cddx.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 授权服务
 *
 * @author 范劲松
 */
@EnableCustomConfig
@EnableCustomSwagger2
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 授权服务启动成功!   ლ(´ڡ`ლ)ﾞ");
    }

}
