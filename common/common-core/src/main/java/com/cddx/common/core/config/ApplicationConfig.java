package com.cddx.common.core.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.TimeZone;

/**
 * 系统配置
 *
 * @author 范劲松
 */
@Configuration
public class ApplicationConfig {
    /**
     * 全局配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> {
            // Long 和 BigInteger 转换配置（防精度丢失）
            jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
            // 时区配置
            jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
        };
    }
}
