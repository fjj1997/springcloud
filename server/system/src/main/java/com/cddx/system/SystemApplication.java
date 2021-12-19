package com.cddx.system;

import com.cddx.common.core.annotation.EnableCustomConfig;
import com.cddx.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统服务
 *
 * @author 范劲松
 */
@EnableCustomConfig
@EnableCustomSwagger2
@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 系统服务启动成功!   ლ(´ڡ`ლ)ﾞ");
    }

}
