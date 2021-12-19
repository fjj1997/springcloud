package com.cddx.common.security.filter;

import com.alibaba.fastjson.JSONObject;
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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
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
