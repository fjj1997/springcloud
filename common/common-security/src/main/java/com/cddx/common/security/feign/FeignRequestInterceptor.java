package com.cddx.common.security.feign;

import com.cddx.common.core.utils.ServletUtils;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.ip.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * feign 请求拦截器
 *
 * @author 范劲松
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest)) {
            // 获得所有请求头
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
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
