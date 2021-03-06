package com.cddx.common.swagger.annotation;

import com.cddx.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启swagger
 *
 * @author 范劲松
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableCustomSwagger2 {

}
