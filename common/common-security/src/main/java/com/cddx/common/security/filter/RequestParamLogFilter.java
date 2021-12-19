package com.cddx.common.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.cddx.common.core.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 请求参数打印
 *
 * @author 范劲松
 */
@Log4j2
@Component
@WebFilter(filterName = "requestParamLogFilter", urlPatterns = {"/*"})
@Order(Ordered.HIGHEST_PRECEDENCE + 150)
public class RequestParamLogFilter implements Filter {

    /**
     * 不打印日志的接口
     */
    private static final List<String> DEFAULT_IGNORE = new ArrayList<>(Arrays.asList("/actuator/**"));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            // 排除部分接口不打印日志
            if (StringUtils.matches(request.getRequestURI(), DEFAULT_IGNORE)) {
                // 放行
                chain.doFilter(servletRequest, servletResponse);
                return;
            }

            log.info("request: {} {}", request.getMethod(), request.getRequestURI());
            log.info("query params: {}", JSONObject.toJSON(request.getParameterMap()));
            byte[] requestByteBody = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(request.getInputStream(), baos);
            requestByteBody = baos.toByteArray();
            log.info("body params: {}", JSONObject.parseObject(new String(requestByteBody)).toString());
        } catch (Exception ignore) {} finally {
            chain.doFilter(servletRequest, servletResponse);
        }
    }
}
