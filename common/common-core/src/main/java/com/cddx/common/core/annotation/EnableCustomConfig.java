package com.cddx.common.core.annotation;

import com.cddx.common.core.config.ApplicationConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * 公共配置注解
 *
 * @author 范劲松
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.cddx.**.mapper")
// 扫描JPA
@EntityScan("com.cddx.**.po")
// 自动扫描注入
@ComponentScan("com.cddx.**")
// 开启线程异步执行
@EnableAsync
// 自动加载类
@Import({ApplicationConfig.class, FeignAutoConfiguration.class, AutoConfigurations.class})
public @interface EnableCustomConfig {

}
