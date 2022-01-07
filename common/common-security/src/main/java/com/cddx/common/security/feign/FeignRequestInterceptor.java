package com.cddx.common.security.feign;

import com.cddx.common.core.constant.SecurityConstants;
import com.cddx.common.core.utils.ServletUtils;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.ip.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * feign 请求拦截器
 *
 * @author 范劲松
 */
@Log4j2
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest)) {
            // 获得所有请求头
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            // 写入新的请求头
            headers.put(SecurityConstants.SERVICE_ID, applicationName);
            headers.put(SecurityConstants.SERVICE_IP,
                    DigestUtils.md5DigestAsHex(IpUtils.getHostIp().getBytes(StandardCharsets.UTF_8)));
            // 将请求头中的原有数据原封不动传递出去
            Set<String> headerKeys = headers.keySet();
            // 移除空键
            headerKeys.removeIf(StringUtils::isNull);
            headerKeys.forEach(key -> {
                String value = headers.get(key);
                if (StringUtils.isNotNull(value)) {
                    requestTemplate.header(key, value);
                }
            });
            // 其他业务逻辑

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr(httpServletRequest));
        }
    }
}
