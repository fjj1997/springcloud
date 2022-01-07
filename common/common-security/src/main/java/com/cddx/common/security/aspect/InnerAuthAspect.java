package com.cddx.common.security.aspect;

import com.cddx.common.core.constant.SecurityConstants;
import com.cddx.common.core.constant.ServiceNameConstants;
import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.exception.CustomException;
import com.cddx.common.core.exception.InnerAuthException;
import com.cddx.common.core.utils.ServletUtils;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.ip.IpUtils;
import com.cddx.common.security.annotation.InnerAuth;
import com.cddx.common.security.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 内部服务调用验证处理
 *
 * @author 范劲松
 */
@Aspect
@Log4j2
@Component
public class InnerAuthAspect implements Ordered {
    @Resource
    private DiscoveryClient discoveryClient;

    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable {
        // 获取请求过来的service-id
        String serviceId = ServletUtils.getRequest().getHeader(SecurityConstants.SERVICE_ID);
        String serviceIp = ServletUtils.getRequest().getHeader(SecurityConstants.SERVICE_IP);
        // 检测是否白名单ip
        if (!isMatchWhiteIp(serviceId, serviceIp)) {
            // 非白名单成员，禁止访问
            throw new CustomException(ResultEnum.ILLEGAL_OPERATION);
        }

        // 内部请求验证
        // String source = ServletUtils.getRequest().getHeader(SecurityConstants.FROM_SOURCE);
        // if (!StringUtils.equals(SecurityConstants.INNER, source)) {
        //     throw new InnerAuthException("没有内部访问权限，不允许访问");
        // }

        String userid = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USER_ID);
        String username = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USERNAME);
        // 用户信息验证
        if (innerAuth.isUser() && (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username))) {
            // 没有设置用户信息，不允许访问
            throw new CustomException(ResultEnum.ILLEGAL_OPERATION);
        }
        return point.proceed();
    }

    /**
     * 匹配是否是白名单
     * @return 判断结果
     */
    private boolean isMatchWhiteIp(String service, String ip) {
        // 获取注册中心的注册ip
        Set<String> monitor = discoveryClient.getInstances(service)
                .stream().map(item -> DigestUtils.md5DigestAsHex(item.getHost().getBytes(StandardCharsets.UTF_8)))
                .collect(Collectors.toSet());
        // 匹配结果
        return monitor.stream().anyMatch(ip::startsWith);
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
