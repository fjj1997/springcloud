package com.cddx.common.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.cddx.common.core.constant.Constants;
import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.utils.ip.IpUtils;
import com.cddx.common.core.web.response.AjaxResult;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
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
import java.util.List;

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
    @Resource
    private Config config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("actuator white ips: {}", config.getIp());
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        PrintWriter writer = null;
        try {
            // 检测是否白名单ip
            if (isMatchWhiteIp(IpUtils.getIpAddr(request))) {
                // 放行
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
        log.info("actuator ip: {}", ip);
        return config.getIp().stream().anyMatch(ip::startsWith);
    }

    @Data
    @ToString
    @RefreshScope
    @Configuration
    @ConfigurationProperties("management.white")
    static class Config {
        private List<String> ip = new ArrayList<>();
    }
}
