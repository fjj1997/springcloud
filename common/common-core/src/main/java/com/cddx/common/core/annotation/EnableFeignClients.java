package com.cddx.common.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义feign注解
 * 添加basePackages路径
 *
 * @author 范劲松
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.cloud.openfeign.EnableFeignClients
public @interface EnableFeignClients {
    String[] value() default {};

    String[] basePackages() default {"com.cddx"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
