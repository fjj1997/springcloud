package com.cddx.common.security.config;

import com.cddx.common.security.interceptor.HeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author 范劲松
 * @date 2022-01-07 11:49
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 不需要拦截地址
     */
    public static final String[] EXCLUDE_URLS = {"/login", "/logout", "/refresh"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_URLS)
                .order(-10);
    }

    /**
     * 自定义请求头拦截器
     */
    public HeaderInterceptor getHeaderInterceptor() {
        return new HeaderInterceptor();
    }

}
