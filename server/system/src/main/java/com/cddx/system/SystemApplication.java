package com.cddx.system;

import com.cddx.common.core.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统服务
 *
 * @author 范劲松
 */
@EnableCustomConfig
@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 系统服务启动成功!   ლ(´ڡ`ლ)ﾞ");
    }

}
