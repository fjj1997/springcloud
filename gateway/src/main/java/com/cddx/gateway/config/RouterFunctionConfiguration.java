package com.cddx.gateway.config;

import com.cddx.gateway.handler.HystrixFallbackHandler;
import com.cddx.gateway.handler.ValidateCodeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import javax.annotation.Resource;

/**
 * 路由配置信息
 *
 * @author 范劲松
 */
@Configuration
public class RouterFunctionConfiguration {
    @Resource
    private ValidateCodeHandler validateCodeHandler;

    @Resource
    private HystrixFallbackHandler hystrixFallbackHandler;

    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions
                .route(RequestPredicates.path("/fallback").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        hystrixFallbackHandler).andRoute(
                        RequestPredicates.GET("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        validateCodeHandler);
    }
}
