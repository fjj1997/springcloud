package com.cddx.common.security.annotation;

import java.lang.annotation.*;

/**
 * 内部认证注解
 *
 * @author 范劲松
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth {
    /**
     * 是否校验用户信息
     */
    boolean isUser() default false;
}