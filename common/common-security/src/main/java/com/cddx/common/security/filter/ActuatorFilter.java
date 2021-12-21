package com.cddx.common.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.cddx.common.core.constant.Constants;
import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.ip.IpUtils;
import com.cddx.common.core.web.response.AjaxResult;
import com.cddx.common.security.config.ActuatorConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 针对于SpringCloud Actuator的检测白名单，只有白名单ip才可访问
 *
 * @author 范劲松
 */
@Log4j2
@Component
@RefreshScope
@WebFilter(filterName = "actuatorFilter", urlPatterns = {"/actuator/*"})
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ActuatorFilter implements Filter {
    private static final List<String> DEFAULT_WHITE = new ArrayList<>(Arrays.asList("/actuator/**"));

    @Resource
    private ActuatorConfig config;

    @Resource
    private DiscoveryClient discoveryClient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("actuator white ips: {}", config.getIp());
        log.info("client service: {}", discoveryClient.getInstances("monitor")
                .stream().map(ServiceInstance::getHost).collect(Collectors.toSet()));
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 检查地址外成员放行
        if (!StringUtils.matches(request.getRequestURI(), DEFAULT_WHITE)) {
            // 放行
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        PrintWriter writer = null;
        try {
            // 检测是否白名单ip
            if (isMatchWhiteIp(IpUtils.getIpAddr(request))) {
                chain.doFilter(request, servletResponse);
                return;
            }
            throw new Exception();
        } catch (Exception e) {
            servletResponse.setCharacterEncoding(Constants.UTF8);
            servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer = servletResponse.getWriter();
            ResultEnum responseEnum = ResultEnum.NOT_PERMISSION_ERROR;
            writer.write(new JSONObject(AjaxResult.error(responseEnum)).toString());
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * 匹配是否是白名单
     * @return 判断结果
     */
    private boolean isMatchWhiteIp(String ip) {
        return config.getIp().stream().anyMatch(ip::startsWith);
    }
}
