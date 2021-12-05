package com.cddx.file;

import com.cddx.common.core.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件服务
 *
 * @author 范劲松
 */
@EnableCustomConfig
@SpringBootApplication
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 文件服务启动成功!   ლ(´ڡ`ლ)ﾞ");
    }

}
