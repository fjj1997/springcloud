package com.cddx.common.security.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Actuator 放行配置
 *
 * @author 范劲松
 */
@Data
@ToString
@RefreshScope
@Configuration
@ConfigurationProperties("management.white")
public class ActuatorConfig {
    private List<String> ip = new ArrayList<>();
}
